# Liferay Gradle Plugins Soy Change Log

## 2.0.0 - 2016-09-12

### Added
- [LPS-67766]: Add a new `com.liferay.soy.translation` plugin to use a custom
localization mechanism in the generated `.soy.js` files by replacing
`goog.getMsg` definitions with a different function call (e.g.,
`Liferay.Language.get`).

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-67573]: Move `BuildSoyTask` from the `com.liferay.gradle.plugins.soy`
package to the `com.liferay.gradle.plugins.soy.tasks` package.

## 3.0.0 - 2017-01-11

### Added
- [LPS-70092]: Support translation replacement for
[`metal-cli`](https://github.com/metal/metal-cli) 2.

[metal-cli]: https://github.com/metal/metal-cli
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67766]: https://issues.liferay.com/browse/LPS-67766
[LPS-70092]: https://issues.liferay.com/browse/LPS-70092