# Liferay Gradle Plugins Node Change Log

## 1.0.22 - 2016-08-26

### Changed
- [LPS-67023]: A `DownloadNodeModuleTask` task is automatically disabled if the
following conditions are met:
	- the task is configured to download in the project's `node_modules`
	directory
	- the project has a `package.json`
	- the `package.json` file contains the same module name in the
	`dependencies` or `devDependencies` object

[LPS-67023]: https://issues.liferay.com/browse/LPS-67023