<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
</head>

<body class="${css_class}">

<@liferay.quick_access content_id="#main-content" />

${theme.include(body_top_include)}

<div class="${liferay_product_menu_state} sidenav-container" id="sidenavContainerId">
	<div class="sidenav-menu-slider">
		<div class="product-menu sidebar sidebar-inverse sidenav-menu">
			<@liferay.product_menu />
		</div>
	</div>

	<div class="sidenav-content">
		<div class="container-fluid" id="wrapper">
			<header id="banner" role="banner">
				<div id="heading">
					<h1 class="site-title">
						<a class="${logo_css_class}" href="${site_default_url}" key="go-to-x" title="<@liferay.language_format arguments="${site_name}" />">
							<img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" />
						</a>

						<#if show_site_name>
							<span class="site-name" key="go-to-x" title="<@liferay.language_format arguments="${site_name}" />">
								${site_name}
							</span>
						</#if>
					</h1>
				</div>

				<#if !is_signed_in>
					<a data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}" id="sign-in" rel="nofollow">${sign_in_text}</a>
				</#if>

				<#if has_navigation || is_signed_in>
					<#include "${full_templates_path}/navigation.ftl" />
				</#if>
			</header>

			<section id="content">
				<h1 class="hide-accessible">${the_title}</h1>

				<nav id="breadcrumbs">
					<@liferay.breadcrumbs />
				</nav>

				<#if selectable>
					${theme.include(content_include)}
				<#else>
					${portletDisplay.recycle()}

					${portletDisplay.setTitle(the_title)}

					${theme.wrapPortlet("portlet.ftl", content_include)}
				</#if>
			</section>

			<footer id="footer" role="contentinfo">
				<p class="powered-by">
					<@liferay.language key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
				</p>
			</footer>
		</div>

		<#if is_signed_in>
			<@liferay.control_menu />
		</#if>
	</div>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>