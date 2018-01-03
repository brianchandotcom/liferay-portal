# lib Directory

JARs can be stored in three different locations in the `lib` directory, each
with a different purpose.

JARs in the `development` directory are only visible during compile time. Most
of these JARs are already included by the application server or are only useful
for a specific database (i.e., JDBC drivers).

JARs in the `global` directory are automatically copied to the application
server's global classpath and are visible by all applications loaded by the
application server.

JARs in the `portal` directory are automatically copied to the portal classpath
and are only visible by the portal.