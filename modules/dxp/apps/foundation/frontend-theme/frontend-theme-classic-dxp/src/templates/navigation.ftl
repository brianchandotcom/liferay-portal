<div aria-expanded="false" class="collapse navbar-collapse" id="navigationCollapse">
	<#if has_navigation && is_setup_complete>
		<nav class="${nav_css_class} site-navigation" id="navigation" role="navigation">
			<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

			<#if show_header_search>
				<div class="navbar-form navbar-right" role="search">
					<@liferay.search default_preferences="${preferences}" />
				</div>
			</#if>

			<div class="navbar-right">
				<@liferay.navigation_menu default_preferences="${preferences}" />
			</div>
		</nav>
	</#if>
</div>