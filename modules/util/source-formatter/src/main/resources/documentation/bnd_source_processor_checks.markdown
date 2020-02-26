# Checks for .bnd

Check | Category | Description
----- | -------- | -----------
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | Bug Prevention | Validates property value for `Bundle-Activator` |
BNDBundleCheck | Bug Prevention | Validates `Liferay-Releng-*` properties |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | Bug Prevention | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName` |
BNDCapabilityCheck | Styling | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability` |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | Bug Prevention | Validates definition keys in `.bnd` files |
BNDDeprecatedAppBNDsCheck | Miscellaneous | Checks for redundant `app.bnd` in deprecated or archived modules |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | Bug Prevention | Checks if the directory names of the submodules match the parent module name |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | Bug Prevention | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages |
BNDImportsCheck | Styling | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package` |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | Bug Prevention | Checks for unnesecarry including of `test-classes/integration` |
BNDLineBreaksCheck | Styling | Checks for incorrect/missing line breaks |
BNDMultipleAppBNDsCheck | Bug Prevention | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module) |
BNDRangeCheck | Bug Prevention | Checks for use or range expressions |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | Bug Prevention | Checks for incorrect use of property `Liferay-Require-SchemaVersion` |
BNDStylingCheck | Styling | Applies rules to enforce consisteny in code style |
BNDSuiteCheck | Miscellaneous | Checks that deprecated apps are moved to the `archived` folder |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | Bug Prevention | Checks if the property value for `Web-ContextPath` matches the module directory |
BNDWhitespaceCheck | Styling | Checks for incorrect/missing line whitespace |