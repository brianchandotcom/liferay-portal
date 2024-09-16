<#if entries?has_content>
	<#assign languageId = localeUtil.toLanguageId(locale) />

	<@liferay_aui["select"]
		changesContext=true
		label=""
		name='${randomNamespace + name}'
		onChange='${randomNamespace + "changeLanguage();"}'
		title="language"
		useNamespace=false
	>
		<#list entries as entry>
			<@liferay_aui["option"]
				cssClass="taglib-language-option taglib-language-option-${entry.getW3cLanguageId()}"
				disabled=entry.isDisabled()
				label=entry.getLongDisplayName()
				lang=entry.getW3cLanguageId()
				localizeLabel=false
				selected=entry.isSelected()
				value=entry.getURL()!
			/>
		</#list>
	</@>

	<@liferay_aui["script"]>
		function ${randomNamespace}changeLanguage() {
			window.location.href=event.target.value;
		}
	</@>
</#if>