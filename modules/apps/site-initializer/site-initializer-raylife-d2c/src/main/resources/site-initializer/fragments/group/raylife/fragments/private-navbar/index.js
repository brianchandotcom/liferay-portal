/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const dropdownButton = fragmentElement.querySelector('.dropdown-toggle');
const dropdownMenu = fragmentElement.querySelector('.dropdown-menu');

dropdownButton.addEventListener('click', () => {
	dropdownMenu.classList.toggle('show');

	dropdownButton.setAttribute(
		'aria-expanded',
		dropdownMenu.classList.contains('show').toString()
	);
});
