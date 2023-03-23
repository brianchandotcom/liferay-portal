/* eslint-disable no-undef */
/* eslint-disable no-return-assign */
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
const btnMyaccount = fragmentElement.querySelector('#dropdown-item-myaccount');
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
btnMyaccount.onclick = () => handleCustomizebleUrl(btnMyaccount.href);
btnNotifications.onclick = () => redirectUrl('notifications-list');
btnAccountsettings.onclick = () => redirectUrl('account-settings');
btnSignout.onclick = () => handleCustomizebleUrl(btnSignout.href);
btnDropdown.onclick = () => btnDropdown.classList.toggle('show');
