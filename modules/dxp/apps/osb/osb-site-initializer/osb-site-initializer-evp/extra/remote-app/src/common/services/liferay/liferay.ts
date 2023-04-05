/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

interface IThemeDisplay {
	getCompanyGroupId: () => number;
	getPathThemeImages: () => string;
	getPortalURL: () => string;
	getScopeGroupId: () => number;
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
		getSiteGroupId: () => 0,
		getUserId: () => '',
		getUserName: () => 'Test Test',
	},
	authToken: '',
	publish: '',
};
