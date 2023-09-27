/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const loginButton = fragmentElement.querySelector('.btn-login');
const url = themeDisplay.getPortalURL();

if (themeDisplay.getUserEmailAddress() !== '') {
	loginButton.innerHTML = 'Sign out';
	loginButton.href = `${url}/c/portal/logout`;
}
