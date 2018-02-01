<#assign
	cartItemsCount = commerceCartHelper.getCurrentCommerceCartItemsCount(request, themeDisplay.getResponse(), 0)
	cartUrl = commerceCartHelper.getCommerceCartPortletURL(request, 0)
	currentAccount = (commerceCustomerPortalHelper.getCurrentOrganization(request, "account"))!""
	currentBranch = (commerceCustomerPortalHelper.getCurrentOrganization(request, "branch"))!""
	demo_mode = getterUtil.getBoolean(themeDisplay.getThemeSetting("demo-mode"))
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

<#macro commerce_search_organization default_preferences = "">
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletName="com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationSearchPortlet"
	/>
</#macro>