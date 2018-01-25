<#ftl strip_whitespace=true>

<#--
Use computer number format to prevent issues with locale settings. See
LPS-30525.
-->

<#setting number_format = "computer">

<#assign
	css_main_file = ""
	is_signed_in = false
	js_main_file = ""
	is_setup_complete = false
/>

<#if user??>
	<#assign is_setup_complete = user.isSetupComplete() />
</#if>

<#if themeDisplay??>
	<#assign css_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeCss()}/main.css")) />
	<#assign is_signed_in = themeDisplay.isSignedIn() />
	<#assign js_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeJavaScript()}/main.js")) />

	<#if !is_setup_complete>
		<#assign is_setup_complete = themeDisplay.isImpersonated() />
	</#if>
</#if>

<#function max x y>
	<#if x < y>
		<#return y>
	<#else>
		<#return x>
	</#if>
</#function>

<#function min x y>
	<#if x <= y>
		<#return x>
	<#else>
		<#return y>
	</#if>
</#function>

<#macro breadcrumbs
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry"
	/>
</#macro>

<#macro control_menu>
	<#if is_setup_complete && is_signed_in>
		<@liferay_product_navigation["control-menu"] />
	</#if>
</#macro>

<#macro css
	file_name
>
	<#if file_name == css_main_file>
		<link class="lfr-css-file" href="${file_name}" id="mainLiferayThemeCSS" rel="stylesheet" type="text/css" />
	<#else>
		<link class="lfr-css-file" href="${file_name}" rel="stylesheet" type="text/css" />
	</#if>
</#macro>

<#macro date
	format
>
${dateUtil.getCurrentDate(format, locale)}</#macro>

<#macro js
	file_name
>
	<#if file_name == js_main_file>
		<script id="mainLiferayThemeJavaScript" src="${file_name}" type="text/javascript"></script>
	<#else>
		<script src="${file_name}" type="text/javascript"></script>
	</#if>
</#macro>

<#macro language
	key
>
${languageUtil.get(locale, key)}</#macro>

<#macro language_format
	arguments
	key
>
${languageUtil.format(locale, key, arguments)}</#macro>

<#macro languages
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry"
	/>
</#macro>

<#macro navigation_menu
	default_preferences = ""
	instance_id = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId=instance_id
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.theme.NavItem"
	/>
</#macro>

<#macro search
	default_preferences = ""
>
	<#if is_setup_complete>
		<@liferay_portlet["runtime"]
			defaultPreferences=default_preferences
			portletProviderAction=portletProviderAction.VIEW
			portletProviderClassName="com.liferay.admin.kernel.util.PortalSearchApplicationType$Search"
		/>
	</#if>
</#macro>

<#macro search_bar
	default_preferences = ""
>
	<#if is_setup_complete>
		<@liferay_portlet["runtime"]
			defaultPreferences=default_preferences
			instanceId="templateSearch"
			portletProviderAction=portletProviderAction.VIEW
			portletProviderClassName="com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet"
		/>
	</#if>
</#macro>

<#macro silently
	foo
>
	<#assign foo = foo />
</#macro>

<#macro user_personal_bar>
	<#if is_setup_complete || !is_signed_in>
		<@liferay_portlet["runtime"]
			portletProviderAction=portletProviderAction.VIEW
			portletProviderClassName="com.liferay.admin.kernel.util.PortalUserPersonalBarApplicationType$UserPersonalBar"
		/>
	</#if>
</#macro>