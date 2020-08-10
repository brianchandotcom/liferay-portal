<#assign
	is_login_page = getterUtil.getBoolean(themeDisplay.getThemeSetting("is-login-page"))
	redirect_to_private_layouts = getterUtil.getBoolean(themeDisplay.getThemeSetting("redirect-to-private-layouts"))
	show_account_selector = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-account-selector"))
	show_mini_cart = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-mini-cart"))
	show_search_bar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-search-bar"))
	show_top_menu = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-top-menu"))
	show_topbar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-topbar"))
	show_sidebar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-sidebar"))
	wide_layout = getterUtil.getBoolean(themeDisplay.getThemeSetting("wide-layout"))
	userManagementUrl = commerceThemeMiniumHttpHelper.getAccountManagementPortletURL(request)
	wishlistUrl = commerceWishListHttpHelper.getCommerceWishListPortletURL(request)
	wish_lists_text = commerceThemeMiniumHttpHelper.getMyListsLabel(locale)
	my_profile_text = languageUtil.get(locale, "my-profile")
	notifications_text = languageUtil.get(locale, "notifications")
	notification_url = commerceThemeMiniumHttpHelper.getNotificationsURL(request)
	notification_count = commerceThemeMiniumHttpHelper.getNotificationsCount(themeDisplay)
	back_url = paramUtil.getString(request, "p_r_p_backURL")
	minium_content_css_class = "minium-content"
/>

<#if wide_layout>
	<#assign
	minium_content_css_class = "minium-content minium-content--wide"
	/>
</#if>

<#macro site_navigation_menu_main default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="siteNavigationMenuPortlet_main"
		portletName="com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	/>
</#macro>

<#macro site_navigation_menu_sub_navigation default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="siteNavigationMenuPortlet_sub_navigation"
		portletName="com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	/>
</#macro>