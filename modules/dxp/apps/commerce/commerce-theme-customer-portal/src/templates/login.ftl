<div class="b2b-login-container">
	<header class="autofit-row b2b-login-header">
		<div class="autofit-col b2b-login-header-logo">Logo?</div>
		<div class="autofit-col autofit-col-expand b2b-login-header-title">
			<div class="autofit-section">
				Welcome, <strong>${company_name}</strong>
			</div>
		</div>
	</header>

	<div class="b2b-login-body">
		<div class="b2b-login-bg">
			<img alt="Elegant Man tying shoes" class="b2b-login-bg-image" src="${images_folder}/placeholder-elegant-man.jpeg">
			<div class="b2b-login-overlay b2b-overlay-scanline"></div>
		</div>

		<div class="b2b-login-card card">
			<#if is_signed_in && !validator.isNotNull(currentOrganization)>
				<@commerce_search_organization default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
			<#else>
				<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

				<@liferay_portlet["runtime"]
					defaultPreferences=preferences
					portletName="com_liferay_login_web_portlet_LoginPortlet"
				/>
			</#if>
		</div>
	</div>
</div>