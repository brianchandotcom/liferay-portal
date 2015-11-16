<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<#assign languageId = localeUtil.toLanguageId(locale) />

	<@aui["form"]
		action=formAction
		method="post"
		name='${namespace + formName}'
		useNamespace=false
	>
		<@aui["select"]
			changesContext=true
			id='${namespace + name}'
			label=""
			name='${name}'
			onChange='${namespace + "changeLanguage();"}'
			title="language"
		>
			<#list entries as entry>
				<@aui["option"]
					cssClass="taglib-language-option"
					disabled=entry.isDisabled()
					label=entry.getLongDisplayName()
					lang=entry.getW3cLanguageId()
					selected=entry.isSelected()
					value=entry.getLanguageId()
				/>
			</#list>
		</@>
	</@>

	<@aui["script"]>
		<#list entries as entry>
			document.${namespace + formName}.${name}.options[${entry_index}].style.backgroundImage = 'url(${themeDisplay.getPathThemeImages()}/language/${entry.getLanguageId()}.png)';
			document.${namespace + formName}.${name}.options[${entry_index}].style.backgroundPosition = 'left center';
			document.${namespace + formName}.${name}.options[${entry_index}].style.backgroundRepeat = 'no-repeat';
			document.${namespace + formName}.${name}.options[${entry_index}].style.paddingLeft = '20px';
		</#list>

		function ${namespace}changeLanguage() {
			var languageId = AUI.$(document.${namespace + formName}.${name}).val();

			submitForm(document.${namespace + formName});
		}
	</@>
</#if>