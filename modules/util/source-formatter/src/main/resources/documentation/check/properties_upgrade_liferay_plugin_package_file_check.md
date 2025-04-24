## PropertiesUpgradeLiferayPluginPackageFileCheck

This check upgrades the `liferay-plugin-package.properties` file in a Liferay
workspace module.

- Removes the following deprecated properties:
  - `portal-dependency-jars`
- Add required properties:
  - `-noee: true`

### Example

Incorrect:

```properties
licenses=LGPL
liferay-versions=7.0.0+,7.1.0+,7.2.0+,7.3.0+,7.4.0+
long-description=
module-group-id=liferay
module-incremental-version=1
name=sample-war-mvc-portlet
page-url=http://www.liferay.com
portal-dependency-jars=\
    ehcache-core.jar,\
    hibernate.jar,\
    log4j.jar,\
    spring-context.jar,\
    spring-mvc.jar,\
    spring-tx.jar,\
    spring-web.jar
short-description=
tags=
```

Correct:

```properties
licenses=LGPL
liferay-versions=7.0.0+,7.1.0+,7.2.0+,7.3.0+,7.4.0+
long-description=
module-group-id=liferay
module-incremental-version=1
name=sample-war-mvc-portlet
page-url=http://www.liferay.com
short-description=
tags=
-noee: true
```