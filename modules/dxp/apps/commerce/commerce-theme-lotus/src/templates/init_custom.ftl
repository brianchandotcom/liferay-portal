<#assign demo_mode = getterUtil.getBoolean(themeDisplay.getThemeSetting("demo-mode")) />

 <#macro commerce_category_navigation_menu default_preferences = "">
    <@liferay_portlet["runtime"]
        defaultPreferences=default_preferences
        portletName="com_liferay_commerce_product_asset_category_navigation_web_internal_portlet_CPAssetCategoryNavigationPortlet"
    />
</#macro>