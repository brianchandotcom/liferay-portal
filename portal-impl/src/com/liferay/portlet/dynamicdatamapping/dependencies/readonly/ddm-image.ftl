<#include "../init.ftl">

<#assign alt = "">

<#if fieldRawValue?has_content>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign alt = fileJSONObject.getString("alt")>
	<#assign src = fileJSONObject.getString("data")>
</#if>

<@aui["field-wrapper"] data=data>
	<label>
		<@liferay_ui.message key=escape(label) />
	</label>

	<#if (fields??) && (fieldValue != "")>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="${portletNamespace}${namespacedFieldName}ToggleImage();">${languageUtil.get(locale, "show")}</a> ]

		<div class="hide wcm-image-preview" id="${portletNamespace}${namespacedFieldName}Container">
			<img alt="${alt}" id="${portletNamespace}${namespacedFieldName}Image" src="${src}" class="img-polaroid"/>
		</div>

		<#if !disabled>
			<@aui.input name="${namespacedFieldName}URL" type="hidden" value="${src}" />

			<@aui.input label="image-description" name="${namespacedFieldName}Alt" type="hidden" value="${alt}" />
		</#if>
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
</@>