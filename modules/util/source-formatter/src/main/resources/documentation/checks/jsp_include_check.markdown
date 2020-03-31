## JSPIncludeCheck

When using `include` in `.jsp` the value of `file` should point to path relative
to either the `docroot` or `resources` directory.

### Examples:

File location of file to be inlucded:
`/modules/apps/expando/expando-taglib/src/main/resources/META-INF/resources/custom_attribute/init.jsp`

```
<%@ include file="/custom_attribute/init.jsp" %>
```

File location of file to be inlucded:
`/portal-web/docroot/html/common/themes/init.jsp`

```
<%@ include file="/html/common/themes/init.jsp" %>
```