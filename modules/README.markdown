# Liferay Modules

This document explains how to use Liferay's build system and its recommended
best practices.

## Build System

### Build with Java 8

The default value for source/target compatibility is still Java 7. This is to
limit the use of new features only when necessary. In order to use Java 8
features, add these lines in your `build.gradle` file:

```gradle
sourceCompatibility = "1.8"
targetCompatibility = "1.8"
```

### Deploy Directory

The module's deploy directory is the `deploy.destinationDir` property (the
`destinationDir` property of the `deploy` task). This property is set to
`liferay.deployDir` (the `deployDir` property of the `liferay` extension object)
by default.

The logic that chooses the default deploy directory is as follows:

1. If the project directory contains a `.lfrbuild-app-server-lib` marker file,
the module is deployed to `${app.server.portal.dir}/WEB-INF/lib`.
2. If the project directory contains a `.lfrbuild-tool` marker file, the module
is deployed to `${liferay.home}/tools/${module.dir.name}`.
3. If the project directory contains a `.lfrbuild-static` marker file, the
module is deployed to `${liferay home}/osgi/static`.
4. If the module symbolic name starts with `com.liferay.portal.`, the module is
deployed to `${liferay home}/osgi/portal`.
5. Otherwise, the module is deployed to `${liferay home}/osgi/modules`.

If possible, you should always use these marker files to specify the deploy
directory of your modules. If none of these cases apply to you, then add
something like this to your `build.gradle`:

```gradle
liferay {
   deployDir = file("${liferayHome}/osgi/test")
}
```

To know what paths (e.g., `liferayHome`) are available, examine the getter
methods in the `LiferayExtension` class.

It's fine to have both `.lfrbuild-portal` and one of these marker files in the
same project; the `.lfrbuild-portal` file tells the build system to build the
module with `ant all` and the other marker files choose the deploy directory.

## Marker Files

### Build

File Name | Description
--------- | -----------
`.lfrbuild-portal-pre` | Builds the module, during the `ant compile` execution, in the `tmp/lib-pre` directory before building `portal-kernel`, `portal-impl`, etc.
`.lfrbuild-portal` | Deploys the module during the `ant all` execution. `-test` modules never have this file.
`.lfrbuild-slim` | Deploys the module during the `ant all` execution if building a Liferay Slim Runtime.

### Continuous Integration

File Name | Description
--------- | -----------
`.lfrbuild-ci` | Deploys the module during the `ant all` execution, but only if running in Jenkins.
`.lfrbuild-semantic-versioning` | Enables the semantic versioning check of the module on CI. `apps` and `core` modules are already checked, so they don't need this marker file.

### Deploy Directory

File Name | Description
--------- | -----------
`.lfrbuild-app-server-lib` | Deploys the module to `${app.server.portal.dir}/WEB-INF/lib`.
`.lfrbuild-static` | Deploys the module to `${liferay home}/osgi/static`.
`.lfrbuild-tool` | Deploys the module to `${liferay.home}/tools/${module.dir.name}`.

### Faro

File Name | Description
--------- | -----------
`.lfrbuild-faro-connector` | Deploys the module to the Faro client portal directory.
`.lfrbuild-faro-site` | Deploys the module to the Faro site portal directory.

### Release

File Name | Description
--------- | -----------
`.lfrbuild-releng-ignore` | Ignores checking the module for staleness, so the module is never publishable. A *stale* module has code that is different from the latest published release.
`.lfrrelease-src` | Includes the app's source code in the DXP release, when added to the root of an app.

### Themes

File Name | Description
--------- | -----------
`.lfrbuild-missing-resources-importer` | Prevents the theme from being published in case it doesn't contain the *Resources Importer* files. This is only added on the `master `branch.

## Source Formatting

### Gradle Files

The following source formatting rules should be followed for Gradle files.

* Always use double quotes, unless single quotes are necessary.
* Never define local variables with `def`; explicitly define the types, even for
closure arguments.
* Dependencies:
	* There is usually no need to declare `transitive: false` for
	`compileInclude` or `provided` dependencies; this is the default behavior.
	* If a module only includes unit tests, add all dependencies to the
	`testCompile` configuration. If a module only includes integration tests,
	add all dependencies to the `testIntegrationCompile` configuration.
	* Always sort dependencies alphabetically.
	* Separate dependencies of different configurations with an empty line.
* Ordering inside Gradle files:
	1. Class imports, sorted and separated in groups (same logic used in
	Java).
	2. `apply plugin` logic, sorted alphabetically.
	3. `ext { ... }` block.
	4. Task creation: `task taskName(type: TaskType)` or simply `task taskName`
	for default tasks. Don't declare the task dependencies here.
	5. Project property assignments (e.g., `sourceCompatibility`).
	6. Variables used globally by the whole script, like a URL or a relative
	path.
	7. Blocks `{ ... }` to configure tasks, extension objects, etc. These must be
	sorted alphabetically.
* Inside a block `{ ... }`:
	* If variables are needed, declare them inside the block at the beginning.
	* If setting a property, use the `=` assignment, even if Gradle doesn't
	complain when it's not used.
	* If multiple assignments are necessary (for example, multiple `dependsOn`
	or multiple `excludes` declarations), write them on separate lines.
	* Order assignments alphabetically, leaving an empty line after multiple
	calls to the same method (e.g., after multiple `dependsOn` declarations) or if
	the assignment has a closure.

## Subrepositories

### `gradle.properties`

Property Name | Mandatory | Description
------------- | --------- | -----------
`com.liferay.source.formatter.version` | No | The version of Source Formatter to use in the subrepository. If the property is not set, the latest version is used.
`project.group` | No | The group ID of the artifacts the are published from the subrepository. If this property is not set, the default value `com.liferay` is used.
`project.path.prefix` | Yes | The project path of the Gradle prefix. It must start with a `':'` character and be equal to the relative path of the subrepository directory inside the main Liferay repository, with path components separated by `':'` character instead of slashes.
`systemProp.repository.private.password` | No | The password used to access the private Maven repository. If set, this property must be equal to the value of the property `build.repository.private.password` in `build.properties`.
`systemProp.repository.private.url` | No | The URL of the private Maven repository. If set, this property must be equal to the value of the property `build.repository.private.url` in `build.properties`.
`systemProp.repository.private.username` | No | The user name used to access the private private Maven repository. If set, this property must be equal to the value of the property `build.repository.private.username` in `build.properties`.