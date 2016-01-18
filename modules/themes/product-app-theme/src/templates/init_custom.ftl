<#assign liferay_control_menu=PortalJspTagLibs["/META-INF/resources/liferay-control-menu.tld"]>
<#assign liferay_ui=PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"]>
<#assign liferay_util=PortalJspTagLibs["/WEB-INF/tld/liferay-util.tld"]>

<#assign fullScreenNavigation = getterUtil.getBoolean(themeDisplay.getThemeSetting("full-screen-navigation"))>
<#assign socialMediaFacebook = getterUtil.getBoolean(themeDisplay.getThemeSetting("social-media-facebook"))>
<#assign socialMediaTwitter = getterUtil.getBoolean(themeDisplay.getThemeSetting("social-media-twitter"))>
<#assign socialMediaFacebookUrl = themeDisplay.getThemeSetting("social-media-facebook-url")>
<#assign socialMediaTwitterUrl = themeDisplay.getThemeSetting("social-media-twitter-url")>
<#assign siteLogoRetina = getterUtil.getBoolean(themeDisplay.getThemeSetting("site-logo-retina"))>

<#if fullScreenNavigation>
	<#assign css_class = css_class + " full-screen-navigation">
	<#assign nav_collapse = "">
	<#assign nav_css_right = "">
<#else>
	<#assign nav_css_right = "navbar-right">
	<#assign nav_collapse = "navbar-collapse">
</#if>

<#if siteLogoRetina>
	<#assign company_logo_height = company_logo_height/2>
	<#assign company_logo_width = company_logo_width/2>
</#if>