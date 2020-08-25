<div class="minium-sidebar">
	<div class="minium-sidebar__start">
		<div class="minium-logo">
			<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
				<img alt="${logo_description}" class="logo-image-sm" src="${site_logo}" />
			</a>
		</div>
	</div>

	<div class="minium-sidebar__middle">
		<@site_navigation_menu_main default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</div>

	<div class="minium-sidebar__end">
		<#include "${full_templates_path}/user_nav.ftl" />
	</div>
</div>