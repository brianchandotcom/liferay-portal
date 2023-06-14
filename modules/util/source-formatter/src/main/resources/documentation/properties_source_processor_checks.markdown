# Checks for .eslintignore, .prettierignore or .properties

Check | Category | Description
----- | -------- | -----------
PropertiesArchivedModulesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds `test.batch.class.names.includes` property value pointing to archived modules in `test.properties`. |
PropertiesBuildIncludeDirsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies property value of `build.include.dirs` in `build.properties`. |
PropertiesCommentsCheck | [Styling](styling_checks.markdown#styling-checks) | Validates comments in `.properties` files. |
PropertiesDefinitionKeysCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts definition keys in `liferay-plugin-package.properties` file. |
PropertiesDependenciesFileCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts the properties in `dependencies.properties` file. |
PropertiesEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary empty lines. |
PropertiesEnvironmentVariablesCheck | [Documentation](documentation_checks.markdown#documentation-checks) | Verifies that the environment property in the documentation matches the property name. |
PropertiesFeatureFlagsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Generate feature flags in `portal.properties` file. |
PropertiesImportedFilesContentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `imported-files.properties` file. |
[PropertiesLanguageKeysCheck](check/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there is no HTML markup in language keys. |
PropertiesLanguageKeysContextCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks if the language keys include a word of context to indicate specific meaning. |
PropertiesLanguageKeysOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Sort language keys in `Language.properties` file. |
PropertiesLanguageStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in code style. |
PropertiesLiferayPluginPackageFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `liferay-plugin-package.properties` file. |
PropertiesLiferayPluginPackageLiferayVersionsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the version in `liferay-plugin-package.properties` file. |
PropertiesLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds lines that are longer than the specified maximum line length. |
PropertiesMultiLineValuesOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Verifies that property with multiple values is not on a single line. |
PropertiesPortalFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `portal.properties` or `portal-*.properties` file. |
PropertiesPortletFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `portlet.properties` file. |
PropertiesReleaseBuildCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that the information in `release.properties` matches the information in `ReleaseInfo.java`. |
PropertiesSQLStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in code style. |
PropertiesServiceKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds usage of legacy properties in `service.properties`. |
PropertiesSourceFormatterContentCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `source-formatter.properties` file. |
PropertiesSourceFormatterFileCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `source-formatter.properties` file. |
PropertiesStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consistency in code style. |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
PropertiesVerifyPropertiesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds usage of legacy properties in `portal.properties` or `system.properties`. |
PropertiesWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace in `.properties` files. |