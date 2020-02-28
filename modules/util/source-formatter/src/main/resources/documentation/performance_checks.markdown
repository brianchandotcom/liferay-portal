# Performance Checks

Check | File Extensions | Description
----- | --------------- | -----------
[ArrayCheck](checks/array_check.markdown#arraycheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | .jsp, .jspf, .tag, .tpl or .vm | Checks for unnesecarry duplication of code that already exists in `defineObjects` |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | .java | Checks that we do not concatenate more than 3 String objects |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no unnecessary map iterations |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | .java | Checks if performance can be improved by using different String operation methods |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there are no calls to `Validator.equals(Object, Object)` |