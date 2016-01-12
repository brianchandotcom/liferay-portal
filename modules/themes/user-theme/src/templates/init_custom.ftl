<#assign liferay_control_menu=PortalJspTagLibs["/META-INF/resources/liferay-control-menu.tld"]>
<#assign liferay_ui=PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"]>
<#assign liferay_util=PortalJspTagLibs["/WEB-INF/tld/liferay-util.tld"]>

<#assign layout_set_title = site_name>

<#assign pageDistributionType = themeDisplay.getThemeSetting("page-distribution-type")>
<#assign userCardVisible = getterUtil.getBoolean(themeDisplay.getThemeSetting("user-card-visible"))>

<#if page_group.isUser() && layout.isPrivateLayout()>
	<#assign layout_set_title = languageUtil.get(locale, "my-dashboard")>
</#if>

<#if pageDistributionType = "horizontal">
	<#assign css_class = "css_class user-card-horizontal">
	<#assign firstColumnClass = "col-md-3">
	<#assign secondColumnClass = "col-md-9">
	<#assign userIconSize = "user-icon-xxl">
<#else>
	<#assign css_class = "css_class user-card-vertical">
	<#assign firstColumnClass = "col-md-12">
	<#assign secondColumnClass = "col-md-12">
	<#assign userIconSize = "user-icon-xl">
</#if>