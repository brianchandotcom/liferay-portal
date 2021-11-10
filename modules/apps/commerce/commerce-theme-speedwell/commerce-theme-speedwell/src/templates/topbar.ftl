<div class="commerce-topbar ${speedwell_topbar_css_class}">
	<div class="speedwell-topbar__menu speedwell-main-menu">
	<#if is_setup_complete || !is_signed_in>
		<button class="speedwell-topbar__button speedwell-main-menu__open js-toggle-main-menu">
			<svg class="lexicon-icon lexicon-icon-bars">
				<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#bars" />
			</svg>
		</button>

		<div class="speedwell-main-menu__link-wrapper">
			<button class="speedwell-topbar__button js-toggle-main-menu">
				<svg class="lexicon-icon lexicon-icon-times">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#times" />
				</svg>
			</button>

			<div class="speedwell-main-menu__links">
				<@site_navigation_menu_main default_preferences=freeMarkerPortletPreferences
					.getPreferences("portletSetupPortletDecoratorId", "barebone") />

				<#include "${full_templates_path}/category_navigation.ftl" />
			</div>
		</div>
	</#if>
	</div>

	<div class="speedwell-topbar__logo">
		<div class="speedwell-logo">
			<a
				class="${logo_css_class}" href="${site_default_url}"
			   title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
				<img alt="${site_name}" class="logo" src="${site_logo}" />
			</a>
		</div>
	</div>

	<div class="speedwell-topbar__actions">
	<#if is_setup_complete || !is_signed_in>
		<div class="speedwell-topbar__search speedwell-search">
			<div class="speedwell-topbar__button js-toggle-search">
				<svg class="lexicon-icon lexicon-icon-search">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#search" />
				</svg>
			</div>

			<div class="speedwell-search__bar-wrapper">
				<div class="speedwell-search__bar">
					<button class="speedwell-topbar__button" disabled>
						<svg class="lexicon-icon lexicon-icon-search">
							<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#search" />
						</svg>
					</button>
					<@liferay_commerce_ui["search-bar"] id="search-bar" />

					<button class="speedwell-topbar__button js-toggle-search">
						<svg class="lexicon-icon lexicon-icon-times">
							<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#times" />
						</svg>
					</button>
				</div>
			</div>

			<div class="speedwell-search__results">
				<@liferay_commerce_ui["search-results"] />
			</div>
		</div>

		<div class="speedwell-account">
			<button class="speedwell-topbar__button js-toggle-account">
				<svg class="lexicon-icon lexicon-icon-user">
					<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#user" />
				</svg>
			</button>

			<div class="speedwell-account__dropdown">
				<div class="speedwell-account__content">
					<#include "${full_templates_path}/user_nav.ftl" />
				</div>
			</div>
		</div>

		<#if show_mini_cart>
			<div class="speedwell-topbar__cart-wrapper speedwell-cart">
				<@liferay_commerce_ui["mini-cart"]
					displayTotalItemsQuantity=mini_cart_display_total_items_count
					spritemap="${themeDisplay.getPathThemeImages()}/clay/icons.svg"
					toggleable=!mini_cart_keep_open
				/>
			</div>
		</#if>
	</#if>
	</div>
</div>