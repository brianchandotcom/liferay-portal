/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type FetcherReturn<T> = Promise<
	T & {
		error?: string;
	}
>;
export declare type CacheData<T> = {
	data?: T;
	key: string;
	loadPromise?: FetcherReturn<T>;
	status: typeof CACHE_STATUS[keyof typeof CACHE_STATUS];
};
export declare const CACHE_KEYS: {
	readonly actionError: 'actionError';
	readonly allowedInputTypes: 'allowedInputTypes';
	readonly collectionConfigurationUrl: 'collectionConfigurationUrl';
	readonly collectionVariations: 'collectionVariations';
	readonly collectionWarningMessage: 'collectionWarningMessage';
	readonly formFields: 'formFields';
	readonly relationships: 'relationships';
	readonly roles: 'roles';
	readonly users: 'users';
};
export declare type CacheKey = typeof CACHE_KEYS[keyof typeof CACHE_KEYS];
export declare const CACHE_STATUS: {
	readonly loading: 'loading';
	readonly saved: 'saved';
};
export declare function initializeCache(): void;
export declare function disposeCache(): void;
export declare function getCacheKey(
	key: CacheKey | [CacheKey, ...string[]]
): string | null;
export declare function getCacheItem<T>(
	key: string | null
): CacheData<T> | Record<string, never>;
export declare function deleteCacheItem(key: string): void;
export declare function setCacheItem<T>({
	data,
	key,
	loadPromise,
	status,
}: CacheData<T>): void;
