<footer class="footer" id="footer" role="contentinfo">
	<nav id="navbar-footer">
		<div class="container-fluid-1280">
			<div class="row small">
				<div class="col-md-2 nav nav-stacked" role="menubar">
					<#assign preferencesMap = {"displayStyle": "ddmTemplate_NAV-PILLS-STACKED-FTL", "portletSetupPortletDecoratorId": "decorate"} />

					<@liferay.navigation_menu
						default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
						instance_id="footer_navigation_menu_01"
					/>
				</div>

				<div class="col-md-2 nav nav-stacked" role="menubar">
					<@liferay.navigation_menu
						default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
						instance_id="footer_navigation_menu_02"
					/>
				</div>

				<div class="col-md-2 nav nav-stacked" role="menubar">
					<@liferay.navigation_menu
						default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
						instance_id="footer_navigation_menu_03"
					/>
				</div>

				<div class="col-md-5 col-md-offset-1 nav" role="menubar">
					<h2 class="h4">FOLLOW US</h2>
					<p class="h2">
						<span class="icon-twitter"></span>
						<span class="icon-facebook"></span>
						<span class="icon-dribbble"></span>
						<span class="icon-linkedin"></span>
						<span class="icon-comments"></span>
						<span class="icon-github-alt"></span>
					</p>

					<p>Powered and designed by Liferay</p>
				</div>
			</div>
		</div>
	</nav>
</footer>