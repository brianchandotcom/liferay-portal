# Liferay Modules

The goal of this document is to explain how to use Liferay's build system and
its recommended best practices.

## Build

### Build with Java 8

The default value for source/target compatibility is still Java 7. In order to
use Java 8 features, please add these lines in your `build.gradle`:

```gradle
sourceCompatibility = "1.8"
targetCompatibility = "1.8"
```

It has been decided not to change the default compatibility to Java 8 yet, in
order to limit the use of new features only when necessary. If you need them,
you have to add those lines.

### Deploy Directory

The deploy directory is the `deploy.destinationDir` property (that is, the
`destinationDir` property of the `deploy` task). This property is automatically
set to `liferay.deployDir` (that is, the `deployDir` property of the `liferay`
extension object).

The logic that chooses the default deploy directory is as follows:

1. If the project dir contains a `.lfrbuild-app-server-lib` marker file, the
module is deployed to `${app.server.portal.dir}/WEB-INF/lib`.
2. If the project dir contains a `.lfrbuild-tool` marker file, the module is
deployed to `${liferay.home}/tools/${module.dir.name}`.
3. If the project dir contains a `.lfrbuild-static` marker file, the module is
deployed to `${liferay home}/osgi/static`.
4. If the module symbolic name starts with `com.liferay.portal.`, the module is
deployed to `${liferay home}/osgi/portal`.
5. Otherwise, the module is deployed to `${liferay home}/osgi/modules`.

If possible, you should always use these marker files to specify the deploy
directory of your modules. If none of these cases is correct for you, then add
something like this to your `build.gradle`:

```gradle
liferay {
   deployDir = file("${liferayHome}/osgi/test")
}
```

In order to know what paths like `liferayHome` are available, please have a look
at the getters in the LiferayExtension class.

It is perfectly normal to have both `.lfrbuild-portal` and one of these marker
file in the same project: the first one tells the build system to build the
module with `ant all`, the other ones chooses the deploy directory.

## Marker Files

### Build

File Name | Description
--------- | -----------
`.lfrbuild-portal-pre` | During `ant compile`, builds the module in the `tmp/lib-pre` directory even before building `portal-kernel`, `portal-impl`, etc.
`.lfrbuild-portal` | Deploys the module during a regular `ant all`. `-test` modules never have this file.
`.lfrbuild-slim` | Deploys the module during `ant all` if building a Liferay Slim Runtime.

### Continuous Integration

File Name | Description
--------- | -----------
`.lfrbuild-ci` | Deploys the module during `ant all`, but only if running in Jenkins.
`.lfrbuild-semantic-versioning` | Enables the semantic versioning check of the module on CI. `apps` and `core` modules are already checked, so they don't need this marker file.

### Deploy Directory

File Name | Description
--------- | -----------
`.lfrbuild-app-server-lib` | Deploys module to `${app.server.portal.dir}/WEB-INF/lib`.
`.lfrbuild-static` | Deploys module to `${liferay home}/osgi/static`.
`.lfrbuild-tool` | Deploys module to `${liferay.home}/tools/${module.dir.name}`.

### Release

File Name | Description
--------- | -----------
`.lfrbuild-releng-ignore` | Never considers the module as *stale*, in order to avoid its release.
`.lfrrelease-src` | Added to the root of an app, includes the app's source code in the DXP release.

### Themes

File Name | Description
--------- | -----------
`.lfrbuild-missing-resources-importer` | Added only on the `master `branch, prevents the theme from theme from being published in case it doesn't contain the *Resources Importer* files.

## Source Formatting

### Gradle files

* Always use double quotes, unless necessary.
* Don't use `def`, explicitly define the types, even for closure arguments.
* Dependencies:
	* There is usually no need to declare `transitive: false` for
	`compileInclude` or `provided` dependencies, this is the default behavior.
	* If a module only includes unit tests, add all dependencies to the
	`testCompile` configuration. If a module only includes integration tests,
	add all dependencies to the `testIntegrationCompile` configuration.
	* Always sort dependencies alphabetically.
	* Separate dependencies of different configurations with an empty line.
* Ordering inside Gradle files:
	1. Classes imports, sorted and separated in groups (same logic we use in
	Java)
	2. `apply plugin`, sorted alphabetically
	3. `ext { ... }` block
	4. Task creation: `task taskName(type: TaskType)` or simply `task taskName`
	for default tasks. Don't declare the task dependencies here.
	5. Variables used globally by the whole script, like a URL or a relative
	path
	6. Blocks `{ ... }` to configure tasks, extension objects, etc., sorted
	alphabetically
* Inside a block `{ ... }`:
	* If variables are needed, declare them inside the block, at the beginning.
	* If setting a property, use the `=` assignment even if Gradle doesn't
	complain when not used.
	* If multiple assignments are necessary (for example, multiple `dependsOn`
	or multiple `excludes`), write them on separate lines.
	* Order assignments alphabetically, leaving an empty line after multiple
	calls to the same method (like after multiple `dependsOn`) or if the
	assignment has a closure.
