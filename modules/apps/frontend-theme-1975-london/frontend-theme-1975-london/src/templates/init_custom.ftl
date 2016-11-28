<#assign
	floatingHeader = getterUtil.getBoolean(themeDisplay.getThemeSetting("floating-header"))
	fullScreenNavigation = getterUtil.getBoolean(themeDisplay.getThemeSetting("full-screen-navigation"))
	siteLogoRetina = getterUtil.getBoolean(themeDisplay.getThemeSetting("site-logo-retina"))
/>

<#if floatingHeader>
	<#assign css_class = css_class + " floating-header" />
</#if>

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