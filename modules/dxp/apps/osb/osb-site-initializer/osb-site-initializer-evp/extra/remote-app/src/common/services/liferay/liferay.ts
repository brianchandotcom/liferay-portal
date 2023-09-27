/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IThemeDisplay {
	getCompanyGroupId: () => number;
	getPathThemeImages: () => string;
	getPortalURL: () => string;
	getScopeGroupId: () => number;
	getSiteAdminURL: () => string;
	getSiteGroupId: () => number;
	getUserId: () => string;
	getUserName: () => string;
}

interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	authToken: string;
	detach: (eventName: string, options?: any) => void;
	on: (eventName: string, options?: any) => void;
	publish: (eventName: string, optopms?: any) => void;
}

declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay || {
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getPathThemeImages: () => '',
		getPortalURL: () => '',
		getScopeGroupId: () => 0,
		getSiteAdminURL: () => '',
		getSiteGroupId: () => 0,
		getUserId: () => '',
		getUserName: () => 'Test Test',
	},
	authToken: '',
	publish: '',
};
