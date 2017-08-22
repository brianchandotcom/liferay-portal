# Liferay Gradle Plugins Lang Builder Change Log

## 2.0.0 - 2017-04-06

### Added
- [LPS-71375]: Add the property `translateSubscriptionKey` in `BuildLangTask` to
support the Translator Text Translation API on Microsoft Cognitive Services.

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Removed
- [LPS-71375]: The properties `translateClientId` and `translateClientSecret` of
`BuildLangTask` are no longer available.

## 2.1.0 - 2017-08-21

### Added
- [LPS-74250]: Add the property `excludedLanguageIds` in `BuildLangTask` to
configure which language IDs to exclude in the automatic translation.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-71375]: https://issues.liferay.com/browse/LPS-71375
[LPS-74250]: https://issues.liferay.com/browse/LPS-74250