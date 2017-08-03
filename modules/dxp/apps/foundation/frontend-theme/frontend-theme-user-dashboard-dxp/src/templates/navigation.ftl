<#if has_navigation && is_setup_complete>
	<nav class="${nav_css_class} site-navigation" id="navigation" role="navigation">
		<@liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</nav>
</#if>