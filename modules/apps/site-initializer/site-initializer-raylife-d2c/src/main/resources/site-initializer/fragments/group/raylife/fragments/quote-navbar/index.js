/* eslint-disable no-return-assign */
/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const menuButton = fragmentElement.querySelector('.raylife-navbar-button');
const myDropdown = fragmentElement.querySelector('#myDropdown');
const menuIcon = fragmentElement.querySelector('#button-menu-icon');

const backgroundIconDisplay = ['background-icon-close', 'background-icon-grid'];
const menuDisplay = ['show-menu', 'hidden-menu'];

const pages = [
	'congrats',
	'hang-tight',
	'quote-comparison',
	'selected-quote',
	'get-in-touch',
];
const logoElement = document.querySelector('.logo-desktop');
const pathName = window.location.pathname;
const menuElements = document.querySelectorAll('.raylife-quote-menu');

pages.forEach((page) => {
	if (pathName.includes(page)) {
		menuElements.forEach((menuElement) =>
			menuElement.classList.add('menu-button-style')
		);
		logoElement.classList.add('white-background');
	}
});

menuButton.addEventListener('click', () => {
	menuDisplay.forEach((cssClass) => {
		myDropdown.classList.toggle(cssClass);
	});

	backgroundIconDisplay.forEach((cssClass) => {
		menuIcon.classList.toggle(cssClass);
	});

	if (myDropdown.classList.contains('show-menu')) {
		return (fragmentElement.querySelector(
			'.raylife-navbar-button div span'
		).innerText = 'CLOSE');
	}

	fragmentElement.querySelector('.raylife-navbar-button div span').innerText =
		'MENU';
});

menuButton.addEventListener('blur', () => {
	if (myDropdown.classList.contains('show-menu')) {
		menuDisplay.forEach((cssClass) => {
			myDropdown.classList.toggle(cssClass);
		});

		backgroundIconDisplay.forEach((cssClass) => {
			menuIcon.classList.toggle(cssClass);
		});

		if (myDropdown.classList.contains('show-menu')) {
			return (fragmentElement.querySelector(
				'.raylife-navbar-button div span'
			).innerText = 'CLOSE');
		}

		fragmentElement.querySelector(
			'.raylife-navbar-button div span'
		).innerText = 'MENU';
	}
});
