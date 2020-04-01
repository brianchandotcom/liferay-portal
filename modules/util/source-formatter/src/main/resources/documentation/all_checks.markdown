# All Checks

Check | Category | File Extensions | Description
----- | -------- | --------------- | -----------
AnnotationParameterOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks the style of elements in annotations. |
AnonymousClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
AppendCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ArquillianCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[ArrayCheck](checks/array_check.markdown#arraycheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the style of array type definitions. |
AssertEqualsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
AttributeOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds nested blocks (blocks that are used freely in the code). |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no import statements that use the * notation. |
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property value for `Bundle-Activator` |
[BNDBundleCheck](checks/bnd_bundle_check.markdown#bndbundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates `Liferay-Releng-*` properties |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName` |
BNDCapabilityCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability` |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Validates definition keys in `.bnd` files |
[BNDDeprecatedAppBNDsCheck](checks/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the directory names of the submodules match the parent module name |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages |
[BNDImportsCheck](checks/bnd_imports_check.markdown#bndimportscheck) | [Styling](styling_checks.markdown#styling-checks) | .bnd | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package` |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for unnesecarry including of `test-classes/integration` |
BNDLineBreaksCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Checks for incorrect/missing line breaks |
[BNDMultipleAppBNDsCheck](checks/bnd_multiple_app_bnds_check.markdown#bndmultipleappbndscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module) |
[BNDRangeCheck](checks/bnd_range_check.markdown#bndrangecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for use or range expressions |
BNDRunInstructionsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | .bndrun | Sorts definition keys alphabetically |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion` |
BNDStylingCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Applies rules to enforce consisteny in code style |
[BNDSuiteCheck](checks/bnd_suite_check.markdown#bndsuitecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .bnd | Checks that deprecated apps are moved to the `archived` folder |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory |
BNDWhitespaceCheck | [Styling](styling_checks.markdown#styling-checks) | .bnd | Checks for incorrect/missing line whitespace |
CDNCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
CQLKeywordCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .cql | |
CSSCommentsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .css or .scss | |
CSSEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .css or .scss | |
CSSHexColorsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .css or .scss | |
CSSImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .css or .scss | |
CSSPropertiesOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .css or .scss | |
CamelCaseNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that chaining is only applied on certain types and methods |
[CodeownersFileLocationCheck](checks/codeowners_file_location_check.markdown#codeownersfilelocationcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | CODEOWNERS | |
CodeownersWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | CODEOWNERS | |
CompatClassImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConcatCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConfigDefinitionKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .cfg or .config | |
ConstantNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConstructorMissingEmptyLineCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ContractionsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[CopyrightCheck](checks/copyright_check.markdown#copyrightcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[CreationMenuBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Check that the default is after all the cases in a switch statement. |
DeprecatedUsageCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
DockerfileEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Dockerfile | |
DockerfileInstructionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Dockerfile | |
EmptyCollectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
EnumConstantDividerCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
EnumConstantOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[ExceptionMessageCheck](checks/message_check.markdown#messagecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ExceptionVariableNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
FTLEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
[FTLIfStatementCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLLiferayVariableOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLStringRelationalOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLTagAttributesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLTagCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FTLWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .ftl | |
FactoryCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
FilterStringWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used |
FullyQualifiedNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[GetterUtilCheck](checks/getter_util_check.markdown#getterutilcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
GradleBlockOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleBodyCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
[GradleDependenciesCheck](checks/gradle_dependencies_check.markdown#gradledependenciescheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .gradle | Checks that value `default` is not used for attribute `version` |
GradleDependencyConfigurationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleDependencyVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleExportedPackageDependenciesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleJavaVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradlePropertiesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleProvidedDependenciesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
[GradleRequiredDependenciesCheck](checks/gradle_required_dependencies_check.markdown#gradlerequireddependenciescheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
GradleStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
[GradleTaskCreationCheck](checks/gradle_task_creation_check.markdown#gradletaskcreationcheck) | [Styling](styling_checks.markdown#styling-checks) | .gradle | Checks that a task is declared on a separate line before the closure |
GradleTestDependencyVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .gradle | |
HTMLEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .html | |
HTMLWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .html | |
[IncorrectFileLocationCheck](checks/incorrect_file_location_check.markdown#incorrectfilelocationcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
InstanceofOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ItemListBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JSLodashDependencyCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .js or .jsx | |
[JSONDeprecatedPackagesCheck](checks/json_deprecated_packages_check.markdown#jsondeprecatedpackagescheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONLineBreakCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONNamingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
JSONPackageJSONBNDVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONPackageJSONCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONPackageJSONDependencyVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONPropertyOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `JSONUtil` |
[JSONValidationCheck](checks/json_validation_check.markdown#jsonvalidationcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
JSONWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .json or .npmbridgerc | |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no array functions |
JSPButtonTagCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | [Performance](performance_checks.markdown#performance-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects` |
JSPEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPExceptionOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPFunctionNameCheck](checks/jsp_function_name_check.markdown#jspfunctionnamecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPIllegalSyntaxCheck](checks/jsp_illegal_syntax_check.markdown#jspillegalsyntaxcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPImportsCheck](checks/jsp_imports_check.markdown#jspimportscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPIncludeCheck](checks/jsp_include_check.markdown#jspincludecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLanguageKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLanguageUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLineBreakCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLogFileNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLogParametersCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPMissingTaglibsCheck](checks/jsp_missing_taglibs_check.markdown#jspmissingtaglibscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPModuleIllegalImportsCheck](checks/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPParenthesesCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPRedirectBackURLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `HttpServletResponse.sendRedirect` from `jsp` files |
[JSPServiceUtilCheck](checks/jsp_service_util_check.markdown#jspserviceutilcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPSessionKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPStringBundlerCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPStringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPSubnameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPTagAttributesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPTaglibVariableCheck](checks/jsp_taglib_variable_check.markdown#jsptaglibvariablecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPUnusedJSPF | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPUnusedTermsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSPXSSVulnerabilitiesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .jsp, .jspf, .tag, .tpl or .vm | |
JSStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .js or .jsx | |
JSWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .js or .jsx | |
Java2HTMLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures |
JavaAbstractMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaAggregateTestRuleParameterOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaAnnotationDefaultAttributeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaAnnotationsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaAnonymousInnerClassCheck](checks/java_anonymous_inner_class_check.markdown#javaanonymousinnerclasscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaAssertEqualsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaBooleanStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaBooleanUsageCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaCleanUpMethodSuperCleanUpCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that variables in `Tag` classes get cleaned up properly |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `Collator` |
[JavaComponentActivateCheck](checks/java_component_activate_check.markdown#javacomponentactivatecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaComponentAnnotationsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration` |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web` |
[JavaConstructorParametersCheck](checks/java_constructor_parameters_check.markdown#javaconstructorparameterscheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that the order of variable assignments matches the order of the parameters in the constructor signature |
JavaConstructorSuperCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaDataAccessConnectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaDeprecatedJavadocCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaDeprecatedKernelClassesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaDeserializationSecurityCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaElseStatementCheck](checks/java_else_statement_check.markdown#javaelsestatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaEmptyLineAfterSuperCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath` |
JavaFinderImplCustomSQLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaForLoopCheck](checks/java_for_loop_check.markdown#javaforloopcheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks if a Enhanced For Loop can be used instead of a Simple For Loop |
[JavaHelperUtilCheck](checks/java_helper_util_check.markdown#javahelperutilcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaHibernateSQLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaIOExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaIllegalImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the type gets returned when using annotation `@Indexable` |
JavaInnerClassImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaInterfaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaInternalPackageCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaJSPDynamicIncludeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that `java.text.Collator` is used when comparing localized values |
JavaLogClassNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaLogLevelCheck](checks/java_log_level_check.markdown#javaloglevelcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaLogParametersCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaLongLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaMapBuilderGenericsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta` |
JavaModifiedServiceMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for use of `@Component` in `-api` or `-spi` modules |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for exposure of SPI types in API |
JavaModuleExtendedObjectClassDefinitionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaModuleIllegalImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaModuleInternalImportsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaModuleJavaxPortletInitParamTemplatePathCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaModuleServiceProxyFactoryCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaModuleServiceReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks package names in tests |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks that we do not concatenate more than 3 String objects |
JavaOSGiReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that the package name matches the file location |
JavaParameterAnnotationsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID` |
JavaProviderTypeAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaRedundantConstructorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaReleaseInfoCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for correct use `java.sql.ResultSet.getInt(int)` |
JavaReturnStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for nested annotations inside `@see` |
JavaServiceImplCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaServiceObjectCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaServiceTrackerFactoryCheck](checks/java_service_tracker_factory_check.markdown#javaservicetrackerfactorycheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class |
JavaSessionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaSignatureParametersCheck](checks/java_signature_parameters_check.markdown#javasignatureparameterscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaSourceFormatterDocumentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStagedModelDataHandlerCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStaticBlockCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaStaticImportsCheck](checks/java_static_imports_check.markdown#javastaticimportscheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks that there are no static imports |
JavaStaticVariableDependencyCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential NullPointerException when using `StopWatch` |
[JavaStringBundlerConcatCheck](checks/java_string_bundler_concat_check.markdown#javastringbundlerconcatcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStringBundlerInitialCapacityCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaSwitchCheck](checks/java_switch_check.markdown#javaswitchcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaSystemEventAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaSystemExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTaglibMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTermDividersCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTermOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaTermStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaTestMethodAnnotationsCheck](checks/java_test_method_annotations_check.markdown#javatestmethodannotationscheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks if methods with test annotations follow the naming conventions |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks for potential ClassCastException |
JavaUnusedSourceFormatterChecksCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Performs several checks on Upgrade classes |
JavaUpgradeConnectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaUpgradeVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaVariableTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaVerifyUpgradeConnectionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavaXMLSecurityCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
JavadocCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | [Styling](styling_checks.markdown#styling-checks) | .java | Validates Javadoc comments to help ensure they are well formed. |
LFRBuildContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .lfrbuild-* | |
LFRBuildReadmeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .lfrbuild-* | |
LPS42924Check | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `lambda` statements are as simple as possible |
LanguageKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .js or .jsx | |
LineBreakAfterCommaCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LineBreakBeforeGenericStartCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LineEndCharacterCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for utilization of class `ListUtil` |
LiteralStringEqualsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local final variable names conform to a specified pattern. |
LocalPatternCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LogMessageCheck](checks/message_check.markdown#messagecheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations |
MarkdownFileExtensionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownSourceFormatterDocumentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownSourceFormatterReadmeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
MarkdownWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .markdown or .md | |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that method names conform to a specified pattern. |
MethodNamingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingAuthorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | [Styling](styling_checks.markdown#styling-checks) | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDeprecatedJavadocCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MissingEmptyLineCheck](checks/missing_empty_line_check.markdown#missingemptylinecheck) | [Styling](styling_checks.markdown#styling-checks) | .java | Checks for missing line breaks around variable declarations |
MissingModifierCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingOverrideCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
MissingParenthesesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that each variable declaration is in its own statement and on its own line. |
NestedIfStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
NewFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace before a token. |
NonbreakingSpaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
NotRequireThisCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
NumberSuffixCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is only one statement per line. |
OperatorOperandCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
OperatorOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the policy on how to wrap lines on operators. |
[PackageNameCheck](https://checkstyle.sourceforge.io/config_naming.html#PackageName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java | Checks that package names conform to a specified pattern. |
PackageinfoBNDExportPackageCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | packageinfo | |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PersistenceCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that there are no stale references in service code from persistence updates |
PlusStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PoshiAnnotationsOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiCommandsOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiParametersOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PoshiWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .function, .macro or .testcase | |
PrimitiveWrapperInstantiationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PrincipalExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PropertiesBuildIncludeDirsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesCommentsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesDefinitionKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesDependenciesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesImportedFilesContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
[PropertiesLanguageKeysCheck](checks/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .prettierignore or .properties | Checks that there is no HTML markup in language keys |
PropertiesLanguageKeysOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLiferayPluginPackageFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLiferayPluginPackageLiferayVersionsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesLongLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortalEnvironmentVariablesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortalFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesPortletFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesServiceKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesSourceFormatterContentCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesSourceFormatterFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesVerifyPropertiesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
PropertiesWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .prettierignore or .properties | |
RedundantBranchingStatementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
RedundantVariableDeclarationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ReferenceAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `java.util.ResourceBundle.getBundle` |
SQLEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .sql | |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .sql | Checks for table and column names that exceed 30 characters |
SQLStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .sql | |
SelfReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SemiColonCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SessionKeysCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SingleStatementClauseCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SizeIsZeroCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
SlantedQuotesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | | |
SoyEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .soy | |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for consistent naming on variables of type 'StringBundler' |
StringCastCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that string literals are not used with == or !=. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Performance](performance_checks.markdown#performance-checks) | .java | Checks if performance can be improved by using different String operation methods |
SubstringCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TLDElementOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .tld | |
TLDTypeCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .tld | |
TernaryOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TestClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ThreadLocalUtilCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
ThreadNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
TransactionalTestRuleCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
TryWithResourcesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[TypeNameCheck](https://checkstyle.sourceforge.io/config_naming.html#TypeName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that type names conform to a specified pattern. |
UnnecessaryAssignCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if unnecessary parentheses are used in a statement or expression. |
UnparameterizedClassCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
UnprocessedExceptionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedMethodCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedParameterCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnusedVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
UnwrappedVariableInfoCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | [Performance](performance_checks.markdown#performance-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)` |
ValidatorIsNullCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | |
VariableDeclarationAsUsedCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
VariableNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
WhitespaceAfterParameterAnnotationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | [Styling](styling_checks.markdown#styling-checks) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is surrounded by whitespace. |
WhitespaceAroundGenericsCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .java | |
WhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .cfg, .config, .cql, .css, .dtd, .gradle, .groovy, .scss, .soy, .sql, .tld, .ts, Dockerfile or packageinfo | |
XMLBuildFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCheckstyleFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCustomSQLOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCustomSQLStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLDDLStructuresFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLFSBExcludeFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLFriendlyURLRoutesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLHBMFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLIndentationCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .tld, .toggle, .wsdl, .xml or .xsd | |
XMLIvyFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLog4jFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLookAndFeelCompatibilityVersionCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLookAndFeelFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLModelHintsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPomFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPortletFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPortletPreferencesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPoshiFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLProjectElementCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLResourceActionsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
[XMLServiceEntityNameCheck](checks/xml_service_entity_name_check.markdown#xmlserviceentitynamecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | Chceks that the entity name in `service.xml` does not equal the package name |
XMLServiceFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLServiceReferenceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSolrSchemaFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSourcechecksFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSpringFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLStrutsConfigFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSuppressionsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTagAttributesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .html, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTestIgnorableErrorLinesFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTilesDefsFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLToggleFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLWebFileCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
YMLDefinitionOrderCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .yaml or .yml | |
YMLEmptyLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .yaml or .yml | |
YMLLongLinesCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .yaml or .yml | |
YMLStylingCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .yaml or .yml | |
YMLWhitespaceCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | .yaml or .yml | |