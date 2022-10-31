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

const menuButtonGroup = fragmentElement.querySelector('.menu-button-group');

const tabletMobileNavSection = fragmentElement.querySelector(
	'.tablet-mobile-nav-section'
);

const menuBtn = fragmentElement.querySelector('.menu-btn');

const closeBtn = fragmentElement.querySelector('.close-btn');

const accountMenus = fragmentElement.querySelectorAll('.account');

const sites = fragmentElement.querySelector('.sites');

menuBtn.addEventListener('click', () => {
	menuButtonGroup.classList.toggle('menu-open');
	tabletMobileNavSection.classList.toggle('menu-open');
});

closeBtn.addEventListener('click', () => {
	menuButtonGroup.classList.toggle('menu-open');
	tabletMobileNavSection.classList.toggle('menu-open');
});

accountMenus.forEach((accountMenu) => {
	accountMenu.addEventListener('click', () => {
		accountMenu.classList.toggle('menu-open');
	});
});

sites.addEventListener('click', () => {
	sites.classList.toggle('show');
});
