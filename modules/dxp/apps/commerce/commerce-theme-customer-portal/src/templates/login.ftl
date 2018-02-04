<div class="container">
	<div class="col-md-4 col-md-offset-1">
		<h2 class="text-center">SIGN IN WITH YOUR EMAIL</h2>
		<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

		<@liferay_portlet["runtime"]
			defaultPreferences=preferences
			portletName="com_liferay_login_web_portlet_LoginPortlet"
		/>
	</div>
</div>