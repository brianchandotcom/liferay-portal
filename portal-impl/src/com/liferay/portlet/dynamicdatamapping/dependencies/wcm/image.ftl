<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName type="file">
		<@aui.validator name="acceptFiles">'.gif,.jpeg,.jpg,.png'</@aui.validator>

		<#if required && !(fields??)>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui.input name="${namespacedFieldName}Delete" type="hidden" value="delete" />

	<#if (fields??) && (fieldValue != "")>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="${portletNamespace}${namespacedFieldName}ToggleImage();">${languageUtil.get(locale, "show")}</a> ]

		<div class="aui-helper-hidden" id="${portletNamespace}${namespacedFieldName}Container">
			<#if !required>
				<a href="javascript:;" id="${portletNamespace}${namespacedFieldName}DeleteImage" onClick="${portletNamespace}${namespacedFieldName}ToggleDeleteImage();">${languageUtil.get(locale, "delete")}</a>
			</#if>

			<img id="${portletNamespace}${namespacedFieldName}Image" src="${fieldValue}" />
		</div>
	</#if>

	${fieldStructure.children}
</@>

<@aui.script>
	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleImage',
		function() {
			var A = AUI();

			var containerNode = A.one('#${portletNamespace}${namespacedFieldName}Container');
			var toggleImageNode = A.one('#${portletNamespace}${namespacedFieldName}ToggleImage');

			if (containerNode.hasClass('aui-helper-hidden')) {
				toggleImageNode.setContent('${languageUtil.get(locale, "hide")}');
			}
			else {
				toggleImageNode.setContent('${languageUtil.get(locale, "show")}');
			}

			containerNode.toggleClass('aui-helper-hidden');
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleDeleteImage',
		function() {
			var A = AUI();

			var deleteButtonNode = A.one('#${portletNamespace}${namespacedFieldName}DeleteImage');
			var deleteInputNode = A.one('#${portletNamespace}${namespacedFieldName}Delete');
			var imageInputNode = A.one('#${portletNamespace}${namespacedFieldName}');
			var imageNode = A.one('#${portletNamespace}${namespacedFieldName}Image');

			if (imageInputNode.get('disabled')) {
				deleteButtonNode.setContent('${languageUtil.get(locale, "delete")}');

				imageInputNode.set('disabled', false);
				deleteInputNode.set('name', '${portletNamespace}${namespacedFieldName}Delete');
			}
			else {
				deleteButtonNode.setContent('${languageUtil.get(locale, "cancel")}');

				imageInputNode.set('disabled', true);
				deleteInputNode.set('name', '${portletNamespace}${namespacedFieldName}');
			}

			imageNode.toggleClass('aui-helper-hidden');
		},
		['aui-base']
	);
</@>