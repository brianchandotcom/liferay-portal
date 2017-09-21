<ul class="nav navbar-nav navbar-right navbar-recursive">
	<#if show_wish_list_icon>
		<li class="collapse-hover" id="wishListIcon">
			<a class="btn hidden-xs animate" href="/wishlist">
				<#if wishListItemsCount != 0>
					<svg class="lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-wish-list-full" />
					</svg>

					<span class="sticker sticker-outside">${wishListItemsCount}</span>
				<#else>
					<svg class="lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-wish-list" />
					</svg>
				</#if>
			</a>

			<div class="collapse dropdown-menu">
				<div class="small">
					<#include "${full_templates_path}/wish_list.ftl" />
				</div>
			</div>
		</li>
	</#if>

	<#if show_cart_icon>
		<li class="collapse-hover" id="cartIcon">
			<a class="btn hidden-xs animate" href="/cart">
				<#if cartItemsCount != 0>
					<svg class="lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-bag-full" />
					</svg>

					<span class="sticker sticker-outside">${cartItemsCount}</span>
				<#else>
					<svg class="lexicon-icon">
						<use xlink:href="${images_folder}/theme-icons.svg#icon-bag" />
					</svg>
				</#if>
			</a>

			<div class="collapse dropdown-menu">
				<div class="small">
					<#include "${full_templates_path}/cart.ftl" />
				</div>
			</div>
		</li>
	</#if>

	<li>
		<#if is_signed_in>
			<@liferay.user_personal_bar />
		<#else>
			<a href="${sign_in_url}" class="btn sign-in text-default" data-redirect="false">
				<svg class="lexicon-icon">
					<use xlink:href="${images_folder}/theme-icons.svg#icon-user" />
				</svg>
			</a>
		</#if>
	</li>
</ul>