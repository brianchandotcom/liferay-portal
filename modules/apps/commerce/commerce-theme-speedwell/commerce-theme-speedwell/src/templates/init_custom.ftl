<#assign
	copyright = getterUtil.getString(themeDisplay.getThemeSetting("copyright"))
	mini_cart_display_total_items_count = getterUtil.getBoolean(themeDisplay.getThemeSetting("mini-cart-display-total-items-count"))
	mini_cart_keep_open = getterUtil.getBoolean(themeDisplay.getThemeSetting("mini-cart-keep-open"))
	show_mini_cart = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-mini-cart"))
	show_top_menu = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-top-menu"))
	my_account_url = themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getScopeGroup().getFriendlyURL() + "/my-account"
	wishlistUrl = commerceWishListHttpHelper.getCommerceWishListPortletURL(request)
	wish_lists_text = commerceThemeMiniumHttpHelper.getMyListsLabel(locale)
	notifications_text = languageUtil.get(locale, "notifications")
	notification_url = commerceThemeMiniumHttpHelper.getNotificationsURL(request)
	notification_count = commerceThemeMiniumHttpHelper.getNotificationsCount(themeDisplay)
	wide_layout = getterUtil.getBoolean(themeDisplay.getThemeSetting("wide-layout"))
	back_url = paramUtil.getString(request, "p_r_p_backURL")
	speedwell_content_css_class = "speedwell-content"
	translucent_topbar = getterUtil.getBoolean(themeDisplay.getThemeSetting("translucent-topbar"))
	speedwell_topbar_css_class = "speedwell-topbar"
/>

<#if !is_setup_complete && is_signed_in>
	<#assign translucent_topbar = false />
</#if>

<#if is_maximized>
	<#assign
		translucent_topbar = false
		wide_layout = false
	/>
</#if>

<#if wide_layout>
	<#assign
	speedwell_content_css_class = "speedwell-content speedwell-content--wide"
	/>
</#if>

<#if translucent_topbar>
	<#assign
	speedwell_topbar_css_class = "speedwell-topbar speedwell-topbar--translucent"
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

<#macro site_navigation_menu_account default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="siteNavigationMenuPortlet_account"
		portletName="com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	/>
</#macro>

<#macro commerce_category_navigation_menu default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="cpAssetCategoriesNavigationPortlet_navigation_menu"
		portletName="com_liferay_commerce_product_asset_categories_navigation_web_internal_portlet_CPAssetCategoriesNavigationPortlet"
	/>
</#macro>