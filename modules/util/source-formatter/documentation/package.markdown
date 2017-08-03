## Java package formatting

### Naming

#### Follow file location
The java package name should match the directory structure of the location of
the java file.

#### Example

The correct package for class UserLocalServiceImpl located in
```portal-impl\com\liferay\portal\service\impl\``` is

```java
package com.liferay.portal.service.impl;
```

#### Follow modules settings
For modules, the package name should match the ```Bundle-SymbolicName```
specified in the bnd settings

#### Example

```
Bundle-SymbolicName: com.liferay.freemarker.osgi.bridge
```
The package path for all files inside the module should start with
```com.liferay.freemarker.osgi.bridge```

#### Exceptions

1. Bundle-SymbolicName ending with ```.api``` or ```.test```

```
Bundle-SymbolicName: com.liferay.blogs.api
```
or
```
Bundle-SymbolicName: com.liferay.blogs.api
```

The package path for all files inside the module should start with
```com.liferay.blogs```

2. Bundle-SymbolicName ending with ```.impl```

```
Bundle-SymbolicName: com.liferay.blogs.demo.data.creator.impl
```

The package path for all files inside the module should start with
```com.liferay.blogs.demo.data.creator.impl``` or
```com.liferay.blogs.demo.data.creator.internal```