<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<@liferay_ui["icon-menu"] icon="../aui/globe" message=locale.getDisplayName(locale)>
		<#assign languageId = localeUtil.toLanguageId(locale) />

		<#list entries as entry>
			<#assign currentLanguageId = localeUtil.toLanguageId(entry) />

			<#if currentLanguageId != languageId>
				<#assign updateLanguageURL = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&languageId=" + currentLanguageId + "&redirect=" + currentURL />

				<@liferay_ui["icon"]
					image="../language/" + currentLanguageId
					label=true
					lang=localeUtil.toW3cLanguageId(entry)
					message=entry.getDisplayName(locale)
					url=updateLanguageURL
				/>
			</#if>
		</#list>
	</@>
</#if>