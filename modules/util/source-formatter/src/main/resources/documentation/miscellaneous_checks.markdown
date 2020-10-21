# Miscellaneous Checks

Check | File Extensions | Description
----- | --------------- | -----------
[BNDDeprecatedAppBNDsCheck](checks/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules. |
[BNDSuiteCheck](checks/bnd_suite_check.markdown#bndsuitecheck) | .bnd | Checks that deprecated apps are moved to the `archived` folder. |
[CreationMenuBuilderCheck](checks/builder_check.markdown#buildercheck) | .java | Checks that `CreationMenuBuilder` is used when possible. |
FullyQualifiedNameCheck | .java | Finds cases where a Fully Qualified Name is used instead of importing a class. |
[ItemListBuilderCheck](checks/builder_check.markdown#buildercheck) | .java | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
[JSPModuleIllegalImportsCheck](checks/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
[JSPParenthesesCheck](checks/if_statement_check.markdown#ifstatementcheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | .jsp, .jspf, .tag, .tpl or .vm | Validates values of variable `redirect`. |
[JSPServiceUtilCheck](checks/jsp_service_util_check.markdown#jspserviceutilcheck) | .jsp, .jspf, .tag, .tpl or .vm | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
Java2HTMLCheck | .java | Finds incorrect use of `.java.html` in `.jsp` files. |
JavaDiamondOperatorCheck | .java | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | .java | Finds variables where a variable with the same name already exists in an extended class. |
[JavaElseStatementCheck](checks/java_else_statement_check.markdown#javaelsestatementcheck) | .java | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | .java | Finds missing emptly line after a `super` call. |
[JavaUnusedSourceFormatterChecksCheck](checks/java_unused_source_formatter_checks_check.markdown#javaunusedsourceformattercheckscheck) | .java | Finds `*Check` classes that are not configured. |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |