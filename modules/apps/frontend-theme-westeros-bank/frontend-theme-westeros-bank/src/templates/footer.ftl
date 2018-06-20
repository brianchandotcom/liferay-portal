<footer id="footer" role="contentinfo">
	<nav id="footer-recursive">
		<div class="container-fluid container-fluid-max-xl">
			<div class="row">
				<div class="ml-auto nav small text-uppercase" role="menubar">
					<#assign preferencesMap = {"displayDepth": "1", "portletSetupPortletDecoratorId": "barebone"} />

					<@liferay.navigation_menu
						default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
						instance_id="footer_navigation_menu"
					/>
				</div>
			</div>
		</div>
	</nav>
</footer>