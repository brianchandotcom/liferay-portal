<div aria-expanded="false" class="collapse navbar-collapse" id="navigationCollapse">
	<#if has_navigation && is_setup_complete>
		<nav class="${nav_css_class} site-navigation" id="navigation" role="navigation">
			<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")>

			<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />

			<#assign VOID = freeMarkerPortletPreferences.reset()>
		</nav>
	</#if>
</div>