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

<#macro breadcrumbs
	control_panel = ""
>
	<#if control_panel = "control_panel">
		${theme.breadcrumb(0, false, false, true, true)}
	<#else>
		${theme.breadcrumb()}
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

<#macro ie6_png_fix>
<#if browserSniffer.isIe(request) && browserSniffer.getMajorVersion(request) < 7>
/* ---------- IE6 PNG image fix ---------- */
img, .png {
	position: relative;
	behavior: expression(
		(this.runtimeStyle.behavior = "none") &&
		(
			this.pngSet || (this.src && this.src.toLowerCase().indexOf('spacer.png') > -1) ?
				this.pngSet = true :
					(
						this.nodeName == "IMG" &&
						(
							(
								(this.src.toLowerCase().indexOf('.png') > -1) ||
								(this.className && ([''].concat(this.className.split(' ')).concat(['']).join('|').indexOf('|png|')) > -1)
							) &&
							(this.className.indexOf('no-png-fix') == -1)
						) ?
							(
								this.runtimeStyle.backgroundImage = "none",
								this.runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + this.src + "', sizingMethod='image')",
								this.src = "${images_folder}/spacer.png"
							) :
								(
									(
										(this.currentStyle.backgroundImage.toLowerCase().indexOf('.png') > -1) ||
										(this.className && ([''].concat(this.className.split(' ')).concat(['']).join('|').indexOf('|png|')) > -1)
									) ?
										(
												this.origBg = this.origBg ?
													this.origBg :
													this.currentStyle.backgroundImage.toString().replace('url("','').replace('")',''),
													this.runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + this.origBg + "', sizingMethod='crop')",
													this.runtimeStyle.backgroundImage = "none"
										) :
											''
								)
					),
					this.pngSet = true
		)
	);
}
</#if>
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

<#macro quick_acess
	content_id
>
	${theme.quick_acess(content_id)}
</#macro>

<#macro silently
	foo
>
	<#assign foo = foo />
</#macro>