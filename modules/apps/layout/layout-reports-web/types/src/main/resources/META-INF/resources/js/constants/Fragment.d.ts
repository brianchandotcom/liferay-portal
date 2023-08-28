/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type Entries<T> = {
	[K in keyof T]: [K, T[K]];
}[keyof T][];
export declare type Fragment = {
	cached: boolean;
	fragmentCollectionURL: string;
	fromMaster: boolean;
	hierarchy: string;
	isPortlet: boolean;
	itemId: string;
	itemType: string;
	name: string;
	renderTime: number;
	warnings: string[];
};
export declare const FILTER_NAMES: Record<
	typeof FRAGMENT_FILTERS[keyof typeof FRAGMENT_FILTERS][number],
	string
>;
export declare const FILTER_TYPE_NAMES: Record<
	keyof typeof FRAGMENT_FILTERS,
	string
>;
export declare const FRAGMENT_FILTERS: {
	readonly origin: readonly ['fromMaster'];
	readonly status: readonly ['cached', 'notCached'];
	readonly type: readonly ['fragment', 'widget'];
};
export declare type FragmentFilter = {
	[key in keyof typeof FRAGMENT_FILTERS]:
		| typeof FRAGMENT_FILTERS[key][number]
		| null;
};
