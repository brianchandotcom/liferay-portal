<div class="commerce-topbar minium-topbar">
	<div class="minium-topbar__start">
		<#if back_url?has_content>
			<a class="commerce-topbar-button" href="${htmlUtil.escape(back_url)}">
				<svg class="commerce-icon commerce-topbar-button__icon lexicon-icon lexicon-icon-arrow-back">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#arrow-back" />
				</svg>

				<span class="commerce-topbar-button__label">
					${languageUtil.get(locale, "back")}
				</span>
			</a>
		</#if>
		<#if show_search_bar>
			<label class="commerce-topbar-button js-toggle-search" for="commerce-search-input">
				<svg class="commerce-icon commerce-topbar-button__icon commerce-topbar-button__icon--not-active lexicon-icon lexicon-icon-search">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#search" />
				</svg>

				<svg class="commerce-icon commerce-topbar-button__icon commerce-topbar-button__icon--active lexicon-icon lexicon-icon-times">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#times" />
				</svg>
			</label>
		</#if>
	</div>

	<div class="minium-topbar__middle">
		<#if show_top_menu>
			<@site_navigation_menu_sub_navigation default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
		</#if>
	</div>

	<#if show_account_selector || show_mini_cart>
		<div class="minium-topbar__end">
			<#if show_account_selector>
				<div class="minium-topbar__account-selector-wrapper">
					<@liferay_commerce_ui["account-selector"] />
				</div>
			</#if>

			<#if show_mini_cart>
				<div class="minium-topbar__cart-wrapper">
					<@liferay_commerce_ui["mini-cart"]
						displayTotalItemsQuantity=mini_cart_display_total_items_count
						spritemap="${themeDisplay.getPathThemeImages()}/clay/icons.svg"
						toggleable=!mini_cart_keep_open
					/>
				</div>
			</#if>
		</div>
	</#if>

	<#if show_search_bar>
		<div class="minium-topbar__search">
			<@liferay_commerce_ui["search-bar"] id="search-bar" />
		</div>
	</#if>
</div>