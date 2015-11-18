<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<@aui.input cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) name="${namespacedFieldName}" type="file">
		<@aui.validator name="acceptFiles">'.gif,.jpeg,.jpg,.png'</@aui.validator>

		<#if required && !(fields??)>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<#if (fields??) && (fieldValue != "")>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="window['${portletNamespace}${namespacedFieldName}ToggleImage']();">${languageUtil.get(locale, "show")}</a> ]

		<div class="hide wcm-image-preview" id="${portletNamespace}${namespacedFieldName}Container">
			<#if !required>
				<a href="javascript:;" id="${portletNamespace}${namespacedFieldName}DeleteImage" onClick="window['${portletNamespace}${namespacedFieldName}ToggleDeleteImage']();">${languageUtil.get(locale, "delete")}</a>
			</#if>

			<img id="${portletNamespace}${namespacedFieldName}Image" src="${themeDisplay.getPathContext()}${fieldValue}" />
		</div>

		<@aui.input name="${namespacedFieldName}URL" type="hidden" value="${fieldValue}" />
	</#if>

	${fieldStructure.children}
</@>

<@aui.script>
	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleImage',
		function() {
			var A = AUI();

			var toggleText = '${languageUtil.get(locale, "show")}';

			var containerNode = A.one('#${portletNamespace}${namespacedFieldName}Container');

			if (containerNode.test(':hidden')) {
				toggleText = '${languageUtil.get(locale, "hide")}';
			}

			A.one('#${portletNamespace}${namespacedFieldName}ToggleImage').setContent(toggleText);

			containerNode.toggle();
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleDeleteImage',
		function() {
			var A = AUI();

			var buttonText = '${languageUtil.get(locale, "cancel")}';

			var disabled = true;

			var imageFileInputNode = A.one('#${portletNamespace}${namespacedFieldName}');
			var imageURLInputNode = A.one('#${portletNamespace}${namespacedFieldName}URL');

			if (imageFileInputNode.get('disabled')) {
				buttonText = '${languageUtil.get(locale, "delete")}';

				disabled = false;
			}

			A.one('#${portletNamespace}${namespacedFieldName}DeleteImage').setContent(buttonText);

			imageFileInputNode.attr('disabled', disabled);
			imageURLInputNode.attr('disabled', disabled);

			A.one('#${portletNamespace}${namespacedFieldName}Image').toggle();
		},
		['aui-base']
	);
</@>