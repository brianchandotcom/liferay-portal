<#assign
	accountPageFriendlyUrl = getterUtil.getString(themeDisplay.getThemeSetting("account-page-friendly-url"))
	accountUrl = "${site_default_url}" + accountPageFriendlyUrl
	cartItemsCount = commerceCartHelper.getCurrentCommerceCartItemsCount(request, themeDisplay.getResponse())
	cartUrl = commerceCartHelper.getCommerceCartPortletURL(request)
	demo_mode = getterUtil.getBoolean(themeDisplay.getThemeSetting("demo-mode"))
	show_cart_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-cart-icon"))
	show_main_search_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-main-search-icon"))
	show_wishlist_icon = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-wishlist-icon"))
	wishListItemsCount = commerceWishListHelper.getCurrentCommerceWishListItemsCount(request)
	wishlistUrl = commerceWishListHelper.getCommerceWishListPortletURL(request)
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