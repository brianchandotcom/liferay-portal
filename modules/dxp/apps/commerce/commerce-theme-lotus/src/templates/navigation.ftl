<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="dropdown-col-md-4 dropdown-full dropdown-wide navbar-nav mr-auto site-navigation" id="navigation" role="navigation">
		<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</nav>

	<ul class="nav navbar-nav navbar-right navbar-recursive">
		<#if show_main_search_icon>
			<li class="nav-item" id="mainSearchIcon">
				<a class="nav-link collapsed" role="button" data-toggle="collapse" href="#collapseSearch" aria-expanded="false" aria-controls="collapseSearch">
					<svg class="commerce-icon lexicon-icon lexicon-icon-search">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
					</svg>

					<svg class="commerce-icon lexicon-icon lexicon-icon-close">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-close" />
					</svg>
				</a>

				<div class="position-anchored position-anchored-left">
					<div class="collapse" id="collapseSearch">
						<div class="card card-horizontal">
							<svg class="commerce-icon lexicon-icon lexicon-icon-search-static">
								<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
							</svg>

							<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
						</div>
					</div>
				</div>
			</li>
		</#if>

		<#if show_wishlist_icon>
			<li class="collapse-hover nav-item" id="wishlistIcon">
				<a class="animate nav-link" href="${wishlistUrl}">
					<#if wishListItemsCount != 0>
						<svg class="commerce-icon lexicon-icon icon-heart-full">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-whislist-full" />
						</svg>

						<span class="sticker sticker-outside">${wishListItemsCount}</span>
					<#else>
						<svg class="commerce-icon lexicon-icon icon-heart">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-whislist" />
						</svg>

						<svg class="commerce-icon lexicon-icon icon-heart-full" style="display: none">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-whislist-full" />
						</svg>
					</#if>
				</a>

				<div class="collapse position-anchored">
					<div class="card card-horizontal small">
						<#include "${full_templates_path}/wishlist.ftl" />
					</div>
				</div>
			</li>
		</#if>

		<#if show_cart_icon>
			<li class="collapse-hover nav-item" id="cartIcon">
				<a class="animate nav-link" href="${cartUrl}">
					<#if orderItemsCount != 0>
						<span class="sticker sticker-outside">${orderItemsCount}</span>

						<svg class="commerce-icon lexicon-icon icon-bag-full">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
						</svg>
					<#else>
						<svg class="commerce-icon lexicon-icon icon-bag">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag" />
						</svg>

						<svg class="commerce-icon lexicon-icon icon-bag-full" style="display: none">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
						</svg>
					</#if>
				</a>

				<div class="collapse position-anchored">
					<div class="card card-horizontal small">
						<#include "${full_templates_path}/cart.ftl" />
					</div>
				</div>
			</li>
		</#if>

		<li class="nav-item">
			<#if is_signed_in>
				<a class="nav-link" role="button" href="${accountUrl}">
					<svg class="commerce-icon lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-user" />
					</svg>
				</a>
			<#else>
				<a class="collapsed nav-link" role="button" data-toggle="collapse" href="#collapseLogin" aria-expanded="false" aria-controls="collapseLogin">
					<svg class="commerce-icon lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-user" />
					</svg>
				</a>
			</#if>
		</li>
	</ul>
</div>