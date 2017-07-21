<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="dropdown-col-md-4 dropdown-full dropdown-wide navbar-nav site-navigation" id="navigation" role="navigation">

		<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />

	</nav>

	<ul class="nav navbar-nav navbar-right navbar-recursive" role="search">
		<li class="hidden-xs">
			<a class="btn btn-xs collapsed" role="button" data-toggle="collapse" href="#collapseSearch" aria-expanded="false" aria-controls="collapseSearch">
				<svg class="lexicon-icon lexicon-icon-search">
					<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
				</svg>
				<svg class="lexicon-icon lexicon-icon-times">
					<use xlink:href="${images_folder}/lexicon/icons.svg#times" />
				</svg>
			</a>
		</li>
		<li class="hidden-xs">
			<a class="btn btn-xs collapsed" role="button" data-toggle="collapse" href="#collapseFavorites" aria-expanded="false" aria-controls="collapseFavorites">
				<svg class="lexicon-icon">
					<use xlink:href="${images_folder}/theme-icons.svg#icon-whislist" />
				</svg>
			</a>
		</li>
		<li class="hidden-xs">
			<a class="btn collapsed" role="button" data-toggle="collapse" href="#collapseCart" aria-expanded="false" aria-controls="collapseCart">
				<svg class="lexicon-icon">
					<use xlink:href="${images_folder}/theme-icons.svg#icon-bag" />
				</svg>
			</a>
		</li>
		<li>
			<#if is_signed_in>
				<@liferay.user_personal_bar />
			<#else>
				<a class="btn collapsed" role="button" data-toggle="collapse" href="#collapseLogin" aria-expanded="false" aria-controls="collapseLogin">
					<svg class="lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-user" />
					</svg>
				</a>
			</#if>
		</li>
	</ul>

	<div class="collapse position-anchored" id="collapseSearch">
		<div class="well small">
			<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
		</div>
	</div>

	<div class="collapse position-anchored" id="collapseFavorites">
		<div class="well small">
			<#include "${full_templates_path}/favorites.ftl" />
		</div>
	</div>

	<div class="collapse position-anchored" id="collapseCart">
		<div class="well small">
			<#include "${full_templates_path}/cart.ftl" />
		</div>
	</div>
</div>