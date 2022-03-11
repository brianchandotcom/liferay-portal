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

interface IThemeDisplay {
	getBCP47LanguageId(): () => string;
	getCompanyGroupId: () => number;
	getLanguageId: () => string;
	getPathThemeImages: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserName: () => string;
}

interface LiferayUtil {
	openToast: (options?: any) => void;
}

interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	Util: LiferayUtil;
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
		getLanguageId: () => 'en_US',
		getPathThemeImages: () => '',
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserName: () => 'Test Test',
	},
	authToken: '',
};
