<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

${theme.include(body_top_include)}

<@liferay.product_menu_sidebar state="${liferay_product_menu_state!}" />

<#if is_setup_complete && is_signed_in>
	<@liferay_control_menu["control-menu"] />
</#if>

<div id="wrapper">
	<header class="container-fluid-1280" id="banner" role="banner">
		<div class="row">
			<div class="navbar-header" id="heading">
				<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<img alt="${logo_description}" height="64" src="${site_logo}" width="64" />
				</a>

				<#if show_site_name>
					<span class="site-name" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
						${site_name}
					</span>
				</#if>
			</div>

			<#include "${full_templates_path}/navigation.ftl" />
		</div>
	</header>

	<section class="container-fluid-1280" id="content">
		<h1 class="hide-accessible">${the_title}</h1>

		<#if selectable>
			${theme.include(content_include)}
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				${theme.include(content_include)}
			</@>
		</#if>
	</section>

	<footer class="container-fluid-1280" id="footer" role="contentinfo">
		<div class="row">
			<@liferay.language key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
		</div>
	</footer>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>