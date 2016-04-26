<#assign layout_set_title = site_name>
<#assign main_menu_style = "">

<#if page_group.isUser()>
	<#assign main_menu_style = "style='background-image: url(${user2.getPortraitURL(theme_display)});'">
</#if>

<#if page_group.isUser() && layout.isPrivateLayout()>
	<#assign layout_set_title = languageUtil.get(locale, "my-dashboard")>
</#if>