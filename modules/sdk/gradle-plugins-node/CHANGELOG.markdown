# Liferay Gradle Plugins Node Change Log

## 1.0.1 - 2015-09-08

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.

## 1.0.7 - 2015-11-03

### Dependencies
- [LPS-60153]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.13 - 2016-04-21

### Commits
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update build.gradle of Node-based plugins (176b68ee4f)

### Dependencies
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.16 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.22 - 2016-08-27

### Commits
- [LPS-67658]: Configure GradleTest in gradle-plugins-node (3bea1444af)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Description
- [LPS-67023]: A `DownloadNodeModuleTask` task is automatically disabled if the
following conditions are met:
	- The task is configured to download in the project's `node_modules`
	directory.
	- The project has a `package.json` file.
	- The `package.json` file contains the same module name in the
	`dependencies` or `devDependencies` object.

## 1.1.0 - 2016-09-20

### Description
- [LPS-66906]: Add property `inheritProxy` to all tasks that extend
`ExecuteNodeTask`. If `true`, the Java proxy system properties are passed to
Node.js via the environment variables `http_proxy`, `https_proxy`, and
`no_proxy`.
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-67573]: Move utility classes to the
`com.liferay.gradle.plugins.node.internal` package.

## 1.1.1 - 2016-10-06

### Commits
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)

## 1.2.0 - 2016-10-06

### Description
- [LPS-68564]: Add task `npmShrinkwrap` to call `npm shrinkwrap` and exclude
unwanted dependencies from the generated `npm-shrinkwrap.json` file.

## 1.3.0 - 2016-10-21

### Description
- [LPS-66906]: Add the ability to use callables and closures as a value for the
`removeShrinkwrappedUrls` property of `NpmInstallTask`.
- [LPS-66906]: Set the `removeShrinkwrappedUrls` property of all tasks that
extend `NpmInstallTask` to `true` by default if the property `registry` has a
value.

## 1.3.1 - 2016-11-29

### Commits
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)

## 1.4.0 - 2016-11-29

### Description
- [LPS-69445]: Add the `useGradleExec` property to all tasks that extend
`ExecuteNodeTask`. If `true`, Node.js is invoked via
[`project.exec`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:exec(org.gradle.api.Action)),
which can solve hanging problems with the Gradle Daemon.

## 1.4.1 - 2016-12-08

### Description
- [LPS-69618]: Disable the up-to-date check for the `npmInstall` task.

## 1.4.2 - 2016-12-14

### Description
- [LPS-69677]: Fix problem with `ExecuteNpmTask` when `node.download = false`.

## 1.5.0 - 2016-12-21

### Description
- [LPS-69802]: Add the task `cleanNPM` to delete the `node_modules` directory
and the `npm-shrinkwrap.json` file from the project, if present.
- [LPS-69802]: Execute the `cleanNPM` task before generating the
`npm-shrinkwrap.json` file via the `npmShrinkwrap` task.

## 1.5.1 - 2016-12-29

### Description
- [LPS-69920]: Retry `npm install` automatically if it fails.

## 1.5.2 - 2017-02-09

### Commits
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)

### Description
- [LPS-69920]: Remove up-to-date check from all tasks that extend
`NpmInstallTask`.

## 2.0.0 - 2017-02-23

### Description
- [LPS-69920]: Fix duplicated NPM arguments while retrying `npm install` in case
of failure.
- [LPS-70870]: Fix Node.js download with authenticated proxies.

## 2.0.1 - 2017-03-09

### Description
- [LPS-70634]: Reuse the `package.json` file of a project, if it exists, while
executing the `PublishNodeModuleTask`.

## 2.0.2 - 2017-03-13

### Description
- [LPS-71222]: Always sort the generated `npm-shrinkwrap.json` files.

## 2.1.0 - 2017-04-11

### Description
- [LPS-71826]: Add the ability to set the NPM log level by setting the property
`logLevel` of `ExecuteNPMTask`.

## 2.2.0 - 2017-04-25

### Description
- [LPS-72152]: Add property `npmUrl` to all tasks that extend
`DownloadNodeTask`. If set, it downloads a specific version of NPM to override
the one that comes with the Node.js installation.
- [LPS-72152]: Add properties `npmUrl` and `npmVersion` to the `node` extension
object. By default, `npmUrl` is equal to
`https://registry.npmjs.org/npm/-/npm-${node.npmVersion}.tgz`. These properties
let you set a specific version of NPM to download with the `downloadNode` task.

## 2.2.1 - 2017-05-03

### Description
- [LPS-72340]: Skip task `npmShrinkwrap` if project does not contain a
`package.json` file.

## 2.2.2 - 2017-07-07

### Dependencies
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.3.0 - 2017-07-07

### Description
- [LPS-73472]: Add a `npmRum[script]` task for each
[script](https://docs.npmjs.com/misc/scripts) declared in the `package.json`
file.
- [LPS-73472]: Run the `"build"` script (if declared in the `package.json` file)
when compiling a Java project.

## 2.3.1 - 2017-07-17

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.

## 3.0.0 - 2017-07-17

### Description
- [LPS-73472]: Recreate symbolic links in the `.bin` directories of
`node_modules` if the `NpmInstallTask`'s `nodeModulesCacheDir` property is set.
- [LPS-73472]: Remove all deprecated methods.
- [LPS-73472]: The `NpmInstallTask`'s `nodeModulesCacheRemoveBinDirs` property
is no longer available.

## 3.1.0 - 2017-08-29

### Description
- [LPS-73070]: Add the ability to run `ExecuteNpmTask` instances concurrently
even when pointing to a shared NPM's cache directory, if supported.
- [LPS-73070]: By default, use the current user's NPM cache directory
concurrently if running on NPM 5. Prior versions of NPM do not allow for
concurrent access to the cache directory; only one NPM invocation at a time.
- [LPS-73070]: Delete the `package-lock.json` file when running the `cleanNPM`
task, if present.
- [LPS-73070]: Use the `package-lock.json` file to calculate the `node_modules`
cache digest.
- [LPS-73472]: Remove spurious files before recreating symbolic links in the
`.bin` directories of `node_modules`.

## 3.1.1 - 2017-09-18

### Description
- [LPS-74770]: Run the `"test"` script (if declared in the `package.json` file)
when executing the `check` task.

## 3.2.0 - 2017-09-28

### Description
- [LPS-74933]: Add the ability to merge the existing `package.json` of the
project with the values provided by the task properties of
`PublishNodeModuleTask` when publishing a package to the NPM registry.

## 3.2.1 - 2017-10-10

### Description
- [LPS-75175]: Fix the `downloadNode` task's circular dependency when setting
the `node.global` property to `true` in the root project.

## 4.0.0 - 2017-11-20

### Description
- [LPS-75965]: Download the Node.js Windows distribution if running on Windows.
- [LPS-75965]: The `downloadNode.nodeExeUrl` and `node.nodeExeUrl` properties
are no longer available.

## 4.0.1 - 2018-01-02

### Description
- [LPS-74904]: Fail the build if all retries configured in the
`npmInstallRetries` property of an `ExecuteNodeTask` instance have been
exhausted.

## 4.0.2 - 2018-01-17

### Commits
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)

## 4.1.0 - 2018-02-08

### Description
- [LPS-69802]: Add the task `npmPackageLock` to delete the NPM files and run
`npm install` to install the dependencies declared in the project's
`package.json` file, if present.

## 4.2.0 - 2018-02-13

### Description
- [LPS-77996]: Add the property `nodeModulesDigestFile`. If this property is
set, the digest is compared with the `node_modules` directory's digest. If they
don't match, the `node_modules` directory is deleted before running
`npm install`.

## 4.2.1 - 2018-03-15

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 4.3.0 - 2018-03-15

### Description
- [LPS-78741]: Add the property `useNpmCI` to the `NpmInstallTask`. If `true`,
run `npm ci` instead of `npm install`.
- [LPS-73472]: Allow single `"bin"` values in the `package.json` files.

## 4.3.1 - 2018-03-22

### Description
- [LPS-78741]: Do not run `npm install` if the `nodeModulesDigestFile` matches
the `node_modules` directory's digest.

## 4.3.2 - 2018-03-30

### Description
- [LPS-78741]: Do not run `npm ci` if the `nodeModulesDigestFile` matches the
`node_modules` directory's digest.

## 4.3.3 - 2018-04-05

### Description
- [LPS-78741]: Fix the `npmPackageLock` task execution when the `npmInstall`
task's `useNpmCI` property is set to `true`.

## 4.3.4 - 2018-05-07

### Description
- [LPS-75530]: Define task inputs and outputs for `NpmInstallTask`.

## 4.3.5 - 2018-06-08

### Description
- [LPS-82130]: Fix the broken `npm` symbolic link.

## 4.4.0 - 2018-06-22

### Description
- [LPS-82568]: Add the property `environment` to all tasks that extend
`ExecuteNodeTask`. This provides a way to set environment variables.

## 4.4.1 - 2018-10-03

### Commits
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

### Description
- [LPS-85959]: Delete the NPM cached data before retrying `npm install`.

## 4.4.2 - 2018-10-09

### Description
- [LPS-85959]: Verify the NPM cached data before retrying `npm install`.

## 4.4.3 - 2018-10-22

### Description
- [LPS-86576]: Node.js provides Windows binaries bundled with NPM and Node.js
beginning from version 6.2.1. Download and install Node.js and NPM separately
for Node.js versions 5.5.0 - 6.2.0.

## 4.4.4 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 4.5.0 - 2018-11-16

### Description
- [LPS-87465]: Add the property `production` to all tasks that extend
`ExecuteNodeTask`. If `true`,
[`devDependencies`](https://docs.npmjs.com/files/package.json#devdependencies)
are not installed when running `npm install` without any arguments and sets
`NODE_ENV=production` for lifecycle scripts.

## 4.5.1 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 4.5.2 - 2019-01-07

### Commits
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

## 4.6.0 - 2019-01-07

### Description
- [LPS-87479]: Update the [Liferay Gradle Util] dependency to version 1.0.33.
- [LPS-87479]: Add inputs and outputs for tasks of type `NpmRunTask` to skip
rerunning the task if none of the inputs or outputs have changed.
- [LPS-87479]: Update the inputs for tasks of type `NpmInstallTask` to use the
`nodeModulesDigestFile` instead of the `node_modules` directory as an input.

## 4.6.1 - 2019-01-09

### Description
- [LPS-88909]: The `processResources` task will skip overwriting files ending
with `.es.js` if the `npmRun.sourceDigestFile` matches the `npmRun.sourceFiles`
digest to preserve changes made by the `npmRun` task.

## 4.6.2 - 2019-01-09

### Description
- [LPS-87479]: Set the up-to-date check for `npmRun` tasks to `true` if the
classes directory does not exist for Java projects.

## 4.6.3 - 2019-01-14

### Description
- [LPS-89126]: Fix failures in parallel builds by updating the outputs for tasks
of type `NpmInstallTask` to check the `nodeModulesDir` instead of the
`nodeModulesDigestFile`.

## 4.6.4 - 2019-01-16

### Description
- [LPS-88909]: If the `npmRun.sourceDigestFile` matches the `npmRun.sourceFiles`
digest, all files ending with the `js` extension in the classes directory will
be copied before the `processResources` task runs and then copied back to
preserve changes made by the `npmRun` task.

## 4.6.5 - 2019-01-24

### Description
- [LPS-89369]: The `npmRun` task should fail when there are Soy compile errors.
- [LPS-89436]: Add Soy files as inputs for tasks of type `NpmRunTask`.

## 4.6.18 - 2019-06-21

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.