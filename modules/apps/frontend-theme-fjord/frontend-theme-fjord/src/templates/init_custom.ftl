<#assign
	facebook_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("facebook-icon"))
	facebook_icon_link_url = themeDisplay.getThemeSetting("facebook-icon-link-url")
	show_main_navigation_in_full_screen = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-main-navigation-in-full-screen"))
	twitter_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("twitter-icon"))
	twitter_icon_link_url = themeDisplay.getThemeSetting("twitter-icon-link-url")
	use_a_retina_logo = getterUtil.getBoolean(themeDisplay.getThemeSetting("use-a-retina-logo"))
/>

<#if show_main_navigation_in_full_screen>
	<#assign
		css_class = css_class + " full-screen-navigation"
		nav_collapse = ""
		nav_css_right = ""
	/>

<#else>
	<#assign
		nav_css_right = "ml-auto"
		nav_collapse = "navbar-expand-md"
	/>
</#if>

<#if use_a_retina_logo>
	<#assign company_logo_height = company_logo_height/2 />
	<#assign company_logo_width = company_logo_width/2 />
</#if>