/* eslint-disable no-return-assign */
/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const checkbox = fragmentElement.querySelector('#togBtn');
const sliderBefore = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider'
);
const sliderOn = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider .on'
);
const sliderOff = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider .off'
);

function changeBeforeText(checked) {
	if (checked) {
		return sliderBefore.setAttribute(
			'data-content',
			sliderOn.firstChild.nodeValue
		);
	}
	sliderBefore.setAttribute('data-content', sliderOff.firstChild.nodeValue);
}

changeBeforeText(checkbox.checked);

checkbox.addEventListener('click', (event) => {
	changeBeforeText(event.target.checked);
});

const backgroundIconDisplay = ['background-icon-close', 'background-icon-grid'];
const menuButton = fragmentElement.querySelector('.raylife-navbar-button');
const menuDisplay = ['show-menu', 'hidden-menu'];
const menuIcon = fragmentElement.querySelector('#button-menu-icon');
const myDropdown = fragmentElement.querySelector('#myDropdown');

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
