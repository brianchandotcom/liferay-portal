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
