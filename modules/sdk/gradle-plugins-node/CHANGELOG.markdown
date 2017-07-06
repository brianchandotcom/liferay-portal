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
[LPS-73472]: https://issues.liferay.com/browse/LPS-73472