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

				<ul class="col-md-5 ml-md-auto nav social-set" role="menubar">
					<h2 class="h4">FOLLOW US</h2>
					<li>
						<span class="lexicon-icon icon-twitter"></span>
						twitter
					</li>
					<li>
						<span class="lexicon-icon icon-facebook"></span>
						facebook
					</li>
					<li>
						<span class="lexicon-icon icon-dribbble"></span>
						dribbble
					</li>
					<li>
						<span class="lexicon-icon icon-linkedin"></span>
						linkedin
					</li>
					<li>
						<span class="lexicon-icon icon-comments"></span>
						e-mail
					</li>
				</ul>
			</div>

			<div class="row my-3">
				<p>Powered and designed by Liferay</p>
			</div>
		</div>
	</nav>
</footer>