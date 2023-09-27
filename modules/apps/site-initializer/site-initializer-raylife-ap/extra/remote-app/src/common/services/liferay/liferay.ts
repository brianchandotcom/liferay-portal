/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IThemeDisplay {
	getCompanyGroupId: () => number;
	getPathThemeImages: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserId: () => string;
	getUserName: () => string;
}

export type LiferayStorage = Storage & {
	getItem(key: string, consentType: string): string | null;
	setItem(key: string, value: string, consentType: string): void;
};

export type LiferayOnAction<T> = (payload: T) => void;

interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	Util: {
		LocalStorage: LiferayStorage & {TYPES: {[key: string]: string}};
		SessionStorage: LiferayStorage & {TYPES: {[key: string]: string}};
	};
	authToken: string;
	detach: <T = any>(eventName: string, action?: (payload: T) => void) => void;
	on: <T = any>(eventName: string, action?: (payload: T) => void) => void;
	publish: (eventName: string, optopms?: any) => void;
}

declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

const TYPES = {};

export const Liferay = window.Liferay || {
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getPathThemeImages: () => '',
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => '',
		getUserName: () => 'Test Test',
	},
	Util: {
		LocalStorage: Object.assign(localStorage, {TYPES}),
		SessionStorage: Object.assign(sessionStorage, {TYPES}),
	},
	authToken: '',
	publish: '',
};
