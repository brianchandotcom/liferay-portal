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
	getBCP47LanguageId: () => string;
	getCompanyGroupId: () => number;
	getDefaultLanguageId: () => string;
	getLanguageId: () => string;
	getLayoutRelativeURL: () => string;
	getPathThemeImages: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserId: () => string;
	getUserName: () => string;
}
interface IUtil {
	navigate: (path: string) => void;
	openToast: (options?: any) => void;
}
interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	Util: IUtil;
	authToken: string;
}
declare global {
	interface Window {
		Liferay: ILiferay;
	}
}
export const Liferay = window.Liferay || {
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCompanyGroupId: () => 0,
		getDefaultLanguageId: () => 'en_US',
		getLanguageId: () => 'en_US',
		getLayoutRelativeURL: () => '',
		getPathThemeImages: () => '',
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => '0',
		getUserName: () => 'Test Test',
	},
	authToken: '',
};
