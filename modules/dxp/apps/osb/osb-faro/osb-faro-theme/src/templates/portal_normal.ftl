<!DOCTYPE html>

<#include init />

<html data-browser="${htmlUtil.escape(request.getHeader('user-agent'))}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>Analytics Cloud</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700|Source+Serif+Pro" rel="stylesheet">

	<#if is_signed_in>
		<link href="${htmlUtil.escape(portalUtil.getStaticResourceURL(request, "/o/osb-faro-web/dist/main.css"))}" rel="stylesheet" type = "text/css" />
	</#if>
</head>

<body class="dxp">
	<#if is_signed_in>
		<@liferay_portlet["runtime"] portletName="faro_portlet" />

		<script defer src="${htmlUtil.escape(portalUtil.getStaticResourceURL(request, "/o/osb-faro-web/dist/main.js"))}"></script>
	<#else>
		<@liferay_util["include"] page=top_head_include />

		<@liferay_util["include"] page=body_top_include />

		<@liferay_util["include"] page=content_include />

		<@liferay_util["include"] page=bottom_include />

		<@liferay_util["include"] page=body_bottom_include />
	</#if>

	<#if !is_signed_in>
		<script>
			const parsedUrl = new URL(window.location.href);
			const params = new URLSearchParams(parsedUrl.search);
			const paramName = params.get('_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName')?.replace('/login/', '') ?? null;

			if (paramName) {
				document.querySelector('.portlet-login').classList.add(paramName + '-screen')
			}
		</script>
	</#if>
</body>
</html>