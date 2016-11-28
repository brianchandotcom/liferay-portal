<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone") />

<div aria-expanded="false" class="${nav_collapse} collapse" id="navigationCollapse">
	<#if has_navigation && is_setup_complete>
		<div class="${nav_css_class} ${nav_css_right} site-navigation" id="navigation" role="navigation">
			<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />
		</div>
	</#if>
</div>

<#assign VOID = freeMarkerPortletPreferences.reset() />