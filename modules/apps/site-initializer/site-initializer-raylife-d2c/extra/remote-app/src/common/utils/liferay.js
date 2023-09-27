/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const TYPES = {};

export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	ThemeDisplay: {
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getPathThemeImages: () => null,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
	},
	Util: {
		LocalStorage: Object.assign(localStorage, {TYPES}),
		SessionStorage: Object.assign(sessionStorage, {TYPES}),
	},
	authToken: '',
};

export function getLiferaySiteName() {
	const path = Liferay.ThemeDisplay.getPathContext();

	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	if (path) {
		return `/${pathSplit.slice(1, pathSplit.length - 1).join('/')}`;
	}

	return `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;
}

export function redirectTo(url = '', currentSiteName = getLiferaySiteName()) {
	const pagePreviewEnabled = false;

	const queryParams = pagePreviewEnabled ? '?p_l_mode=preview' : '';

	window.location.href = `${Liferay.ThemeDisplay.getPathContext()}${currentSiteName}/${url}${queryParams}`;
}
