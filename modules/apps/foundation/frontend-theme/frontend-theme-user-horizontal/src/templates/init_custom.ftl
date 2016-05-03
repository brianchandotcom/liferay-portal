<#assign layout_set_title = site_name>

<#if page_group.isUser() && layout.isPrivateLayout()>
	<#assign layout_set_title = languageUtil.get(locale, "my-dashboard")>
</#if>

<#assign main_menu_style = "">

<#if user2??>
	<#assign main_menu_style = "style='background-image: url(${user2.getPortraitURL(theme_display)});'">
</#if>