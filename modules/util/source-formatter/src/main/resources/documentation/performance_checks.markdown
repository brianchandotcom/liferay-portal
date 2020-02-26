# Performance Checks

Check | File Extensions | Description
----- | --------------- | -----------
[ArrayCheck](checks/array_check.markdown#arraycheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | .java | Checks that we do not concatenate more than 3 String objects |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | .java | Checks if performance can be improved by using different String operation methods |