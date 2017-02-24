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

[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677
[LPS-69802]: https://issues.liferay.com/browse/LPS-69802
[LPS-69920]: https://issues.liferay.com/browse/LPS-69920
[LPS-70870]: https://issues.liferay.com/browse/LPS-70870