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

const avatarElement = document.querySelector('.applications-menu-header span');
const left = avatarElement.offsetLeft;
const height = avatarElement.offsetHeight;

const dropdown = document.querySelector('.dropdown.applications-menu-wrapper');
dropdown.onclick = () => dropdown.classList.toggle('show');

const userId = Liferay.ThemeDisplay.getUserName();
document.querySelector('.user-name').innerHTML = userId;

const dropdownContent = document.querySelector('.dropdown-content');
dropdownContent.style.left = `${left + 10}px`;
dropdownContent.style.bottom = `${height + 35}px`;

const redirectUrl = (routeName) => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const urlPaths = pathname.split('/').filter(Boolean);
	const siteName = `/${urlPaths
		.slice(0, urlPaths.length > 2 ? urlPaths.length - 1 : urlPaths.length)
		.join('/')}`;
	window.location.href = `${origin}${siteName}/${routeName}`;
};

const btnDashboard = document.getElementById('dropdown-item-dashboard');
const btnMyaccount = document.getElementById('dropdown-item-myaccount');
const btnNotifications = document.getElementById('dropdown-item-notifications');
const btnAccountsettings = document.getElementById(
	'dropdown-item-accountsettings'
);
const btnSignout = document.getElementById('dropdown-item-signout');

btnDashboard.onclick = () => redirectUrl('dashboard');
btnMyaccount.onclick = () => (window.location.href = btnMyaccount.href);
btnNotifications.onclick = () => redirectUrl('notifications-list');
btnAccountsettings.onclick = () => redirectUrl('account-settings');
btnSignout.onclick = () => (window.location.href = btnSignout.href);
