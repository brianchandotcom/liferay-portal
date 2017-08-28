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

		<div class="main-wrapper" id="wrapper">
			<header class="navbar navbar-inverse navbar-ecommerce">
				<div id="banner" role="banner">
					<div class="navbar" id="firstHeading">
						<div class="navbar-left navbar-top-search">
							<#if show_main_search_icon>
								<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
							</#if>
						</div>

						<#include "${full_templates_path}/navigation_recursive.ftl" />
					</div>

					<div class="container-fluid" id="secondHeading">
						<#if has_navigation>
							<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
						</#if>

						<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
							<img alt="${logo_description}" height="56" src="${site_logo}" />

							<#if show_site_name>
								<h2 class="sr-only">${site_name}</h2>
							</#if>
						</a>

						<div class="navbar-right">
							<div class="navbar-left">
								<#if has_navigation>
									<#include "${full_templates_path}/navigation.ftl" />
								</#if>
							</div>

							<div class="navbar-left">
								<#include "${full_templates_path}/navigation_content.ftl" />
							</div>
						</div>
					</div>
				</div>
			</header>

			<main class="container-fluid" id="content" role="main">
				<h3 class="sr-only">${the_title}</h3>

				<#if demo_mode>
					<#include "${full_templates_path}/demo_content.ftl" />
				<#else>
					<#if selectable>
						<@liferay_util["include"] page=content_include />
					<#else>
						${portletDisplay.recycle()}

						${portletDisplay.setTitle(the_title)}

						<@liferay_theme["wrap-portlet"] page="portlet.ftl">
							<@liferay_util["include"] page=content_include />
						</@>
					</#if>
				</#if>
			</main>

			<#include "${full_templates_path}/footer.ftl" />
		</div>

		<@liferay_util["include"] page=body_bottom_include />

		<@liferay_util["include"] page=bottom_include />
	</body>
</html>