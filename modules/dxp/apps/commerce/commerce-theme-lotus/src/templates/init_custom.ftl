<#assign
	accountUrl = "${site_default_url}/my-account"
	cartItemsCount = commerceCartHelper.getCurrentCommerceCartItemsCount(request, themeDisplay.getResponse(), 0)
	cartUrl = commerceCartHelper.getCommerceCartPortletURL(request, 0)
	demo_mode = getterUtil.getBoolean(themeDisplay.getThemeSetting("demo-mode"))
	show_cart_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-cart-icon"))
	show_main_search_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-main-search-icon"))
	show_wishlist_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-wishlist-icon"))
	wishListItemsCount = commerceCartHelper.getCurrentCommerceCartItemsCount(request, themeDisplay.getResponse(), 1)
	wishlistUrl = commerceCartHelper.getCommerceCartPortletURL(request, 1)
/>

<#macro commerce_category_navigation_menu default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="cpAssetCategoriesNavigationPortlet_navigation_menu"
		portletName="com_liferay_commerce_product_asset_categories_navigation_web_internal_portlet_CPAssetCategoriesNavigationPortlet"
	/>
</#macro>

<#macro commerce_cart_mini default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="commerceCartContentMiniPortlet_0"
		portletName="com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet"
		queryString="type=0"
	/>
</#macro>

<#macro commerce_wish_list_mini default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId="commerceCartContentMiniPortlet_1"
		portletName="com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet"
		queryString="type=1"
	/>
</#macro>