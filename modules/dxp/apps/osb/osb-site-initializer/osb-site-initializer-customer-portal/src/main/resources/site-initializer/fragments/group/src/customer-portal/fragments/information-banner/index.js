/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const bannerElement = document.querySelectorAll('.cp-information-banner');
const bannerFragment = fragmentElement.querySelector('.cp-information-banner');
const closeButton = fragmentElement.querySelector('.close');

const configurationDate = configuration.expirationDate;
const currentDate = new Date();
const {0: month, 1: day, 2: year} = configurationDate.split('/');
const expirationDate = new Date(year, month - 1, day);
const isExpired = expirationDate < currentDate;

const bannerState = !sessionStorage.getItem('@liferayCP:showBanner')
	? true
	: false;
const sessionStorageLogState = Liferay.ThemeDisplay.isSignedIn();

if (!isExpired && sessionStorageLogState && bannerState) {
	bannerElement?.forEach((item) => {
		item.style.display = 'flex';
	});
}

if (closeButton) {
	closeButton.addEventListener('click', () => {
		sessionStorage.setItem('@liferayCP:showBanner', false);
		bannerFragment.style.display = 'none';
	});
}
