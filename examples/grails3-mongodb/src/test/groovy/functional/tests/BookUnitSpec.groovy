package functional.tests

import com.github.fakemongo.Fongo
import com.mongodb.MongoClient
import grails.test.mongodb.MongoSpec

class BookUnitSpec extends MongoSpec {

    @Override
    MongoClient createMongoClient() {
        return new Fongo(getClass().name).mongo
    }

    void "Test low-level API extensions"() {
        when:
        def db = createMongoClient().getDatabase("test")
//        db.drop()
        // Insert a document
        db['languages'].insert([name: 'Groovy'])
        // A less verbose way to do it
        db.languages.insert(name: 'Ruby')
        // Yet another way
        db.languages << [name: 'Python']

        then:
        db.languages.count() == 3
    }

    void "Test GORM access"(){
        when:
        Book book = new Book(title: 'El Quijote').save(flush: true)

        then:
        Book.count() ==1

        when:
        book = Book.findByTitle('El Quijote')

        then:
        book.id
    }

}
