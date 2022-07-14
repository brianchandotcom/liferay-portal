## OSGi Log Service Extender

The OSGi Log Service Extender enables bundles to provide OSGi logging configuration using embedded properties files `META-INF/osgi-logging.properties` or `META-INF/osgi-logging-ext.properties`.

Each line in the file is key-value pair with a logger name and the logging level.

**e.g.**

```properties
com.foo=DEBUG
com.foo.bar=ERROR
```

Supported logging levels are:

* **ERROR** - Information about an error situation.
* **WARN** - Information about a failure or unwanted situation that is not blocking.
* **INFO** - Information about normal operation.
* **DEBUG** - Detailed output for debugging operations.

### Gogo Shell Logging Commands

In addition, Gogo shell commands are provided to interact with log levels in real time. The available commands are in the `logging` command scope (following the syntax `<scope>:<command>`).

> ***Note:** The scope and colon (`<scope>:`) can generally be omitted when executing commands and is only required in order to disambiguate conflicting command names.*

The commands are:

* `logging:levels <context>` - List the levels associated with a given logger context.

  * `<context>` - a logger context name. Uses one the following syntax:
    * `<Bundle-SymbolicName>`
    * `<Bundle-SymbolicName>|<Bundle-Version>`
    * `<Bundle-SymbolicName>|<Bundle-Version>|location`
    * `null` - returns the default logger context

  **e.g.** List the levels in the context `com.liferay.oauth2.provider.service`:

  ```bash
  g! levels org.apache.felix.configadmin
  ```

* `logging:level <context> <name> <level>` - Set the level of a named logger in a given logger context.

  * `<context>` - a logger context name and follows the syntax specified above
  * `<name>` - the name of a logger
  * `<level>` - the level (`ERROR`, `WARN`, `INFO` or `DEBUG` or same as lower case)

  **e.g.** Set the level of the named logger `com.liferay.oauth2.provider.service.persistence.impl` to `DEBUG` in the context `com.liferay.oauth2.provider.service`:

  ```bash
  g! level com.liferay.oauth2.provider.service com.liferay.oauth2.provider.service.persistence.impl debug
  ```

### Mapping of Legacy OSGi Log Events

With version 1.4 of the OSGi Log Service there are 4 additional logger names which map previously uncategorised legacy log events and allow control over them.

* `Events.Bundle` - logger name for Bundle Events
* `Events.Service` - logger name for Service Events
* `Events.Framework` - logger name for Framework Events
* `LogService` - logger name for LogService events

Following the previous examples we can adjust the level of a bundle's legacy `LogService` events using either the properties file:

```properties
com.foo=DEBUG
com.foo.bar=ERROR
LogService=DEBUG
```

We can also adjust the level of a bundle's legacy `LogService` events using the Gogo shell command:

```bash
g! level org.apache.felix.configadmin LogService DEBUG
```