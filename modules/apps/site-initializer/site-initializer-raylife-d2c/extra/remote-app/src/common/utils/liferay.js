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
	let siteName = '/web/raylife';

	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	siteName = `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;

	return siteName;
}

export function redirectTo(url = '', currentSiteName = getLiferaySiteName()) {
	const pagePreviewEnabled = false;

	const queryParams = pagePreviewEnabled ? '?p_l_mode=preview' : '';

	window.location.href = `${currentSiteName}/${url}${queryParams}`;
}
