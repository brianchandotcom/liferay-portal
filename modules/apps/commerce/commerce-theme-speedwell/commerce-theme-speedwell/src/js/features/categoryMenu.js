/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay.component(
	'SpeedwellCategoryMenu',
	(function () {
		const MAIN_LINK_SELECTOR = '.main-link';
		const CATEGORY_NAV_SELECTOR = '.speedwell-category-nav';
		const IS_OPEN = 'is-open';
		let linkElements;
		let categoryNavigationElement;

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
