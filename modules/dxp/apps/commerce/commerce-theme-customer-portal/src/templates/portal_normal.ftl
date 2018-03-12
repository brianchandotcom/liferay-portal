<!DOCTYPE html>

<#include init />

<#assign signed_out_class = "">

<#if !is_signed_in>
	<#assign signed_out_class = " b2b-login">
</#if>

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">
	<head>
		<title>${the_title} - ${company_name}</title>

		<meta content="initial-scale=1.0, width=device-width" name="viewport" />

		<@liferay_util["include"] page=top_head_include />
	</head>

	<body class="${css_class}${signed_out_class}">
		<@liferay_ui["quick-access"] contentId="#main-content" />

		<@liferay_util["include"] page=body_top_include />

		<@liferay.control_menu />

		<div class="main-wrapper" id="wrapper">
			<#if is_signed_in && validator.isNotNull(currentOrganization)>
				<div class="b2b-site-body container-fluid container-fluid-max-xl">
					<#include "${full_templates_path}/application_bar.ftl" />

					<div class="b2b-site-navigation-open b2b-wrapper">
						<#include "${full_templates_path}/sidebar.ftl" />

						<main class="container-fluid" id="content" role="main">
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
				</div>
			<#else>
				<#include "${full_templates_path}/login.ftl" />
			</#if>
		</div>

		<@liferay_util["include"] page=body_bottom_include />

		<@liferay_util["include"] page=bottom_include />
	</body>
</html>