<#assign
	orderItemsQuantity = commerceOrderHelper.getCommerceOrderItemsQuantity(request, themeDisplay.getResponse())
	cartUrl = commerceOrderHelper.getCommerceCartPortletURL(request)
	currentOrganization = (commerceOrganizationHelper.getCurrentOrganization(request))!""
	demo_mode = getterUtil.getBoolean(themeDisplay.getThemeSetting("demo-mode"))
	wishListItemsCount = commerceWishListHelper.getCurrentCommerceWishListItemsCount(request, themeDisplay.getResponse())
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

<#macro commerce_search_organization default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletName="com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationSearchPortlet"
	/>
</#macro>