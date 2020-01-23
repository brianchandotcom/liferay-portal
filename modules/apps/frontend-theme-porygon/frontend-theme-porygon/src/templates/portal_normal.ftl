<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">
	<head>
		<title>${html_title}</title>

		<meta content="initial-scale=1.0, width=device-width" name="viewport" />

		<@liferay_util["include"] page=top_head_include />
	</head>

	<body class="${css_class}">
		<@liferay_ui["quick-access"] contentId="#main-content" />

		<@liferay_util["include"] page=body_top_include />

		<@liferay.control_menu />

		<div id="wrapper">
			<#if show_header>
				<header class="navbar navbar-dark navbar-expand-md navbar-porygon">
					<div class="container-fluid" id="banner" role="banner">
						<div class="navbar-header" id="heading">
							<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
								<img alt="${logo_description}" height="56" src="${site_logo}" />
								<#if show_site_name>
									${site_name}
								</#if>
							</a>
						</div>

						<#if has_navigation>
							<button aria-controls="navigation" aria-expanded="false" class="btn-monospaced ml-auto navbar-toggler" data-target="#navigationCollapse" data-toggle="collapse" type="button">
								<span class="navbar-toggler-icon"></span>
							</button>

							<#include "${full_templates_path}/navigation.ftl" />
						</#if>
					</div>
				</header>
			</#if>

			<main id="content" role="main">
				<h2 class="hide-accessible" role="heading" aria-level="1">${the_title}</h2>

				<#if selectable>
					<@liferay_util["include"] page=content_include />
				<#else>
					${portletDisplay.recycle()}

					${portletDisplay.setTitle(the_title)}

					<@liferay_theme["wrap-portlet"] page="portlet.ftl">
						<@liferay_util["include"] page=content_include />
					</@>
				</#if>
			</main>

			<#if show_footer>
				<#include "${full_templates_path}/footer.ftl" />
			</#if>
		</div>

		<@liferay_util["include"] page=body_bottom_include />

		<@liferay_util["include"] page=bottom_include />
	</body>
</html>