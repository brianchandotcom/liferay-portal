/* eslint-disable no-undef */
/* eslint-disable no-return-assign */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const redirectUrl = (routeName) => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const urlPaths = pathname.split('/').filter(Boolean);
	const siteName = `/${urlPaths
		.slice(0, urlPaths.length > 2 ? urlPaths.length - 1 : urlPaths.length)
		.join('/')}`;
	window.location.href = `${origin}${siteName}/${routeName}`;
};

const handleCustomizebleUrl = (link) => {
	window.location.href = link;
};

const userName = Liferay.ThemeDisplay.getUserName();
fragmentElement.querySelector('.user-name').innerHTML = userName;

const avatarElement = fragmentElement.querySelector(
	'.applications-menu-header span'
);
const left = avatarElement.offsetLeft;
const height = avatarElement.offsetHeight;
const dropdownContent = fragmentElement.querySelector('.dropdown-content');
dropdownContent.style.left = `${left + 10}px`;
dropdownContent.style.bottom = `${height}px`;

const btnDashboard = fragmentElement.querySelector('#dropdown-item-dashboard');
const btnNotifications = fragmentElement.querySelector(
	'#dropdown-item-notifications'
);
const btnAccountsettings = fragmentElement.querySelector(
	'#dropdown-item-accountsettings'
);
const btnSignout = fragmentElement.querySelector('#dropdown-item-signout');
const btnDropdown = fragmentElement.querySelector(
	'.dropdown.applications-menu-wrapper'
);

btnDashboard.onclick = () => redirectUrl('dashboard');
btnNotifications.onclick = () => redirectUrl('notifications-list');
btnAccountsettings.onclick = () => redirectUrl('account-settings');
btnSignout.onclick = () => handleCustomizebleUrl(btnSignout.href);
btnDropdown.onclick = () => btnDropdown.classList.toggle('show');
