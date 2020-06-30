/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

var Speedwell = Speedwell || {features: {}};

Speedwell.features.categoryMenu = (function(_w) {
	'use strict';

	const MAIN_LINK_SELECTOR = '.main-link',
		CATEGORY_NAV_SELECTOR = '.speedwell-category-nav',
		IS_OPEN = 'is-open';

	let linkElements, categoryNavigationElement;

	const CONTAINER = Speedwell.features.context.getContainer();

	function showCategoryNavigationMenu(e) {
		const isCatalogLink =
			e.currentTarget.href.indexOf('/car-parts') > -1 ||
			e.currentTarget.href.indexOf('/catalog') > -1;

		if (isCatalogLink) {
			categoryNavigationElement.focus();
			categoryNavigationElement.classList.add(IS_OPEN);
		} else {
			categoryNavigationElement.classList.remove(IS_OPEN);
		}
	}

	function hideCategoryNavigationMenu() {
		categoryNavigationElement.classList.remove(IS_OPEN);
	}

	function attachListeners() {
		if (!Speedwell.features.context.isMobile()) {
			linkElements.forEach(link => {
				link.addEventListener('mouseover', showCategoryNavigationMenu);
			});

			categoryNavigationElement.addEventListener(
				'focusout',
				hideCategoryNavigationMenu
			);
		}
	}

	function selectElements() {
		linkElements = Array.from(
			CONTAINER.querySelectorAll(MAIN_LINK_SELECTOR)
		);

		categoryNavigationElement = CONTAINER.querySelector(
			CATEGORY_NAV_SELECTOR
		);
	}

	return {
		getElement() {
			return categoryNavigationElement;
		},
		initialize() {
			selectElements();
			attachListeners();
		}
	};
})(window);
