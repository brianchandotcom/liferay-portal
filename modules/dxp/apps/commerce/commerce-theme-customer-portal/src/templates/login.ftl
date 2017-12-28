<div class="container">
	<div class="col-md-4 col-md-offset-1">
		<h2 class="text-center">SIGN IN WITH YOUR EMAIL</h2>
		<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

		<@liferay_portlet["runtime"]
			defaultPreferences=preferences
			portletName="com_liferay_login_web_portlet_LoginPortlet"
		/>
	</div>

	<div class="col-md-4 col-md-offset-2">
		<h2 class="text-center">CREATE AN ACCOUNT</h2>
		<p class="text-center">Creating an account is easy. just click the create an account button below, fill out the form, and enjoy the benefits.</p>
		<a href="home?p_p_id=com_liferay_login_web_portlet_LoginPortlet&amp;p_p_lifecycle=0&amp;p_p_state=maximized&amp;p_p_mode=view&amp;saveLastPath=false&amp;_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName=%2Flogin%2Fcreate_account" target="_self" class="btn btn-lg btn-primary">Create Account</a>
	</div>
</div>