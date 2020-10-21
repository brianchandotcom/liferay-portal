# Checks for .bnd

Check | Category | Description
----- | -------- | -----------
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates property value for `Bundle-Activator`. |
[BNDBundleCheck](checks/bnd_bundle_check.markdown#bndbundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates `Liferay-Releng-*` properties. |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`. |
BNDCapabilityCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability`. |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates definition keys in `.bnd` files. |
[BNDDeprecatedAppBNDsCheck](checks/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks for redundant `app.bnd` in deprecated or archived modules. |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks if the directory names of the submodules match the parent module name. |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages. |
[BNDImportsCheck](checks/bnd_imports_check.markdown#bndimportscheck) | [Styling](styling_checks.markdown#styling-checks) | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package`. |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for unnesecarry including of `test-classes/integration`. |
[BNDLiferayEnterpriseAppCheck](checks/bnd_liferay_enterprise_app_check.markdown#bndliferayenterpriseappcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use of property `Liferay-Enterprise-App`. |
[BNDLiferayRelengCategoryCheck](checks/bnd_liferay_releng_category_check.markdown#bndliferayrelengcategorycheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates `Liferay-Releng-Category` properties |
BNDLineBreaksCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary line breaks in `.bnd` files. |
[BNDMultipleAppBNDsCheck](checks/bnd_multiple_app_bnds_check.markdown#bndmultipleappbndscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module). |
[BNDRangeCheck](checks/bnd_range_check.markdown#bndrangecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for use or range expressions. |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for incorrect use of property `Liferay-Require-SchemaVersion`. |
BNDStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in code style. |
[BNDSuiteCheck](checks/bnd_suite_check.markdown#bndsuitecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that deprecated apps are moved to the `archived` folder. |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks if the property value for `Web-ContextPath` matches the module directory. |
BNDWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary whitespace in `.bnd` files. |