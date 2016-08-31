<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="navbar-nav site-navigation" id="navigation" role="navigation">
		<#assign
			VOID = freeMarkerPortletPreferences.setValue("displayDepth", "1")
			VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")
		/>

		<@liferay.navigation_menu
			instance_id="main_navigation_menu"
			default_preferences="${freeMarkerPortletPreferences}"
		/>

		<#assign VOID = freeMarkerPortletPreferences.reset() />

		<div class="user-area">
		</div>
	</nav>

	<#if show_header_search>
		<div class="navbar-form navbar-right" role="search">
			<div id="search">
				<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone") />

				<@liferay.search default_preferences="${freeMarkerPortletPreferences}" />

				<#assign VOID = freeMarkerPortletPreferences.reset() />
			</div>

			<button aria-controls="navigation" class="btn-link btn-search hidden-xs" type="button">
				<svg class="lexicon-icon">
					<use xlink:href="${images_folder}/lexicon/icons.svg#search" />
				</svg>

				<svg class="lexicon-icon">
					<use xlink:href="${images_folder}/lexicon/icons.svg#times" />
				</svg>
			</button>
		</div>
	</#if>

	<div class="nav navbar-right navbar-user">
		<@liferay.user_personal_bar />
	</div>
</div>