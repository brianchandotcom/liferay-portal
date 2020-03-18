# Bug Prevention Checks

Check | File Extensions | Description
----- | --------------- | -----------
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | .java | Checks that there are no import statements that use the * notation. |
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | .bnd | Validates property value for `Bundle-Activator` |
[BNDBundleCheck](checks/bnd_bundle_check.markdown#bndbundlecheck) | .bnd | Validates `Liferay-Releng-*` properties |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName` |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | .bnd | Validates definition keys in `.bnd` files |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | .bnd | Checks if the directory names of the submodules match the parent module name |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | .bnd | Checks for unnesecarry including of `test-classes/integration` |
[BNDMultipleAppBNDsCheck](checks/bnd_multiple_app_bnds_check.markdown#bndmultipleappbndscheck) | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module) |
[BNDRangeCheck](checks/bnd_range_check.markdown#bndrangecheck) | .bnd | Checks for use or range expressions |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion` |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | .gradle | Checks that value `default` is not used for attribute `version` |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no array functions |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | .java | Checks that variables in `Tag` classes get cleaned up properly |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | .java | Checks for correct use of `Collator` |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration` |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web` |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath` |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | .java | Checks that the type gets returned when using annotation `@Indexable` |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | .java | Checks that `java.text.Collator` is used when comparing localized values |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta` |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | .java | Checks for use of `@Component` in `-api` or `-spi` modules |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | .java | Checks for exposure of SPI types in API |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | .java | Checks package names in tests |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | .java | Checks that the package name matches the file location |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID` |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | .java | Checks for correct use `java.sql.ResultSet.getInt(int)` |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | .java | Checks for nested annotations inside `@see` |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | .java | Checks for potential NullPointerException when using `StopWatch` |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | .java | Checks for potential ClassCastException |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | .java | Performs several checks on Upgrade classes |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | .java | Checks that there are no stale references in service code from persistence updates |
[PropertiesLanguageKeysCheck](checks/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | .prettierignore or .properties | Checks that there is no HTML markup in language keys |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle` |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | .sql | Checks for table and column names that exceed 30 characters |
[XMLServiceEntityNameCheck](checks/xml_service_entity_name_check.markdown#xmlserviceentitynamecheck) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | Chceks that the entity name in `service.xml` does not equal the package name |