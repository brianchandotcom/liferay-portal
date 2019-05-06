# Liferay Gradle Plugins Node Change Log

## 1.0.22 - 2016-08-27

### Changed
- [LPS-67023]: A `DownloadNodeModuleTask` task is automatically disabled if the
following conditions are met:
	- The task is configured to download in the project's `node_modules`
	directory.
	- The project has a `package.json` file.
	- The `package.json` file contains the same module name in the
	`dependencies` or `devDependencies` object.

## 1.1.0 - 2016-09-20

### Added
- [LPS-66906]: Add property `inheritProxy` to all tasks that extend
`ExecuteNodeTask`. If `true`, the Java proxy system properties are passed to
Node.js via the environment variables `http_proxy`, `https_proxy`, and
`no_proxy`.

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-67573]: Move utility classes to the
`com.liferay.gradle.plugins.node.internal` package.

## 1.2.0 - 2016-10-06

### Added
- [LPS-68564]: Add task `npmShrinkwrap` to call `npm shrinkwrap` and exclude
unwanted dependencies from the generated `npm-shrinkwrap.json` file.

## 1.3.0 - 2016-10-21

### Added
- [LPS-66906]: Add the ability to use callables and closures as a value for the
`removeShrinkwrappedUrls` property of `NpmInstallTask`.

### Changed
- [LPS-66906]: Set the `removeShrinkwrappedUrls` property of all tasks that
extend `NpmInstallTask` to `true` by default if the property `registry` has a
value.

## 1.4.0 - 2016-11-29

### Added
- [LPS-69445]: Add the `useGradleExec` property to all tasks that extend
`ExecuteNodeTask`. If `true`, Node.js is invoked via [`project.exec`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:exec(org.gradle.api.Action)),
which can solve hanging problems with the Gradle Daemon.

## 1.4.1 - 2016-12-08

### Fixed
- [LPS-69618]: Disable the up-to-date check for the `npmInstall` task.

## 1.4.2 - 2016-12-14

### Fixed
- [LPS-69677]: Fix problem with `ExecuteNpmTask` when `node.download = false`.

## 1.5.0 - 2016-12-21

### Added
- [LPS-69802]: Add the task `cleanNPM` to delete the `node_modules` directory
and the `npm-shrinkwrap.json` file from the project, if present.
- [LPS-69802]: Execute the `cleanNPM` task before generating the
`npm-shrinkwrap.json` file via the `npmShrinkwrap` task.

## 1.5.1 - 2016-12-29

### Added
- [LPS-69920]: Retry `npm install` automatically if it fails.

## 1.5.2 - 2017-02-09

### Removed
- [LPS-69920]: Remove up-to-date check from all tasks that extend
`NpmInstallTask`.

## 2.0.0 - 2017-02-23

### Fixed
- [LPS-69920]: Fix duplicated NPM arguments while retrying `npm install` in case
of failure.
- [LPS-70870]: Fix Node.js download with authenticated proxies.

## 2.0.1 - 2017-03-09

### Changed
- [LPS-70634]: Reuse the `package.json` file of a project, if it exists, while
executing the `PublishNodeModuleTask`.

## 2.0.2 - 2017-03-13

### Changed
- [LPS-71222]: Always sort the generated `npm-shrinkwrap.json` files.

## 2.1.0 - 2017-04-11

### Added
- [LPS-71826]: Add the ability to set the NPM log level by setting the property
`logLevel` of `ExecuteNPMTask`.

## 2.2.0 - 2017-04-25

### Added
- [LPS-72152]: Add property `npmUrl` to all tasks that extend
`DownloadNodeTask`. If set, it downloads a specific version of NPM to override
the one that comes with the Node.js installation.
- [LPS-72152]: Add properties `npmUrl` and `npmVersion` to the `node` extension
object. By default, `npmUrl` is equal to
`https://registry.npmjs.org/npm/-/npm-${node.npmVersion}.tgz`. These properties
let you set a specific version of NPM to download with the `downloadNode` task.

## 2.2.1 - 2017-05-03

### Fixed
- [LPS-72340]: Skip task `npmShrinkwrap` if project does not contain a
`package.json` file.

## 2.3.0 - 2017-07-07

### Added
- [LPS-73472]: Add a `npmRum[script]` task for each [script](https://docs.npmjs.com/misc/scripts)
declared in the `package.json` file.
- [LPS-73472]: Run the `"build"` script (if declared in the `package.json` file)
when compiling a Java project.

## 3.0.0 - 2017-07-17

### Added
- [LPS-73472]: Recreate symbolic links in the `.bin` directories of
`node_modules` if the `NpmInstallTask`'s `nodeModulesCacheDir` property is set.

### Removed
- [LPS-73472]: Remove all deprecated methods.
- [LPS-73472]: The `NpmInstallTask`'s `nodeModulesCacheRemoveBinDirs` property
is no longer available.

## 3.1.0 - 2017-08-29

### Added
- [LPS-73070]: Add the ability to run `ExecuteNpmTask` instances concurrently
even when pointing to a shared NPM's cache directory, if supported.
- [LPS-73070]: By default, use the current user's NPM cache directory
concurrently if running on NPM 5. Prior versions of NPM do not allow for
concurrent access to the cache directory; only one NPM invocation at a time.
- [LPS-73070]: Delete the `package-lock.json` file when running the `cleanNPM`
task, if present.
- [LPS-73070]: Use the `package-lock.json` file to calculate the `node_modules`
cache digest.

### Fixed
- [LPS-73472]: Remove spurious files before recreating symbolic links in the
`.bin` directories of `node_modules`.

## 3.1.1 - 2017-09-18

### Added
- [LPS-74770]: Run the `"test"` script (if declared in the `package.json` file)
when executing the `check` task.

## 3.2.0 - 2017-09-28

### Added
- [LPS-74933]: Add the ability to merge the existing `package.json` of the
project with the values provided by the task properties of
`PublishNodeModuleTask` when publishing a package to the NPM registry.

## 3.2.1 - 2017-10-10

### Fixed
- [LPS-75175]: Fix the `downloadNode` task's circular dependency when setting
the `node.global` property to `true` in the root project.

## 4.0.0 - 2017-11-20

### Changed
- [LPS-75965]: Download the Node.js Windows distribution if running on Windows.

### Removed
- [LPS-75965]: The `downloadNode.nodeExeUrl` and `node.nodeExeUrl` properties
are no longer available.

## 4.0.1 - 2018-01-02

### Fixed
- [LPS-74904]: Fail the build if all retries configured in the
`npmInstallRetries` property of an `ExecuteNodeTask` instance have been
exhausted.

## 4.0.2 - 2018-01-17

*No changes.*

## 4.1.0 - 2018-02-08

### Added
- [LPS-69802]: Add the task `npmPackageLock` to delete the NPM files and run
`npm install` to install the dependencies declared in the project's
`package.json` file, if present.

## 4.2.0 - 2018-02-13

### Added
- [LPS-77996]: Add the property `nodeModulesDigestFile`. If this property is
set, the digest is compared with the `node_modules` directory's digest. If they
don't match, the `node_modules` directory is deleted before running
`npm install`.

## 4.3.0 - 2018-03-15

### Added
- [LPS-78741]: Add the property `useNpmCI` to the `NpmInstallTask`. If `true`,
run `npm ci` instead of `npm install`.

### Fixed
- [LPS-73472]: Allow single `"bin"` values in the `package.json` files.

## 4.3.1 - 2018-03-22

### Fixed
- [LPS-78741]: Do not run `npm install` if the `nodeModulesDigestFile` matches
the `node_modules` directory's digest.

## 4.3.2 - 2018-03-30

### Changed
- [LPS-78741]: Do not run `npm ci` if the `nodeModulesDigestFile` matches the
`node_modules` directory's digest.

## 4.3.3 - 2018-04-05

### Fixed
- [LPS-78741]: Fix the `npmPackageLock` task execution when the `npmInstall`
task's `useNpmCI` property is set to `true`.

## 4.3.4 - 2018-05-07

### Added
- [LPS-75530]: Define task inputs and outputs for `NpmInstallTask`.

## 4.3.5 - 2018-06-08

### Fixed
- [LPS-82130]: Fix the broken `npm` symbolic link.

## 4.4.0 - 2018-06-22

### Added
- [LPS-82568]: Add the property `environment` to all tasks that extend
`ExecuteNodeTask`. This provides a way to set environment variables.

## 4.4.1 - 2018-10-03

### Changed
- [LPS-85959]: Delete the NPM cached data before retrying `npm install`.

## 4.4.2 - 2018-10-09

### Changed
- [LPS-85959]: Verify the NPM cached data before retrying `npm install`.

## 4.4.3 - 2018-10-22

### Fixed
- [LPS-86576]: Node.js provides Windows binaries bundled with NPM and Node.js
beginning from version 6.2.1. Download and install Node.js and NPM separately
for Node.js versions 5.5.0 - 6.2.0.

## 4.5.0 - 2018-11-16

### Added
- [LPS-87465]: Add the property `production` to all tasks that extend
`ExecuteNodeTask`. If `true`, [`devDependencies`](https://docs.npmjs.com/files/package.json#devdependencies)
are not installed when running `npm install` without any arguments and sets
`NODE_ENV=production` for lifecycle scripts.

## 4.6.0 - 2019-01-07

### Changed
- [LPS-87479]: Update the [Liferay Gradle Util] dependency to version 1.0.33.
- [LPS-87479]: Add inputs and outputs for tasks of type `NpmRunTask` to skip
rerunning the task if none of the inputs or outputs have changed.
- [LPS-87479]: Update the inputs for tasks of type `NpmInstallTask` to use the
`nodeModulesDigestFile` instead of the `node_modules` directory as an input.

## 4.6.1 - 2019-01-09

### Changed
- [LPS-88909]: The `processResources` task will skip overwriting files ending
with `.es.js` if the `npmRun.sourceDigestFile` matches the `npmRun.sourceFiles`
digest to preserve changes made by the `npmRun` task.

## 4.6.2 - 2019-01-09

### Changed
- [LPS-87479]: Set the up-to-date check for `npmRun` tasks to `true` if the
classes directory does not exist for Java projects.

## 4.6.3 - 2019-01-14

### Fixed
- [LPS-89126]: Fix failures in parallel builds by updating the outputs for tasks
of type `NpmInstallTask` to check the `nodeModulesDir` instead of the
`nodeModulesDigestFile`.

## 4.6.4 - 2019-01-16

### Fixed
- [LPS-88909]: If the `npmRun.sourceDigestFile` matches the `npmRun.sourceFiles`
digest, all files ending with the `js` extension in the classes directory will
be copied before the `processResources` task runs and then copied back to
preserve changes made by the `npmRun` task.

## 4.6.5 - 2019-01-24

### Added
- [LPS-89369]: The `npmRun` task should fail when there are Soy compile errors.

### Changed
- [LPS-89436]: Add Soy files as inputs for tasks of type `NpmRunTask`.

[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677
[LPS-69802]: https://issues.liferay.com/browse/LPS-69802
[LPS-69920]: https://issues.liferay.com/browse/LPS-69920
[LPS-70634]: https://issues.liferay.com/browse/LPS-70634
[LPS-70870]: https://issues.liferay.com/browse/LPS-70870
[LPS-71222]: https://issues.liferay.com/browse/LPS-71222
[LPS-71826]: https://issues.liferay.com/browse/LPS-71826
[LPS-72152]: https://issues.liferay.com/browse/LPS-72152
[LPS-72340]: https://issues.liferay.com/browse/LPS-72340
[LPS-73070]: https://issues.liferay.com/browse/LPS-73070
[LPS-73472]: https://issues.liferay.com/browse/LPS-73472
[LPS-74770]: https://issues.liferay.com/browse/LPS-74770
[LPS-74904]: https://issues.liferay.com/browse/LPS-74904
[LPS-74933]: https://issues.liferay.com/browse/LPS-74933
[LPS-75175]: https://issues.liferay.com/browse/LPS-75175
[LPS-75530]: https://issues.liferay.com/browse/LPS-75530
[LPS-75965]: https://issues.liferay.com/browse/LPS-75965
[LPS-77996]: https://issues.liferay.com/browse/LPS-77996
[LPS-78741]: https://issues.liferay.com/browse/LPS-78741
[LPS-82130]: https://issues.liferay.com/browse/LPS-82130
[LPS-82568]: https://issues.liferay.com/browse/LPS-82568
[LPS-85959]: https://issues.liferay.com/browse/LPS-85959
[LPS-86576]: https://issues.liferay.com/browse/LPS-86576
[LPS-87465]: https://issues.liferay.com/browse/LPS-87465
[LPS-87479]: https://issues.liferay.com/browse/LPS-87479
[LPS-88909]: https://issues.liferay.com/browse/LPS-88909
[LPS-89126]: https://issues.liferay.com/browse/LPS-89126
[LPS-89369]: https://issues.liferay.com/browse/LPS-89369
[LPS-89436]: https://issues.liferay.com/browse/LPS-89436