<#assign
	header_floating_on_screen_top = getterUtil.getBoolean(themeDisplay.getThemeSetting("header-floating-on-screen-top"))
	show_main_navigation_in_full_screen = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-main-navigation-in-full-screen"))
	use_a_retina_logo = getterUtil.getBoolean(themeDisplay.getThemeSetting("use-a-retina-logo"))
/>

<#if header_floating_on_screen_top>
	<#assign css_class = css_class + " floating-header" />
</#if>

<#if show_main_navigation_in_full_screen>
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

<#if use_a_retina_logo>
	<#assign company_logo_height = company_logo_height/2 />
	<#assign company_logo_width = company_logo_width/2 />
</#if>