<#assign
	is_login_page = getterUtil.getBoolean(themeDisplay.getThemeSetting("is-login-page"))
	mini_cart_display_total_items_count = getterUtil.getBoolean(themeDisplay.getThemeSetting("mini-cart-display-total-items-count"))
	mini_cart_keep_open = getterUtil.getBoolean(themeDisplay.getThemeSetting("mini-cart-keep-open"))
	minium_content_css_class = "minium-content"
	my_profile_text = languageUtil.get(locale, "my-profile")
	notification_count = commerceThemeMiniumHttpHelper.getNotificationsCount(themeDisplay)
	notification_url = commerceThemeMiniumHttpHelper.getNotificationsURL(request)
	notifications_text = languageUtil.get(locale, "notifications")
	redirect_url = commerceThemeMiniumHttpHelper.getRedirectURL(request)
	show_account_selector = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-account-selector"))
	show_mini_cart = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-mini-cart"))
	show_search_bar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-search-bar"))
	show_sidebar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-sidebar"))
	show_top_menu = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-top-menu"))
	show_topbar = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-topbar"))
	userManagementUrl = commerceThemeMiniumHttpHelper.getAccountManagementPortletURL(request)
	wide_layout = getterUtil.getBoolean(themeDisplay.getThemeSetting("wide-layout"))
	wish_lists_text = commerceThemeMiniumHttpHelper.getMyListsLabel(locale)
	wishlistUrl = commerceWishListHttpHelper.getCommerceWishListPortletURL(request)
/>

<#if wide_layout>
	<#assign
	minium_content_css_class = "minium-content minium-content--wide"
	/>
</#if>

<#macro site_navigation_menu_main
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="siteNavigationMenuPortlet_main"
		portletName="com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	/>
</#macro>

<#macro site_navigation_menu_sub_navigation
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="siteNavigationMenuPortlet_sub_navigation"
		portletName="com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	/>
</#macro>