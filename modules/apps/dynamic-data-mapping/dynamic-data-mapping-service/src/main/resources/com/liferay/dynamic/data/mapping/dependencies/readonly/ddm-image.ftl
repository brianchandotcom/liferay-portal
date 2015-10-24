<#include "../init.ftl">

<#assign alt = "">

<#if fieldRawValue?has_content>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign alt = fileJSONObject.getString("alt")>
	<#assign src = fileJSONObject.getString("data")>
</#if>

<@aui["field-wrapper"] data=data>
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="${portletNamespace}${namespacedFieldName}ToggleImage();">${languageUtil.get(locale, "show")}</a> ]

		<div class="hide wcm-image-preview" id="${portletNamespace}${namespacedFieldName}Container">
			<img alt="${alt}" class="img-polaroid" id="${portletNamespace}${namespacedFieldName}Image" src="${src}" />
		</div>

		<#if !disabled>
			<@aui.input
				name="${namespacedFieldName}URL"
				type="hidden"
				value="${src}"
			/>

			<@aui.input
				label="image-description"
				name="${namespacedFieldName}Alt"
				type="hidden"
				value="${alt}"
			/>
		</#if>
	</#if>

	${fieldStructure.children}
</@>

<@aui.script>
	function ${portletNamespace}${namespacedFieldName}ToggleImage() {
		var toggleText = '${languageUtil.get(locale, "show")}';

		var containerNode = AUI.$('#${portletNamespace}${namespacedFieldName}Container');

		if (containerNode.hasClass('hide')) {
			toggleText = '${languageUtil.get(locale, "hide")}';
		}

		AUI.$('#${portletNamespace}${namespacedFieldName}ToggleImage').html(toggleText);

		containerNode.toggleClass('hide');
	}
</@>