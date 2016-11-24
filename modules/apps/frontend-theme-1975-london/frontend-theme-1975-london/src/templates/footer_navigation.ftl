<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone") />

<div class="col-no-padding row">
	<div class="col-md-3">
		<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />
	</div>

	<div class="col-md-3">
		<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />
	</div>
</div>

<#assign VOID = freeMarkerPortletPreferences.reset() />