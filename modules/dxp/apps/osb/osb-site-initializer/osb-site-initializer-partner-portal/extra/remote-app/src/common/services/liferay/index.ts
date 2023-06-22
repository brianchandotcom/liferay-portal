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
	getLayoutRelativeControlPanelURL: () => string;
	getLayoutRelativeURL: () => string;
	getPathThemeImages: () => string;
	getPortalURL: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserEmailAddress: () => string;
	getUserId: () => string;
	getUserName: () => string;
}
interface IUtil {
	navigate: (path: string) => void;
	openConfirmModal: (options?: any) => void;
	openToast: (options?: any) => void;
}

interface ILiferay {
	FeatureFlags: {[index: string]: boolean};
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
	FeatureFlags: {},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCompanyGroupId: () => 0,
		getDefaultLanguageId: () => 'en_US',
		getLanguageId: () => 'en_US',
		getLayoutRelativeControlPanelURL: () => '',
		getLayoutRelativeURL: () => '',
		getPathThemeImages: () => '',
		getPortalURL: () => '',
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserEmailAddress: () => '',
		getUserId: () => '0',
		getUserName: () => 'Test Test',
	},
	authToken: '',
};
