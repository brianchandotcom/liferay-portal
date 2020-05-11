<!DOCTYPE html>

<#include init />

<html
	class="${root_css_class}" dir="<@liferay.language key="lang.dir" />"
	  lang="${w3c_language_id}">

<head>
	<title>${html_title}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<div class="d-flex flex-column min-vh-100">
	<div class="d-flex flex-column flex-fill pt-0" id="wrapper">
		<section class="${portal_content_css_class} flex-fill" id="content">
			<#assign preferences = freeMarkerPortletPreferences.getPreferences({"portletSetupPortletDecoratorId": "barebone"}) />

			<@liferay_portlet["runtime"]
				defaultPreferences="${preferences}"
				portletName="com_liferay_headless_discovery_web_internal_portlet_HeadlessDiscoveryPortlet"
				portletProviderAction=portletProviderAction.VIEW
			/>
		</section>
	</div>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

</body>

</html>