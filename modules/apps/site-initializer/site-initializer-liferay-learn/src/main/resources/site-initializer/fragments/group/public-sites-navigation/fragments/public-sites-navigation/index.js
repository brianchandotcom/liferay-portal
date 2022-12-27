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

/* eslint-disable no-undef */

adtSpatialNavigationProvider = new navigation.default.SpatialNavigationProvider(
	'.adt-submenu-item-link'
);

spatialNavigationProvider = new navigation.default.SpatialNavigationProvider(
	'.adt-nav-text'
);

const primaryNav = fragmentElement.querySelector('.primary-nav');

spatialNavigationProvider.addFocusableClasses(primaryNav);

window.addEventListener('load', () => {
	new navigation.default.DropdownProvider(
		'.account',
		'.account',
		'menu-open'
	);

	new navigation.default.DropdownProvider(
		'.sites',
		'.liferay-sites-dropdown',
		'show',
		true
	);

	new navigation.default.DropdownProvider(
		'.menu-button-group',
		'.menu-button-group',
		'menu-open'
	);

	new navigation.default.DropdownProvider(
		'.menu-button-group',
		'.tablet-mobile-nav-section',
		'menu-open',
		true
	);

	new navigation.default.DropdownProvider(
		'.adt-nav-text',
		'.adt-submenu',
		'dropdown-open',
		false,
		(menu) => {
			adtSpatialNavigationProvider.addFocusableClasses(menu);
		},
		(menu) => {
			adtSpatialNavigationProvider.removeFocusableClasses(menu);
		}
	);

	new navigation.default.DropdownProvider(
		'.language',
		'.language-selector',
		'list-open',
		true
	);

	new navigation.default.DropdownProvider(
		'.language',
		'.language-dropdown-list-container',
		'list-open',
		true
	);
});
