## DatabaseMetaDataCheck

Using `java.sql.DatabaseMetaData#getIndexInfo()` can lead to unexpected errors
and bugs depending on the current database. Instead, use
`com.liferay.portal.kernel.dao.db.DB#getIndexResultSet()` to obtain database
index information.

### Example

Incorrect:

```java
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

...

DatabaseMetaData databaseMetaData = connection.getMetaData();

try (ResultSet indexResultSet = databaseMetaData.getIndexInfo(
        catalog, schema, tableName, onlyUnique, false)) {

    doSomething(indexResultSet);
}
```

Correct:

```java
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import java.sql.ResultSet;

...

DB db = DBManagerUtil.getDB();

try (ResultSet indexResultSet = db.getIndexResultSet(
        connection, tableName, onlyUnique)) {

    doSomething(indexResultSet);
}
```