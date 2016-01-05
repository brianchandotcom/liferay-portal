<#assign fullScreenNavigation = theme.getSetting("full-screen-navigation")>
<#assign socialMediaFacebook = theme.getSetting("social-media-facebook")>
<#assign socialMediaTwitter = theme.getSetting("social-media-twitter")>
<#assign socialMediaFacebookUrl = theme.getSetting("social-media-facebook-url")>
<#assign socialMediaTwitterUrl = theme.getSetting("social-media-twitter-url")>
<#assign siteLogoRetina = theme.getSetting("site-logo-retina")>

<#if fullScreenNavigation == "true">
	<#assign css_class = css_class + " full-screen-navigation">
	<#assign nav_collapse = "">
	<#assign nav_css_right = "">
<#else>
	<#assign nav_css_right = "navbar-right">
	<#assign nav_collapse = "navbar-collapse">
</#if>

<#if siteLogoRetina == "true">
	<#assign company_logo_height = company_logo_height/2>
	<#assign company_logo_width = company_logo_width/2>
</#if>