<footer id="footer" role="contentinfo">

	<nav id="footer-recursive">
		<div class="container-fluid-1280">
			<div class="nav navbar-right small text-uppercase" role="menubar">
				<#assign preferencesMap = {"displayDepth":"1", "portletSetupPortletDecoratorId":"barebones"} />

				<@liferay.navigation_menu
					instance_id="footer_navigation_menu"
					default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
				/>
			</div>
		</div>
	</nav>
</footer>