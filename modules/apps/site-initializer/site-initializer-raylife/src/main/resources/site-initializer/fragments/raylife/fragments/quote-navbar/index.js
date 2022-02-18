/* eslint-disable no-return-assign */
/* eslint-disable no-undef */
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
