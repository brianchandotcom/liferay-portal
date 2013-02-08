<#include "../init.ftl">

<#assign skipEditorLoading = paramUtil.getBoolean(request, "p_p_isolated")>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<@liferay_ui["input-editor"] initMethod="${namespacedFieldName}InitEditor" name="${namespacedFieldName}Editor" skipEditorLoading=skipEditorLoading />

	<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

	${fieldStructure.children}
</@>

<@aui.script>
	function ${namespace}${namespacedFieldName}InitEditor() {
		return "${unicodeFormatter.toString(fieldValue)}";
	}
</@>

<@aui.script use="aui-base">
	var field = A.one('#${namespace}${namespacedFieldName}');

	if (field) {
		var form = field.get('form');

		if (form) {
			form.on(
				'submit',
				function(event) {
					field.val(window.${namespace}${namespacedFieldName}Editor.getHTML());
				}
			);
		}
	}
</@>