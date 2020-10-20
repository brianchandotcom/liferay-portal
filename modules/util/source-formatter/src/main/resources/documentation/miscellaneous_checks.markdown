# Miscellaneous Checks

Check | File Extensions | Description
----- | --------------- | -----------
[BNDDeprecatedAppBNDsCheck](checks/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules. |
[BNDSuiteCheck](checks/bnd_suite_check.markdown#bndsuitecheck) | .bnd | Checks that deprecated apps are moved to the `archived` folder. |
[CreationMenuBuilderCheck](checks/builder_check.markdown#buildercheck) | .java | Checks that `CreationMenuBuilder` is used when possible. |
FullyQualifiedNameCheck | .java | Finds cases where a Fully Qualified Name is used instead of importing a class. |
[ItemListBuilderCheck](checks/builder_check.markdown#buildercheck) | .java | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
JSPInlineVariableCheck | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPModuleIllegalImportsCheck](checks/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
[JSPParenthesesCheck](checks/if_statement_check.markdown#ifstatementcheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | .jsp, .jspf, .tag, .tpl or .vm | Validates values of variable `redirect`. |
[JSPServiceUtilCheck](checks/jsp_service_util_check.markdown#jspserviceutilcheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
Java2HTMLCheck | .java | Finds incorrect use of `.java.html` in `.jsp` files. |
JavaDiamondOperatorCheck | .java | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | .java | Finds variables where a variable with the same name already exists in an extended class. |
[JavaElseStatementCheck](checks/java_else_statement_check.markdown#javaelsestatementcheck) | .java | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | .java | Finds missing emptly line after a `super` call. |
JavaRedundantConstructorCheck | .java | |
JavaStagedModelDataHandlerCheck | .java | |
JavaStaticMethodCheck | .java | |
JavaStaticVariableDependencyCheck | .java | |
JavaSystemEventAnnotationCheck | .java | |
JavaSystemExceptionCheck | .java | |
JavaTaglibMethodCheck | .java | |
JavaTransactionBoundaryCheck | .java | |
JavaUnusedSourceFormatterChecksCheck | .java | Finds `*Check` classes that are not configured. |
JavaUpgradeConnectionCheck | .java | |
JavaUpgradeVersionCheck | .java | |
JavaVariableTypeCheck | .java | |
JavaVerifyUpgradeConnectionCheck | .java | |
LFRBuildContentCheck | .lfrbuild-* | |
LFRBuildReadmeCheck | .lfrbuild-* | |
LPS42924Check | .java | |
LiteralStringEqualsCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LocalPatternCheck | .java | |
LocaleUtilCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LogMessageCheck](checks/message_check.markdown#messagecheck) | .java | |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
MarkdownSourceFormatterDocumentationCheck | .markdown or .md | |
MarkdownSourceFormatterReadmeCheck | .markdown or .md | |
MethodCallsOrderCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
MissingDeprecatedJavadocCheck | .java | |
MissingDiamondOperatorCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
MissingModifierCheck | .java | |
MissingOverrideCheck | .java | |
MissingParenthesesCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
NestedIfStatementCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
NewFileCheck | | |
NonbreakingSpaceCheck | | |
NotRequireThisCheck | .java | |
NumberSuffixCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
OperatorOperandCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
OperatorOrderCheck | .java | |
PackageinfoBNDExportPackageCheck | packageinfo | |
ParsePrimitiveTypeCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PersistenceCallCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PlusStatementCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PrimitiveWrapperInstantiationCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PrincipalExceptionCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PropertiesArchivedModulesCheck | .prettierignore or .properties | |
PropertiesBuildIncludeDirsCheck | .prettierignore or .properties | |
PropertiesCommentsCheck | .prettierignore or .properties | |
PropertiesDefinitionKeysCheck | .prettierignore or .properties | |
PropertiesDependenciesFileCheck | .prettierignore or .properties | |
PropertiesImportedFilesContentCheck | .prettierignore or .properties | |
PropertiesLanguageKeysOrderCheck | .prettierignore or .properties | |
PropertiesLiferayPluginPackageFileCheck | .prettierignore or .properties | |
PropertiesLiferayPluginPackageLiferayVersionsCheck | .prettierignore or .properties | |
PropertiesLongLinesCheck | .prettierignore or .properties | |
PropertiesMultiLineValuesOrderCheck | .prettierignore or .properties | |
PropertiesPortalEnvironmentVariablesCheck | .prettierignore or .properties | |
PropertiesPortalFileCheck | .prettierignore or .properties | |
PropertiesPortletFileCheck | .prettierignore or .properties | |
PropertiesReleaseBuildCheck | .prettierignore or .properties | |
PropertiesServiceKeysCheck | .prettierignore or .properties | |
PropertiesSourceFormatterContentCheck | .prettierignore or .properties | |
PropertiesSourceFormatterFileCheck | .prettierignore or .properties | |
PropertiesVerifyPropertiesCheck | .prettierignore or .properties | |
RedundantBranchingStatementCheck | .java | |
ReferenceAnnotationCheck | .java | |
SelfReferenceCheck | .java | |
SemiColonCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SingleStatementClauseCheck | .java | |
SizeIsZeroCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SlantedQuotesCheck | | |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | .java | |
StringCastCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SubstringCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TLDElementOrderCheck | .tld | |
TLDTypeCheck | .tld | |
TernaryOperatorCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TestClassCheck | .java | |
ThreadLocalUtilCheck | .java | |
ThreadNameCheck | .java | |
TransactionalTestRuleCheck | .java | |
TryWithResourcesCheck | .java | |
[UnnecessaryAssignCheck](checks/unnecessary_assign_check.markdown#unnecessaryassigncheck) | .java | |
UnnecessaryTypeCastCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[UnnecessaryVariableDeclarationCheck](checks/unnecessary_variable_declaration_check.markdown#unnecessaryvariabledeclarationcheck) | .java | |
UnparameterizedClassCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
UnprocessedExceptionCheck | .java | |
UnusedMethodCheck | .java | |
UnusedParameterCheck | .java | |
UnusedVariableCheck | .java | |
UnwrappedVariableInfoCheck | .java | |
ValidatorIsNullCheck | .java, .jsp, .jspf, .tag, .tpl or .vm | |
VariableDeclarationAsUsedCheck | .java | |
VariableNameCheck | .java | |
WhitespaceAfterParameterAnnotationCheck | .java | |
WhitespaceAroundGenericsCheck | .java | |
XMLBuildFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCDATACheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCheckstyleFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLCustomSQLOrderCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLDDLStructuresFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLFSBExcludeFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLFriendlyURLRoutesFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLHBMFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLIvyFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLog4jFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLookAndFeelCompatibilityVersionCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLLookAndFeelFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLModelHintsFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPomFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPortletFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPortletPreferencesFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLPoshiFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLProjectElementCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLResourceActionsFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLServiceFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLServiceReferenceCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSolrSchemaFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSourcechecksFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSpringFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLStrutsConfigFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLSuppressionsFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTagAttributesCheck | .action, .function, .html, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTestIgnorableErrorLinesFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLTilesDefsFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLToggleFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |
XMLWebFileCheck | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | |