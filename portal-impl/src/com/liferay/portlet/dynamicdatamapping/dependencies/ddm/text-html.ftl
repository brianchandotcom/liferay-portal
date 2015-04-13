<#include "../init.ftl">

<#assign fieldValue = paramUtil.getString(request, "${namespacedFieldName}Editor", fieldValue)>

<@aui.script>
	window['${portletNamespace}${namespacedFieldName}InitEditor'] = function() {
		return "${unicodeFormatter.toString(fieldValue)}";
	}

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}OnBlurEditor',
		function() {
			var A = AUI();

			var field = A.one('#${portletNamespace}${namespacedFieldName}');

			field.val(window['${portletNamespace}${namespacedFieldName}Editor'].getHTML());

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

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<#assign skipEditorLoading = paramUtil.getBoolean(request, "p_p_isolated")>

	<@liferay_ui["input-editor"] contentsLanguageId="${requestedLocale}" initMethod="${namespacedFieldName}InitEditor" name="${namespacedFieldName}Editor" onBlurMethod="${namespacedFieldName}OnBlurEditor" skipEditorLoading=skipEditorLoading />

	<@aui.input name=namespacedFieldName type="hidden" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@>

	${fieldStructure.children}
</@>

<@aui.script use="aui-base">
	var field = A.one('#${portletNamespace}${namespacedFieldName}');

	var form = field.get('form');

	if (form) {
		var handler = Liferay.on(
			'submitForm',
			function(event) {
				if (event.form.compareTo(form)) {
					field.val(window['${portletNamespace}${namespacedFieldName}Editor'].getHTML());
				}
			}
		);

		Liferay.DDM.RepeatableFields.eventHandlers['${portletNamespace}${namespacedFieldName}'] = [handler];
	}
</@>