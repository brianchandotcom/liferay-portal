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
	var field = A.one('#${portletNamespace}${namespacedFieldName}');

	if (field) {
		var form = field.get('form');

		if (form) {
			form.on(
				'submit',
				function(event) {
					field.val(window.${portletNamespace}${namespacedFieldName}Editor.getHTML());
				}
			);
		}
	}
</@>