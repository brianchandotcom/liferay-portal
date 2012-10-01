<#include "../init.ftl">

<@aui["field-wrapper"] helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<@liferay_ui["input-editor"] initMethod="${namespacedFieldName}InitEditor" name="${namespacedFieldName}Editor" />

	<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

	${fieldStructure.children}
</@>

<@aui.script>
	function ${portletNamespace}${namespacedFieldName}InitEditor() {
		return "${unicodeFormatter.toString(fieldValue)}";
	}
</@>

<@aui.script use="aui-base">
	var namespacedField = A.one('#${portletNamespace}${namespacedFieldName}');

	if (namespacedField) {
		var form = namespacedField.ancestor('form');

		if (form) {
			form.on(
				'submit',
				function(event) {
					namespacedField.val(window.${portletNamespace}${namespacedFieldName}Editor.getHTML());

					submitForm(form.getDOM());
				}
			);
		}
	}
</@>