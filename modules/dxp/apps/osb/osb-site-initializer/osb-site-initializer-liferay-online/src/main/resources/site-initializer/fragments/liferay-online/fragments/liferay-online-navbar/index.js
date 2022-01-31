/* eslint-disable no-undef */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const body = document.querySelector('body');

const navbarButton = fragmentElement.querySelector('.navbar-toggler');
const navbarCollapse = fragmentElement.querySelector('.navbar-collapse');
const siteNavbar = fragmentElement.querySelector('.lol-navbar');

navbarButton.addEventListener('click', () => {
	body.classList.toggle('overflow-hidden');
	navbarCollapse.classList.toggle('show');
	siteNavbar.classList.toggle('open');

	navbarButton.setAttribute(
		'aria-expanded',
		navbarCollapse.classList.contains('show').toString()
	);
});
