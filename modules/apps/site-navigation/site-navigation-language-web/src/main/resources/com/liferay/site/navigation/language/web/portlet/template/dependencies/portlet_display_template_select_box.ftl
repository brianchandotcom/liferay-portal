<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<#assign languageId = localeUtil.toLanguageId(locale) />

	<style>
		.taglib-language-option {
			background: none no-repeat 5px center;
			padding-left: 25px;
		}

		<#list entries as entry>
			.taglib-language-option-${entry.getW3cLanguageId()} {
				background-image: url(${themeDisplay.getPathThemeImages()}/language/${entry.getLanguageId()}.png);
			}
		</#list>
	</style>

	<@aui["form"]
		action=formAction
		method="post"
		name='${namespace + formName}'
		useNamespace=false
	>
		<@aui["select"]
			changesContext=true
			id='${namespace + formName}'
			label=""
			name='${name}'
			onChange='${namespace + "changeLanguage();"}'
			title="language"
		>
			<#list entries as entry>
				<@aui["option"]
					cssClass="taglib-language-option taglib-language-option-${entry.getW3cLanguageId()}"
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
		function ${namespace}changeLanguage() {
			var languageId = AUI.$(document.${namespace + formName}.${name}).val();

			submitForm(document.${namespace + formName});
		}
	</@>
</#if>