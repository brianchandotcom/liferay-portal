<div class="col-md-6">
	<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

	<div class="col-md-4">
		<@liferay.navigation_menu default_preferences=preferences />
	</div>

	<div class="col-md-4">
		<@liferay.navigation_menu default_preferences=preferences />
	</div>

	<div class="col-md-4">
		<@liferay.navigation_menu default_preferences=preferences />
	</div>
</div>