<#include "../init.ftl">

<#assign alt = "" />

<#if fieldRawValue?has_content>
	<#assign
		fileJSONObject = getFileJSONObject(fieldRawValue)

		alt = fileJSONObject.getString("alt")
		src = fileJSONObject.getString("data")
	/>
</#if>

<@liferay_aui["field-wrapper"] data=data>
	<#if hasFieldValue || showEmptyFieldLabel>
		<label>
			<@liferay_ui.message key=escape(label) />
		</label>
	</#if>

	<#if hasFieldValue>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="${portletNamespace}${namespacedFieldName}ToggleImage();">${languageUtil.get(locale, "show")}</a> ]

		<div class="hide wcm-image-preview" id="${portletNamespace}${namespacedFieldName}Container">
			<img alt="${escapeAttribute(alt)}" class="img-polaroid" id="${portletNamespace}${namespacedFieldName}Image" src="${escapeAttribute(src)}" />
		</div>

		<#if !disabled>
			<@liferay_aui.input
				name="${namespacedFieldName}URL"
				type="hidden"
				value="${src}"
			/>

			<@liferay_aui.input
				label="image-description"
				name="${namespacedFieldName}Alt"
				type="hidden"
				value="${alt}"
			/>
		</#if>
	</#if>

	${fieldStructure.children}
</@>

<@liferay_aui.script>
	function ${portletNamespace}${namespacedFieldName}ToggleImage() {
		var toggleText = '${languageUtil.get(locale, "show")}';
		var containerNode = document.getElementById('${portletNamespace}${namespacedFieldName}Container');

		if (containerNode) {
			if (containerNode.classList.contains('hide')) {
				toggleText = '${languageUtil.get(locale, "hide")}';

				containerNode.classList.remove('hide');
			}
			else {
				containerNode.classList.add('hide');
			}
		}

		var imageToggle = document.getElementById('${portletNamespace}${namespacedFieldName}ToggleImage');

		if (imageToggle) {
			imageToggle.innerHTML = toggleText;
		}
	}
</@>