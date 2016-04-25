<#assign layout_set_title = site_name>

<#if page_group.isUser() && layout.isPrivateLayout()>
	<#assign layout_set_title = languageUtil.get(locale, "my-dashboard")>
</#if>