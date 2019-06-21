<div class="mx-1 mx-sm-3 order-md-1 porygon-user">
	<@liferay.user_personal_bar />
</div>

<#if show_header_main_search>
	<div class="align-items-md-end align-items-start d-flex order-md-1 porygon-search" role="search">
		<div class="porygon-search-form w-100">
			<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
		</div>

		<button aria-controls="navigation" class="btn btn-monospaced flex-shrink-0 porygon-search-button text-white" type="button">
			<svg class="lexicon-icon">
				<use xlink:href="${images_folder}/lexicon/icons.svg#search" />
			</svg>

			<svg class="lexicon-icon">
				<use xlink:href="${images_folder}/lexicon/icons.svg#times" />
			</svg>
		</button>
	</div>
</#if>

<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="navbar-nav site-navigation" id="navigation" role="navigation">
		<#assign preferencesMap = {"displayDepth": "1", "portletSetupPortletDecoratorId": "barebone"} />

		<@liferay.navigation_menu
			default_preferences=freeMarkerPortletPreferences.getPreferences(preferencesMap)
			instance_id="main_navigation_menu"
		/>
	</nav>
</div>