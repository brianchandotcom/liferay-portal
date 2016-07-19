<footer id="footer" role="contentinfo">

	<nav id="footer-recursive">
		<div class="container-fluid-1280">
			<div class="nav navbar-right small text-uppercase" role="menubar">
				<#assign VOID = freeMarkerPortletPreferences.setValue("displayDepth", "1")>
				<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")>

				<@liferay.navigation_menu
					instance_id="footer_navigation_menu"
					default_preferences="${freeMarkerPortletPreferences}"
				/>

				<#assign VOID = freeMarkerPortletPreferences.reset()>
			</div>
		</div>
	</nav>
</footer>