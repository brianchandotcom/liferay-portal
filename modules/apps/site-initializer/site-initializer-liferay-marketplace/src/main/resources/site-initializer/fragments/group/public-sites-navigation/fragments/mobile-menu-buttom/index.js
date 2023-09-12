/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const menuButton = fragmentElement.querySelector('.nav-menu-buttom');
const menuDisplay = ['show-menu', 'hidden-menu'];
const menuIcon = fragmentElement.querySelector('#button-menu-icon');

const buttomEvent = () => {
	const menuDisplayPages = document.querySelector('.nav-menu-pages');

	menuButton.addEventListener('click', () => {
		menuDisplay.forEach((cssClass) => {
			menuDisplayPages.classList.toggle(cssClass);
		});
	});

	menuButton.addEventListener('blur', () => {
		if (menuDisplayPages.classList.contains('show-menu')) {
			menuDisplay.forEach((cssClass) => {
				menuDisplayPages.classList.toggle(cssClass);
			});

			backgroundIconDisplay.forEach((cssClass) => {
				menuIcon.classList.toggle(cssClass);
			});
		}
	});
};

window.addEventListener('load', buttomEvent);
