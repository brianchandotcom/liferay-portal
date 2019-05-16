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
			<#assign mainNavigationPreferencesMap = {"displayDepth": "1", "portletSetupPortletDecoratorId": "barebone"} />

			<@liferay.navigation_menu
				default_preferences=freeMarkerPortletPreferences.getPreferences(mainNavigationPreferencesMap)
				instance_id="header_navigation_menu"
			/>
		</ul>
	</nav>

	<nav class="navbar-nav site-navigation" id="navigation" role="navigation">
		<#assign secondaryNavigationPreferencesMap = {"displayStyle": "ddmTemplate_NAVBAR-BLANK-JUSTIFIED-FTL", "portletSetupPortletDecoratorId": "barebone", "rootMenuItemType": "relative"} />

		<@liferay.navigation_menu
			default_preferences=freeMarkerPortletPreferences.getPreferences(secondaryNavigationPreferencesMap)
			instance_id="main_navigation_menu"
		/>
	</nav>
</div>