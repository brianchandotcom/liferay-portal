/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const redirectUrl = (routeName) => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const urlPaths = pathname.split('/').filter(Boolean);
	const siteName = `/${urlPaths
		.slice(0, urlPaths.length > 2 ? urlPaths.length - 1 : urlPaths.length)
		.join('/')}`;

	window.location.href = `${origin}${siteName}/${routeName}`;
};

const userName = Liferay.ThemeDisplay.getUserName();
fragmentElement.querySelector(
	'.footer-bar .avatar-user-name'
).innerHTML = userName;

const btnDashboard = fragmentElement.querySelector('.dashboard-menu');
const btnApplications = fragmentElement.querySelector('.applications-menu');
const btnPolicies = fragmentElement.querySelector('.policies-menu');
const btnClaims = fragmentElement.querySelector('.claims-menu');
const btnReports = fragmentElement.querySelector('.reports-menu');
const btnLogo = fragmentElement.querySelector('.top-bar');
const btnNotification = fragmentElement.querySelector(
	'.notification-container'
);
const notificationIcon = fragmentElement.querySelector('#panel-sidebar');

btnNotification.onclick = () =>
	notificationIcon.classList.toggle('notification-open-side-bar');
btnDashboard.onclick = () => redirectUrl('dashboard');
btnApplications.onclick = () => redirectUrl('applications');
btnPolicies.onclick = () => redirectUrl('policies');
btnClaims.onclick = () => redirectUrl('claims');
btnReports.onclick = () => redirectUrl('reports');
btnLogo.onclick = () => redirectUrl('dashboard');
