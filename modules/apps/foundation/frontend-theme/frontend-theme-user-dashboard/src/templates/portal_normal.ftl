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

<div class="container-fluid-1280" id="wrapper">
	<header class="col-md-12 panel" id="banner" role="banner">
		<div class="row">
			<div id="heading">
				<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header navbar-header-left-xs">
					<#if page_group.isUser()>
						<div class="aspect-ratio-bg-cover pull-left text-center user-container">
							<div class="user-info">
								<div class="aspect-ratio-bg-cover user-icon user-icon-xl" ${main_menu_style}></div>
							</div>
						</div>
					<#else>
						<a class="${logo_css_class} pull-left" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
							<img alt="${logo_description}" height="64" src="${site_logo}" width="64" />
						</a>
					</#if>

					<#if show_site_name || page_group.isUser()>
						<div class="pull-left site-name" title="#language_format ("go-to-x", [$site_name])">
							${layout_set_title}
						</div>
					</#if>
				</div>

				<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")>

				<nav class="user-personal-bar">
					<ul class="nav navbar-nav pull-right">
						<@liferay.user_personal_bar />
					</ul>
				<nav>

				<#assign VOID = freeMarkerPortletPreferences.reset()>
			</div>
		</div>
	</header>

	<div class="col-md-12" id="userCard">
		<div aria-expanded="false" class="collapse navbar-collapse panel row" id="navigationCollapse">
			<#include "${full_templates_path}/navigation.ftl" />
		</div>
	</div>

	<section class="col-md-12" id="content">
		<div class="panel">
			<#if selectable>
				<@liferay_util["include"] page=content_include />
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				<@liferay_theme["wrap-portlet"] page="portlet.ftl">
					<@liferay_util["include"] page=content_include />
				</@>
			</#if>
		</div>
	</section>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

</body>

</html>