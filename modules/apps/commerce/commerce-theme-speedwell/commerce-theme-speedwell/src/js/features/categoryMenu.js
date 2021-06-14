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

Liferay.component(
	'SpeedwellCategoryMenu',
	(function () {
		var MAIN_LINK_SELECTOR = '.main-link';
		var CATEGORY_NAV_SELECTOR = '.speedwell-category-nav';
		var IS_OPEN = 'is-open';
		var linkElements;
		var categoryNavigationElement;

		const CONTAINER = document.getElementById('speedwell');

		function showCategoryNavigationMenu(event) {
			const isCatalogLink =
				event.currentTarget.href.indexOf('/car-parts') > -1 ||
				event.currentTarget.href.indexOf('/catalog') > -1;

			if (isCatalogLink) {
				categoryNavigationElement.focus();
				categoryNavigationElement.classList.add(IS_OPEN);
			}
			else {
				categoryNavigationElement.classList.remove(IS_OPEN);
			}
		}

		function hideCategoryNavigationMenu() {
			categoryNavigationElement.classList.remove(IS_OPEN);
		}

		function attachListeners() {
			if (!Liferay.Browser.isMobile()) {
				linkElements.forEach((link) => {
					link.addEventListener(
						'mouseover',
						showCategoryNavigationMenu
					);
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

		selectElements();
		attachListeners();

		return {
			getElement() {
				return categoryNavigationElement;
			},
		};
	})(),
	{destroyOnNavigate: true}
);
