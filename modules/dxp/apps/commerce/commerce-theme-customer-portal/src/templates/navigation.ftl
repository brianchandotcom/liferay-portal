<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="dropdown-col-md-4 dropdown-full dropdown-wide navbar-nav mr-auto site-navigation" id="navigation" role="navigation">
		<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</nav>

	<ul class="nav navbar-nav navbar-right navbar-recursive">
		<svg class="commerce-icon lexicon-icon lexicon-icon-search">
			<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
		</svg>

		<span class="sticker sticker-outside">${cartItemsCount}</span>
	</ul>
</div>