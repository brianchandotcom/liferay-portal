# Miscellaneous Checks

Check | File Extensions | Description
----- | --------------- | -----------
[BNDDeprecatedAppBNDsCheck](check/bnd_deprecated_app_bnds_check.markdown#bnddeprecatedappbndscheck) | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules. |
[BNDSuiteCheck](check/bnd_suite_check.markdown#bndsuitecheck) | .bnd | Checks that deprecated apps are moved to the `archived` folder. |
[CreationMenuBuilderCheck](check/builder_check.markdown#buildercheck) | .java | Checks that `CreationMenuBuilder` is used when possible. |
FullyQualifiedNameCheck | .java | Finds cases where a Fully Qualified Name is used instead of importing a class. |
[ItemBuilderCheck](check/builder_check.markdown#buildercheck) | .java | Checks that `DropdownItemBuilder`, `LabelItemBuilder` or `NavigationItemBuilder` is used when possible. |
[ItemListBuilderCheck](check/builder_check.markdown#buildercheck) | .java | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
[JSPModuleIllegalImportsCheck](check/jsp_module_illegal_imports_check.markdown#jspmoduleillegalimportscheck) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `com.liferay.registry.Registry` or `com.liferay.util.ContentUtil`. |
[JSPParenthesesCheck](check/if_statement_check.markdown#ifstatementcheck) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of parentheses in statement. |
JSPRedirectBackURLCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Validates values of variable `redirect`. |
[JSPServiceUtilCheck](check/jsp_service_util_check.markdown#jspserviceutilcheck) | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds incorrect use of `*ServiceUtil` in `.jsp` files in modules. |
Java2HTMLCheck | .java | Finds incorrect use of `.java.html` in `.jsp` files. |
JavaDiamondOperatorCheck | .java | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | .java | Finds variables where a variable with the same name already exists in an extended class. |
[JavaElseStatementCheck](check/java_else_statement_check.markdown#javaelsestatementcheck) | .java | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | .java | Finds missing emptly line after a `super` call. |
[JavaUnusedSourceFormatterChecksCheck](check/java_unused_source_formatter_checks_check.markdown#javaunusedsourceformattercheckscheck) | .java | Finds `*Check` classes that are not configured. |
[MapBuilderCheck](check/builder_check.markdown#buildercheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
[PortletURLBuilderCheck](check/builder_check.markdown#buildercheck) | .java, .jsp, .jspf, .jspx, .tag, .tpl or .vm | Checks that `PortletURLBuilder` is used when possible. |
[UnicodePropertiesBuilderCheck](check/builder_check.markdown#buildercheck) | .java | Checks that `UnicodePropertiesBuilder` is used when possible. |