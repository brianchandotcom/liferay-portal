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

		<div id="wrapper">
			<div class="navbar-recursive">
				<nav class="navbar navbar-inverse">
					<div class="navbar-wrapper">
						<div class="container-fluid-1280" id="headerTopBar">
							<#if main_recursive_menu_class != "no-screen">
								<div class="${main_recursive_menu_class} nav navbar-nav">
									<#assign
										VOID = freeMarkerPortletPreferences.setValue("displayDepth", "1")
										VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")
									/>

									<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />

									<#assign VOID = freeMarkerPortletPreferences.reset() />
								</div>
							</#if>

							<div class="user-area">
								<div class="nav pull-right">
									<ul class="navbar-nav">
										<#if !is_signed_in>
											<span class="icon-login icon-monospaced">
												<svg class="lexicon-icon">
													<use xlink:href="${images_folder}/lexicon/icons.svg#users" />
												</svg>
											</span>
										</#if>

										<@liferay.user_personal_bar />
									</ul>
								</div>
							</div>
						</div>
					</div>
				</nav>
			</div>

			<header class="container-fluid-1280" id="banner" role="banner">
				<div class="navbar-header" id="heading">
					<#if has_navigation>
						<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
					</#if>

					<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
						<img alt="${logo_description}" height="56" src="${site_logo}" />
					</a>
				</div>

				<#if has_navigation>
					<#include "${full_templates_path}/navigation.ftl" />
				</#if>
			</header>

			<main id="content" role="main">
				<h1 class="hide-accessible">${the_title}</h1>

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

			<#include "${full_templates_path}/footer.ftl" />
		</div>

		<@liferay_util["include"] page=body_bottom_include />

		<@liferay_util["include"] page=bottom_include />
	</body>
</html>