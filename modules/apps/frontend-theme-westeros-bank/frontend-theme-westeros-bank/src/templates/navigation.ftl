<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<#if !stringUtil.equals(main_search_class, "no-screen")>
		<nav id="search" role="navigation">
			<div class="${main_search_class} navbar-form navbar-right" role="search">
				<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
			</div>
		</nav>
	</#if>

	<nav class="nav-header-global row" role="navigation">
		<ul class="nav navbar-nav">
			<#assign preferencesMap = {"displayDepth": "1", "portletSetupPortletDecoratorId": "barebone"} />

			<@liferay.navigation_menu
				default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
				instance_id="header_navigation_menu"
			/>
		</ul>
	</nav>

	<nav class="navbar-nav site-navigation" id="navigation" role="navigation">
		<#assign preferencesMap = {"displayStyle": "ddmTemplate_NAVBAR-BLANK-JUSTIFIED-FTL", "portletSetupPortletDecoratorId": "barebone", "rootLayoutType": "relative"} />

		<@liferay.navigation_menu
			default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
			instance_id="main_navigation_menu"
		/>
	</nav>
</div>