<div class="col-no-padding row">
	<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

	<div class="col-md-3">
		<@liferay.navigation_menu default_preferences=preferences />
	</div>

	<div class="col-md-3">
		<@liferay.navigation_menu default_preferences=preferences />
	</div>
</div>