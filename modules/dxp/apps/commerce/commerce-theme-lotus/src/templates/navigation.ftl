<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="dropdown-col-md-4 dropdown-full dropdown-wide navbar-nav site-navigation" id="navigation" role="navigation">
		<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</nav>

	<ul class="nav navbar-nav navbar-right navbar-recursive">
		<#if show_main_search_icon>
			<li id="mainSearchIcon">
				<a class="btn btn-xs collapsed hidden-xs" role="button" data-toggle="collapse" href="#collapseSearch" aria-expanded="false" aria-controls="collapseSearch">
					<svg class="lexicon-icon lexicon-icon-search">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
					</svg>

					<svg class="lexicon-icon lexicon-icon-close">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-close" />
					</svg>
				</a>

				<div class="collapse position-anchored position-anchored-left" id="collapseSearch">
					<div class="well small">
						<svg class="lexicon-icon lexicon-icon-search-static">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
						</svg>

						<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
					</div>
				</div>
			</li>
		</#if>

		<#if show_wish_list_icon>
			<li class="collapse-hover" id="wishListIcon">
				<a class="btn hidden-xs animate" href="${wishListUrl}">
					<#if wishListItemsCount != 0>
						<svg class="lexicon-icon icon-heart-full">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-wish-list-full" />
						</svg>

						<span class="sticker sticker-outside">${wishListItemsCount}</span>
					<#else>
						<svg class="lexicon-icon icon-heart">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-wish-list" />
						</svg>

						<svg class="lexicon-icon icon-heart-full" style="display: none">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-wish-list-full" />
						</svg>
					</#if>
				</a>

				<div class="collapse position-anchored">
					<div class="well small">
						<#include "${full_templates_path}/wish_list.ftl" />
					</div>
				</div>
			</li>
		</#if>

		<#if show_cart_icon>
			<li class="collapse-hover" id="cartIcon">
				<a class="btn hidden-xs animate" href="${cartUrl}">
					<#if cartItemsCount != 0>
						<span class="sticker sticker-outside">${cartItemsCount}</span>

						<svg class="lexicon-icon icon-bag-full">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
						</svg>
					<#else>
						<svg class="lexicon-icon icon-bag">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag" />
						</svg>

						<svg class="lexicon-icon icon-bag-full" style="display: none">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
						</svg>
					</#if>
				</a>

				<div class="collapse position-anchored">
					<div class="well small">
						<#include "${full_templates_path}/cart.ftl" />
					</div>
				</div>
			</li>
		</#if>

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
</div>