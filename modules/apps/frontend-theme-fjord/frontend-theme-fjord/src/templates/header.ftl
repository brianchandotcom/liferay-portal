<header class="bg-primary fjord-bg-primary header py-2 text-white" role="banner">
	<div class="container">
		<div class="row">
			<div class="col-12">
				<nav class="navbar ${nav_collapse}">
					<a class="text-reset navbar-brand ${logo_css_class}" href="${site_default_url}" rel="home" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
						<img alt="${logo_description}" height="${company_logo_height}" src="${site_logo}" width="${company_logo_width}" />

						<#if show_site_name>
							<span class="align-middle d-inline-block ml-3" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
								${site_name}
							</span>
						</#if>
					</a>

					<button aria-controls="navigationCollapse" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler text-reset" data-target="#navigationCollapse" data-toggle="collapse" type="button">
						<span class="d-flex fjord-navbar-toggler-icon flex-column justify-content-center">
							<span class="d-block"></span>

							<span class="d-block my-1"></span>

							<span class="d-block"></span>
						</span>
					</button>

					<#if has_navigation && is_setup_complete>
						<div aria-expanded="false" class="collapse navbar-collapse" id="navigationCollapse">
							<#include full_templates_path + "/navigation.ftl">
						</div>
					</#if>
				</nav>
			</div>
		</div>
	</div>
</header>