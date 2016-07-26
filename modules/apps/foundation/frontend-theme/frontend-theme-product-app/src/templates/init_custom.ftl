<#assign
	fullScreenNavigation = getterUtil.getBoolean(themeDisplay.getThemeSetting("full-screen-navigation"))
	socialMediaFacebook = getterUtil.getBoolean(themeDisplay.getThemeSetting("social-media-facebook"))
	socialMediaTwitter = getterUtil.getBoolean(themeDisplay.getThemeSetting("social-media-twitter"))
	socialMediaFacebookUrl = themeDisplay.getThemeSetting("social-media-facebook-url")
	socialMediaTwitterUrl = themeDisplay.getThemeSetting("social-media-twitter-url")
	siteLogoRetina = getterUtil.getBoolean(themeDisplay.getThemeSetting("site-logo-retina"))
/>

<#if fullScreenNavigation>
	<#assign
		css_class = css_class + " full-screen-navigation"
		nav_collapse = ""
		nav_css_right = ""
	/>

<#else>
	<#assign
		nav_css_right = "navbar-right"
		nav_collapse = "navbar-collapse"
	/>
</#if>

<#if siteLogoRetina>
	<#assign company_logo_height = company_logo_height/2 />
	<#assign company_logo_width = company_logo_width/2 />
</#if>