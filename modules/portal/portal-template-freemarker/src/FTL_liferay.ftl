<#ftl strip_whitespace=true>

<#--
Use computer number format to prevent issues with locale settings. See
LPS-30525.
-->

<#setting number_format="computer">

<#assign css_main_file = "" />

<#if themeDisplay??>
	<#assign css_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeCss()}/main.css")) />
</#if>

<#assign js_main_file = "" />

<#if themeDisplay??>
	<#assign js_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeJavaScript()}/main.js")) />
</#if>

<#function max x y>
	<#if (x < y)>
		<#return y>
	<#else>
		<#return x>
	</#if>
</#function>

<#function min x y>
	<#if (x > y)>
		<#return y>
	<#else>
		<#return x>
	</#if>
</#function>

<#macro breadcrumbs>
	${theme.runtime("com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry", portletProviderAction.VIEW)}
</#macro>

<#macro control_menu>
	<#if $is_setup_complete && $is_signed_in>
		${theme.runtime("com.liferay.portlet.admin.util.PortalControlMenuApplicationType$ControlMenu", portletProviderAction.VIEW)}
	</#if>
</#macro>

<#macro css
	file_name
>
	<#if file_name = css_main_file>
		<link class="lfr-css-file" href="${file_name}" id="mainLiferayThemeCSS" rel="stylesheet" type="text/css" />
	<#else>
		<link class="lfr-css-file" href="${file_name}" rel="stylesheet" type="text/css" />
	</#if>
</#macro>

<#macro date
	format
>
${dateUtil.getCurrentDate(format, locale)}</#macro>

<#macro dockbar>
	${theme.runtime("145")}
</#macro>

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

<#macro languages>
	${theme.runtime("com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry", portletProviderAction.VIEW)}
</#macro>

<#macro product_menu>
	<#if $is_setup_complete && $is_signed_in>
		${theme.runtime("com.liferay.portlet.admin.util.PortalProductMenuApplicationType$ProductMenu", portletProviderAction.VIEW)}
	</#if>
</#macro>

<#macro quick_access
	content_id
>
	${theme.quickAccess(content_id)}
</#macro>

<#macro search>
	<#if $is_setup_complete>
		${theme.runtime("com.liferay.portlet.admin.util.PortalSearchApplicationType$Search", portletProviderAction.VIEW)}
	</#if>
</#macro>

<#macro silently
	foo
>
	<#assign foo = foo />
</#macro>

<#macro user_personal_bar>
	<#if $is_setup_complete>
		${theme.runtime("com.liferay.portlet.admin.util.PortalUserPersonalBarApplicationType$UserPersonalBar", portletProviderAction.VIEW)}
	</#if>
</#macro>