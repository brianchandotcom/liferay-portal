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

const pagePreviewEnabled = false;

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
	authToken: '',
};

export function getLiferaySiteName() {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const urlPaths = pathname.split('/').filter(Boolean);
	const siteName = `/${urlPaths
		.slice(0, urlPaths.length > 2 ? urlPaths.length - 1 : urlPaths.length)
		.join('/')}`;

	return siteName;
}

export function redirectTo(url = '', currentSiteName = getLiferaySiteName()) {
	const queryParams = pagePreviewEnabled ? '?p_l_mode=preview' : '';

	window.location.href = `${currentSiteName}/${url}${queryParams}`;
}
