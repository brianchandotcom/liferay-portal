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
	getCompanyGroupId: () => number;
	getPathThemeImages: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserName: () => string;
}

interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	authToken: string;
	detach: (eventName: string, options?: any) => void;
	on: (eventName: string, options?: any) => void;
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
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserName: () => 'Test Test',
	},
	authToken: '',
};
