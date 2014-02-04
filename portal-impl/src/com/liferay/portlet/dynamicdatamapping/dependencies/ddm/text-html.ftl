<#include "../init.ftl">

<#assign skipEditorLoading = paramUtil.getBoolean(request, "p_p_isolated")>

<#assign safeNamespacedFieldName = stringUtil.replace(namespacedFieldName, "-", "_")>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<@liferay_ui["input-editor"] initMethod="${safeNamespacedFieldName}InitEditor" name="${safeNamespacedFieldName}Editor" onBlurMethod="${safeNamespacedFieldName}OnBlurEditor" skipEditorLoading=skipEditorLoading />

	<@aui.input name=safeNamespacedFieldName type="hidden" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@>

	${fieldStructure.children}
</@>

<@aui.script>
	function ${portletNamespace}${safeNamespacedFieldName}InitEditor() {
		return "${unicodeFormatter.toString(fieldValue)}";
	}

	Liferay.provide(
		window,
		'${portletNamespace}${safeNamespacedFieldName}OnBlurEditor',
		function() {
			var A = AUI();

			var field = A.one('#${portletNamespace}${safeNamespacedFieldName}');

			field.val(window.${portletNamespace}${safeNamespacedFieldName}Editor.getHTML());

			var form = field.get('form');

			if (form) {
				var formName = form.get('name');

				var formValidator = Liferay.Form.get(formName).formValidator;

				if (formValidator) {
					formValidator.validateField(field);
				}
			}
		},
		['liferay-form']
	);
</@>

<@aui.script use="aui-base">
	var field = A.one('#${portletNamespace}${safeNamespacedFieldName}');

	var form = field.get('form');

	if (form) {
		form.on(
			'submit',
			function(event) {
				field.val(window.${portletNamespace}${safeNamespacedFieldName}Editor.getHTML());
			}
		);
	}
</@>