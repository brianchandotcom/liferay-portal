<#ftl strip_whitespace=true>

<#--
Use computer number format to prevent issues with locale settings. See
LPS-30525.
-->

<#setting number_format = "computer">

<#assign css_main_file = "" />
<#assign is_signed_in = false />
<#assign js_main_file = "" />

<#if themeDisplay??>
	<#assign css_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeCss()}/main.css")) />
	<#assign is_signed_in = themeDisplay.isSignedIn() />
	<#assign js_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeJavaScript()}/main.js")) />
</#if>

<#assign is_setup_complete = false />

<#if user??>
	<#assign is_setup_complete = user.isSetupComplete() />
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

<#macro breadcrumbs
	default_preferences = ""
>
	${theme.runtime("com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry", portletProviderAction.VIEW, "", default_preferences)}
</#macro>

<#macro control_menu>
	<#if is_setup_complete && is_signed_in>
		${theme.runtime("com.liferay.portlet.admin.util.PortalControlMenuApplicationType$ControlMenu", portletProviderAction.VIEW)}
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
	${theme.runtime("com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry", portletProviderAction.VIEW, "", default_preferences)}
</#macro>

<#macro navigation_menu
	default_preferences = ""
>
	${theme.runtime("com.liferay.portal.theme.NavItem", portletProviderAction.VIEW, "", default_preferences)}
</#macro>

<#macro product_menu>
	<#if is_setup_complete && is_signed_in>
		${theme.runtime("com.liferay.portlet.admin.util.PortalProductMenuApplicationType$ProductMenu", portletProviderAction.VIEW)}
	</#if>
</#macro>

<#macro product_menu_sidebar
	state
>
	<#if is_setup_complete && is_signed_in>
		<div class="${state} lfr-product-menu-panel sidenav-fixed sidenav-menu-slider" id="sidenavSliderId">
			<div class="product-menu sidebar sidenav-menu">
				<@liferay.product_menu />
			</div>
		</div>
	</#if>
</#macro>

<#macro quick_access
	content_id
>
	${theme.quickAccess(content_id)}
</#macro>

<#macro search
	default_preferences = ""
>
	<#if is_setup_complete>
		${theme.runtime("com.liferay.portlet.admin.util.PortalSearchApplicationType$Search", portletProviderAction.VIEW, "", default_preferences)}
	</#if>
</#macro>

<#macro silently
	foo
>
	<#assign foo = foo />
</#macro>

<#macro user_personal_bar>
	<#if is_setup_complete>
		${theme.runtime("com.liferay.portlet.admin.util.PortalUserPersonalBarApplicationType$UserPersonalBar", portletProviderAction.VIEW)}
	</#if>
</#macro>