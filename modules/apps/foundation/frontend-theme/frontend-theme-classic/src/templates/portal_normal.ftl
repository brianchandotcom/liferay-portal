<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<@liferay.control_menu />

<div class="pt-0" id="wrapper">
	<header id="banner">
		<div class="navbar navbar-classic navbar-top">
			<div class="container user-personal-bar">
				<#assign preferences = freeMarkerPortletPreferences.getPreferences({"portletSetupPortletDecoratorId": "barebone", "destination": "/search"}) />

				<#if show_header_search>
					<div class="ml-auto mr-4 navbar-form" role="search">
						<@liferay.search_bar default_preferences="${preferences}" />
					</div>
				</#if>

				<@liferay.user_personal_bar />
			</div>
		</div>

		<div class="mb-4 navbar navbar-expand-md navbar-light navbar-classic py-4">
			<div class="container">
				<a class="${logo_css_class} align-items-center d-inline-flex" href="${site_default_url}" title="<@liferay.language_format arguments="" key="go-to-x" />">
					<img alt="${logo_description}" class="mr-3" height="48" src="${site_logo}" />

					<#if show_site_name>
						<h1 class="font-weight-bold h2 mb-0 text-dark">${site_name}</h1>
					</#if>
				</a>

				<#include "${full_templates_path}/navigation.ftl" />
			</div>
		</div>
	</header>

	<section class="container" id="content">
		<h1 class="sr-only">${the_title}</h1>

		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>
	</section>

	<footer id="footer" role="contentinfo">
		<div class="container">
			<div class="row">
				<div class="col-md-6 text-center text-md-left">
					<@liferay.language key="powered-by" />

					<a class="text-white" href="http://www.liferay.com" rel="external">Liferay</a>
				</div>

				<div class="col-md-6 text-center text-md-right">
					2018
				</div>
			</div>
		</div>
	</footer>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

</body>

</html>