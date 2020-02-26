# Bug Prevention Checks

Check | File Extensions | Description
----- | --------------- | -----------
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | .bnd | Validates property value for `Bundle-Activator` |
BNDBundleCheck | .bnd | Validates `Liferay-Releng-*` properties |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName` |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | .bnd | Validates definition keys in `.bnd` files |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | .bnd | Checks if the directory names of the submodules match the parent module name |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | .bnd | Checks for unnesecarry including of `test-classes/integration` |
BNDMultipleAppBNDsCheck | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module) |
BNDRangeCheck | .bnd | Checks for use or range expressions |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion` |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | .java | Checks for potential ClassCastException |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | .sql | Checks for table and column names that exceed 30 characters |