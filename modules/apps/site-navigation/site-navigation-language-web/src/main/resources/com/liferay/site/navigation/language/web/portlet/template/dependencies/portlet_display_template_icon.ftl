<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<#list entries as entry>
		<#if entry.isSelected()>
			<#assign cssClass = "current-language" />
		</#if>

		<#if !entry.isDisabled()>
			<@liferay_ui["icon"]
				cssClass=cssClass
				image="../language/" + entry.getLanguageId()
				lang=entry.getW3cLanguageId()
				message=entry.getLongDisplayName()
				url=entry.getURL()
			/>
		</#if>
	</#list>
</#if>