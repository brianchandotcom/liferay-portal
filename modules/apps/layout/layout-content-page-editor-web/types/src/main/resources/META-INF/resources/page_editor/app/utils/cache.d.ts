/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const CACHE_KEYS: {
	actionError: string;
	allowedInputTypes: string;
	collectionConfigurationUrl: string;
	collectionVariations: string;
	collectionWarningMessage: string;
	formFields: string;
	users: string;
};
export declare const CACHE_STATUS: {
	readonly loading: 'loading';
	readonly saved: 'saved';
};
export declare function initializeCache(): void;
export declare function disposeCache(): void;
export declare function getCacheKey(key: string | string[]): string | null;
export declare function getCacheItem(key: string | null): any;
export declare function deleteCacheItem(key: string): void;
export declare function setCacheItem({
	data,
	key,
	loadPromise,
	status,
}: {
	data?: Record<string, any>;
	key: string;
	loadPromise?: Promise<
		Response & {
			error: string;
		}
	>;
	status: typeof CACHE_STATUS[keyof typeof CACHE_STATUS];
}): void;
