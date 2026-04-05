## JavaPackagePathCheck

### Naming

#### Follow File Location

The package name should match the directory structure of the location of
the `.java` file.

#### Example

The correct package for class `UserLocalServiceImpl` located in
`portal-impl\com\liferay\portal\service\impl\` is

```java
package com.liferay.portal.service.impl;
```

#### Follow Modules Settings

For modules, the package name should match the `Bundle-SymbolicName` specified
in the BND settings.

#### Example

```
`Bundle-SymbolicName`: com.liferay.freemarker.osgi.bridge
```

The package name for all files inside the module should start with
`com.liferay.freemarker.osgi.bridge`

#### Exceptions

1. `Bundle-SymbolicName` ending with `.api` or `.test`

```
Bundle-SymbolicName: com.liferay.blogs.api
```

or

```
Bundle-SymbolicName: com.liferay.blogs.test
```

The package name for all files inside the module should start with
`com.liferay.blogs`.

1. `Bundle-SymbolicName` ending with `.impl`

```
Bundle-SymbolicName: com.liferay.blogs.demo.data.creator.impl
```

The package name for all files inside the module should start with
`com.liferay.blogs.demo.data.creator.impl` or
`com.liferay.blogs.demo.data.creator.internal`.

We should not have package names that contain both `impl` and `internal` like
`com.liferay.wiki.internal.util.impl`, because `internal` implies `impl`.

#### Classes Extending, Implementing or Using Kernel Classes

It's best to mirror the package structure of kernel when possible to make
classes easier to find.

#### Example

Say there is an interface in kernel with the package
`com.liferay.portal.kernel.a.b.c` and we're implementing the class in blogs
service. Then the best place to put the class would be
`com.liferay.blogs.internal.a.b.c`.