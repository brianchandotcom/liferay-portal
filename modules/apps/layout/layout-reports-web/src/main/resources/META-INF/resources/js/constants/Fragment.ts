/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Entries<T> = {
	[K in keyof T]: [K, T[K]];
}[keyof T][];

export type Fragment = {
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

export const FILTER_NAMES: Record<
	typeof FRAGMENT_FILTERS[keyof typeof FRAGMENT_FILTERS][number],
	string
> = {
	cached: Liferay.Language.get('cached'),
	fragment: Liferay.Language.get('fragment'),
	fromMaster: Liferay.Language.get('from-master'),
	notCached: Liferay.Language.get('not-cached'),
	widget: Liferay.Language.get('widget'),
};

export const FILTER_TYPE_NAMES: Record<
	keyof typeof FRAGMENT_FILTERS,
	string
> = {
	origin: `${Liferay.Language.get('filter-by')}...`,
	status: Liferay.Language.get('filter-by-status'),
	type: Liferay.Language.get('filter-by-type'),
};

export const FRAGMENT_FILTERS = {
	origin: ['fromMaster'],
	status: ['cached', 'notCached'],
	type: ['fragment', 'widget'],
} as const;

export type FragmentFilter = {
	[key in keyof typeof FRAGMENT_FILTERS]:
		| typeof FRAGMENT_FILTERS[key][number]
		| null;
};
