/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const body = document.querySelector('body');

const navbarButton = fragmentElement.querySelector('.navbar-toggler');
const navbarCollapse = fragmentElement.querySelector('.navbar-collapse');
const siteNavbar = fragmentElement.querySelector('.main-navbar');

navbarButton.addEventListener('click', () => {
	body.classList.toggle('overflow-hidden');
	navbarCollapse.classList.toggle('show');
	siteNavbar.classList.toggle('open');

	navbarButton.setAttribute(
		'aria-expanded',
		navbarCollapse.classList.contains('show').toString()
	);
});
