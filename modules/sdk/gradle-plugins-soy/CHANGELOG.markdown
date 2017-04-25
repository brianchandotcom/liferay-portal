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

## 3.0.0 - 2017-01-12

### Added
- [LPS-70092]: Support translation replacement for version 2 of the
[Command Line Tools for Metal.js].

### Removed
- [LPS-67573]: Remove deprecated `BuildSoyTask` class from the
`com.liferay.gradle.plugins.soy` package.

## 3.0.1 - 2017-01-13

### Changed
- [LPS-70036]: Reuse logic from [Liferay Portal Tools Soy Builder] in
`ReplaceSoyTranslationTask`.

## 3.1.0 - 2017-02-13

### Added
- [LPS-69139]: Add task `wrapSoyAlloyTemplate` (disabled by default) to wrap the
JavaScript functions compiled from Closure Templates into AlloyUI modules.

### Changed
- [LPS-69139]: Update the [Liferay Portal Tools Soy Builder] dependency to
version 2.0.0.

## 3.1.1 - 2017-04-18

### Changed
- [LPS-69139]: Reuse logic from [Liferay Portal Tools Soy Builder] in
`BuildSoyTask`.
- [LPS-69139]: Update the [Liferay Portal Tools Soy Builder] dependency to
version 3.0.0.

## 3.1.2 - 2017-04-25

### Changed
- [LPS-69139]: Update the [Liferay Portal Tools Soy Builder] dependency to
version 3.0.1.

[Command Line Tools for Metal.js]: https://github.com/metal/metal-cli
[Liferay Portal Tools Soy Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-soy-builder
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67766]: https://issues.liferay.com/browse/LPS-67766
[LPS-69139]: https://issues.liferay.com/browse/LPS-69139
[LPS-70036]: https://issues.liferay.com/browse/LPS-70036
[LPS-70092]: https://issues.liferay.com/browse/LPS-70092