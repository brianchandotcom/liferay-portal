<footer class="commerce-footer" id="footer" role="contentinfo">
	<div class="container-fluid">
		<@liferay_portlet["runtime"]
			instanceId="minetteFooterWebContent_0"
			persistSettings=true
			portletName="com_liferay_journal_content_web_portlet_JournalContentPortlet"
		/>
	</div>

	<div class="container-fluid container-fluid-max-xl">
		<div class="autofit-float autofit-padded autofit-row footer-main-section">
			<div class="autofit-col autofit-col-expand footer-newsletter footer-section-column portlet-flush">
				<@liferay_portlet["runtime"]
					instanceId="minetteFooterNewsletterForm_0"
					persistSettings=true
					portletName="com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet"
				/>
			</div>

			<div class="autofit-col autofit-col-expand footer-section-column">
				<h3 class="footer-list-title">Title One</h3>
				<!-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. This is just a placeholder. -->

				<ul class="footer-list list-unstyled">
					<li><a class="footer-link" href="#placeholder">Link One</a></li>
					<li><a class="footer-link" href="#placeholder">Link Two</a></li>
					<li><a class="footer-link" href="#placeholder">Link Three</a></li>
					<li><a class="footer-link" href="#placeholder">Link Four</a></li>
					<li><a class="footer-link" href="#placeholder">Link Five</a></li>
					<li><a class="footer-link" href="#placeholder">Link Six</a></li>
					<li><a class="footer-link" href="#placeholder">Link Seven</a></li>
					<li><a class="footer-link" href="#placeholder">Link Eight</a></li>
				</ul>
			</div>

			<div class="autofit-col autofit-col-expand footer-section-column">
				<h3 class="footer-list-title">Title Two</h3>
				<!-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. This is just a placeholder. -->

				<ul class="footer-list list-unstyled">
					<li><a class="footer-link" href="#placeholder">Link One</a></li>
					<li><a class="footer-link" href="#placeholder">Link Two</a></li>
					<li><a class="footer-link" href="#placeholder">Link Three</a></li>
					<li><a class="footer-link" href="#placeholder">Link Four</a></li>
					<li><a class="footer-link" href="#placeholder">Link Five</a></li>
					<li><a class="footer-link" href="#placeholder">Link Six</a></li>
					<li><a class="footer-link" href="#placeholder">Link Seven</a></li>
					<li><a class="footer-link" href="#placeholder">Link Eight</a></li>
				</ul>
			</div>

			<div class="autofit-col autofit-col-expand footer-section-column">
				<h3 class="footer-list-title">Title Three</h3>
				<!-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. This is just a placeholder. -->

				<ul class="footer-list list-unstyled">
					<li><a class="footer-link" href="#placeholder">Link One</a></li>
					<li><a class="footer-link" href="#placeholder">Link Two</a></li>
					<li><a class="footer-link" href="#placeholder">Link Three</a></li>
					<li><a class="footer-link" href="#placeholder">Link Four</a></li>
					<li><a class="footer-link" href="#placeholder">Link Five</a></li>
					<li><a class="footer-link" href="#placeholder">Link Six</a></li>
					<li><a class="footer-link" href="#placeholder">Link Seven</a></li>
					<li><a class="footer-link" href="#placeholder">Link Eight</a></li>
				</ul>
			</div>

			<div class="autofit-col footer-section-column">
				<!-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. This is just a placeholder. -->

				<ul class="nav nav-stacked">
					<li class="nav-item">
						<a href="#1">
							<@liferay_aui.icon
								image="social-facebook"
								markupView="lexicon"
							/>
						</a>
					</li>
					<li class="nav-item">
						<a href="#1">
							<@liferay_aui.icon
								image="twitter"
								markupView="lexicon"
							/>
						</a>
					</li>
					<li class="nav-item">
						<a href="#1">
							<@liferay_aui.icon
								image="social-linkedin"
								markupView="lexicon"
							/>
						</a>
					</li>
					<li class="nav-item">
						<a href="#1">
							<span class="icon-instagram"></span>
						</a>
					</li>
					<li class="nav-item">
						<a href="#1">
							<span class="icon-youtube-play"></span>
						</a>
					</li>
				</ul>
			</div>
		</div>

		<div class="autofit-float autofit-row autofit-row-center footer-secondary-section">
			<div class="autofit-col autofit-col-expand footer-section-column">
				<div>2018 &copy; Customer Portal</div>
			</div>

			<div class="autofit-col footer-section-column">
				<a class="footer-link" href="#placeholder">Sitemap</a>
			</div>

			<div class="autofit-col footer-section-column">
				<a class="footer-link" href="#placeholder">Privacy</a>
			</div>

			<div class="autofit-col footer-section-column">
				<a class="footer-link" href="#placeholder">Terms</a>
			</div>
		</div>
	</div>
</footer>