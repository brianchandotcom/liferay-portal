<div aria-expanded="true" class="collapse navbar-collapse" id="navigationCollapse">
	<nav class="dropdown-col-md-4 dropdown-full dropdown-wide mr-auto navbar-nav site-navigation" id="navigation" role="navigation">
		<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</nav>

	<ul class="nav navbar-nav navbar-recursive navbar-right">
		<svg class="commerce-icon lexicon-icon lexicon-icon-search">
			<use xlink:href="${images_folder}/theme-icons.svg#icon-search" />
		</svg>

		<#if show_cart_icon>
			<li class="collapse-hover nav-item" id="cartIcon">
				<a class="nav-link" href="${cartUrl}">
					<#if orderItemsQuantity != 0>
						<span class="sticker sticker-outside">${orderItemsQuantity}</span>

						<svg class="commerce-icon icon-bag-full lexicon-icon">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
						</svg>
					<#else>
						<svg class="commerce-icon icon-bag lexicon-icon">
							<use xlink:href="${images_folder}/theme-icons.svg#icon-bag" />
						</svg>

						<svg class="commerce-icon icon-bag-full lexicon-icon" style="display: none">
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
	</ul>
</div>