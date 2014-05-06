<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<#assign portletURL = renderResponse.createActionURL() />

	${portletURL.setAnchor(false)}
	${portletURL.setParameter("struts_action", "/language/view")}
	${portletURL.setParameter("redirect", portalUtil.getCurrentURL(request))}
	${portletURL.setPortletMode("view")}
	${portletURL.setWindowState("normal")}

	<@liferay_ui["icon-menu"] icon=themeDisplay.getPathThemeImages() + "/common/global.png" message=locale.getDisplayName(locale)>
		<#assign languageId = localeUtil.toLanguageId(locale) />

		<#list entries as entry>
			<#assign currentLanguageId = localeUtil.toLanguageId(entry) />

			<#if currentLanguageId != languageId>
				<@liferay_ui["icon"]
					image="../language/" + currentLanguageId
					label=true
					lang=localeUtil.toW3cLanguageId(entry)
					message=entry.getDisplayName(locale)
					url=httpUtil.setParameter(portletURL.toString(), renderResponse.getNamespace() + "languageId", currentLanguageId)
				/>
			</#if>
		</#list>
	</@>
</#if>