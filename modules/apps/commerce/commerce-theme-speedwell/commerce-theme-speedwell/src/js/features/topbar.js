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

Speedwell.features.topbar = (function(w) {
	'use strict';

	const TOPBAR_CLASS = 'speedwell-topbar',
		TRANSLUCENT_CLASS = TOPBAR_CLASS + '--translucent',
		TOGGLE_PREFIX = '.js-toggle-',
		SPEEDWELL_PREFIX = '.speedwell-',
		IS_OPEN = 'is-open',
		IS_BEHIND = 'is-behind';

	const TOGGLES = {
		ACCOUNT: {name: 'account'},
		MAIN_MENU: {name: 'main-menu'},
		SEARCH: {name: 'search'}
	};

	const CONTAINER = Speedwell.features.context.getContainer();

	let TOPBAR,
		translucencyIsEnabled = false;

	function hideFiltersButtonOnMenuOpen() {
		const catalogFiltersButton = Speedwell.features.mobile.getFiltersButton();

		if (catalogFiltersButton) {
			catalogFiltersButton.classList.toggle(
				IS_BEHIND,
				!isOpen(catalogFiltersButton)
			);
		}
	}

	function attachListener(currentToggle) {
		const toggleWrapper = TOGGLES[currentToggle].wrapper;

		TOGGLES[currentToggle].buttons.forEach(button => {
			button.addEventListener('click', _e => {
				const categoryNav = Speedwell.features.categoryMenu.getElement();

				button.focus();
				toggleWrapper.classList.toggle(IS_OPEN, !isOpen(toggleWrapper));
				categoryNav.classList.remove(IS_OPEN);

				if (Speedwell.features.context.isMobile()) {
					hideFiltersButtonOnMenuOpen();
				}
			});
		});
	}

	function enableToggles() {
		Object.keys(TOGGLES).forEach(attachListener);
	}

	function wipeToggles() {
		Object.keys(TOGGLES).forEach(currentToggle => {
			delete TOGGLES[currentToggle].buttons;
			delete TOGGLES[currentToggle].wrapper;
		});
	}

	function prepareToggles() {
		wipeToggles();

		Object.keys(TOGGLES).forEach(toggle => {
			TOGGLES[toggle].buttons = Array.from(
				TOPBAR.querySelectorAll(TOGGLE_PREFIX + TOGGLES[toggle].name)
			);

			TOGGLES[toggle].wrapper = TOPBAR.querySelector(
				SPEEDWELL_PREFIX + TOGGLES[toggle].name
			);
		});
	}

	function isOpen(toggleElement) {
		return toggleElement.classList.contains(IS_OPEN);
	}

	function toggleTranslucencyOnScroll(scrollThreshold) {
		const isBeyond = w.scrollY <= scrollThreshold;

		if (translucencyIsEnabled) {
			TOPBAR.classList.toggle(TRANSLUCENT_CLASS, isBeyond);
		}
	}

	function isTranslucent() {
		translucencyIsEnabled = TOPBAR.classList.contains(TRANSLUCENT_CLASS);
	}

	function selectElements() {
		TOPBAR = CONTAINER.querySelector('.' + TOPBAR_CLASS);
	}

	return {
		getToggleElements() {
			return TOGGLES;
		},

		initialize() {
			selectElements();
			prepareToggles();
			enableToggles();
			isTranslucent();

			if (translucencyIsEnabled) {
				Speedwell.features.scroll.registerCallback(
					toggleTranslucencyOnScroll
				);
			}
		},

		isOpen
	};
})(window);
