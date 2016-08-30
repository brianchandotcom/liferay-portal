<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<#if main_search_class != "no-screen">
		<nav id="search" role="navigation">
			<div class="${main_search_class} navbar-form navbar-right" role="search">
				<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone") />

				<@liferay.search default_preferences="${freeMarkerPortletPreferences}" />

				<#assign VOID = freeMarkerPortletPreferences.reset() />
			</div>
		</nav>
	</#if>

	<nav class="nav-header-global row" role="navigation">
		<ul class="nav navbar-nav">
			<#assign
				VOID = freeMarkerPortletPreferences.setValue("displayDepth", "1")
				VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")
			/>

			<@liferay.navigation_menu
				instance_id="header_navigation_menu"
				default_preferences="${freeMarkerPortletPreferences}"
			/>

			<#assign VOID = freeMarkerPortletPreferences.reset() />
		</ul>
	</nav>

	<nav class="navbar-nav site-navigation" id="navigation" role="navigation">
		<#assign
			VOID = freeMarkerPortletPreferences.setValue("displayStyle", "ddmTemplate_NAVBAR-BLANK-JUSTIFIED-FTL")
			VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")
			VOID = freeMarkerPortletPreferences.setValue("rootLayoutType", "relative")
		/>

		<@liferay.navigation_menu
			instance_id="main_navigation_menu"
			default_preferences="${freeMarkerPortletPreferences}"
		/>

		<#assign VOID = freeMarkerPortletPreferences.reset() />
	</nav>
</div>