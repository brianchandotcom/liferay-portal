/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch} from 'frontend-js-web';
import {useEffect, useState} from 'react';

import type {FilterPropertyGroup} from '../components/CollectionFilterBuilder/types';

interface Store {
	listeners: Set<(properties: FilterPropertyGroup[]) => void>;
	properties: FilterPropertyGroup[];
}

const stores = new Map<string, Store>();

// Refetches the filterable properties whenever the user changes the
// collection's asset source (type / subtype selectors), fired in
// event sourceChange. The available fields depend on the selected
// class names + class types.

function fetchProperties(
	namespace: string,
	propertiesURL: string,
	store: Store
): void {
	const assetTypeSelector = document.getElementById(
		`${namespace}anyAssetType`
	) as HTMLSelectElement | null;

	const assetTypeValue = assetTypeSelector?.value || '';

	let classNameIds: string[] = [];

	if (assetTypeValue === 'false') {

		// Multi-selection: collect every option out of the hidden
		// <select> the JSP populates with the user's picks.

		const multiSelector = document.getElementById(
			`${namespace}currentClassNameIds`
		) as HTMLSelectElement | null;

		classNameIds = Array.from(multiSelector?.options || []).map(
			(option) => option.value
		);
	}
	else if (assetTypeValue && assetTypeValue !== 'true') {
		classNameIds = [assetTypeValue];
	}

	let classTypeIds: string[] = [];

	// Subtypes only make sense when exactly one asset type is
	// selected — the subtype UI is hidden otherwise.

	if (classNameIds.length === 1) {
		const subtypeContainer = document.querySelector(
			'.asset-subtype:not(.hide)'
		);

		const subtypeSelector = subtypeContainer?.querySelector(
			`[id^="${namespace}anyClassType"]`
		) as HTMLSelectElement | null;

		const subtypeValue = subtypeSelector?.value;

		if (subtypeValue === 'false' && subtypeContainer) {

			// Multi-subtype selection: same pattern as
			// classNameIds above, but the element id is
			// namespaced with the class name.

			const className = subtypeContainer.getAttribute('data-class-name');

			const multiSubtypeSelect = document.getElementById(
				`${namespace}${className}currentClassTypeIds`
			) as HTMLSelectElement | null;

			classTypeIds = Array.from(multiSubtypeSelect?.options || []).map(
				(option) => option.value
			);
		}
		else if (subtypeValue && subtypeValue !== 'true') {
			classTypeIds = [subtypeValue];
		}
	}

	fetch(
		addParams(
			{
				[`${namespace}classNameIds`]: classNameIds.join(','),
				[`${namespace}classTypeIds`]: classTypeIds.join(','),
			},
			propertiesURL
		)
	)
		.then((response) => response.json())
		.then((data) => {
			store.properties = data || [];
			store.listeners.forEach((listener) => listener(store.properties));
		})
		.catch((error) =>
			console.error('Failed to fetch type properties: ', error)
		);
}

// Single store per (namespace, propertiesURL) shared by every consumer of the
// hook so the Liferay event listener and fetch run once, regardless of how
// many React roots mount this hook.

function getStore(
	namespace: string,
	propertiesURL: string,
	initialProperties: FilterPropertyGroup[]
): Store {
	const key = `${namespace}|${propertiesURL}`;

	let store = stores.get(key);

	if (!store) {
		const newStore: Store = {
			listeners: new Set(),
			properties: initialProperties,
		};

		stores.set(key, newStore);

		Liferay.on(`${namespace}sourceChange`, () =>
			fetchProperties(namespace, propertiesURL, newStore)
		);

		store = newStore;
	}

	return store;
}

export default function useTypeProperties(
	namespace: string,
	propertiesURL?: string,
	initialProperties: FilterPropertyGroup[] = []
): FilterPropertyGroup[] {
	const [properties, setProperties] =
		useState<FilterPropertyGroup[]>(initialProperties);

	useEffect(() => {
		if (!propertiesURL) {
			return undefined;
		}

		const store = getStore(namespace, propertiesURL, initialProperties);

		setProperties(store.properties);

		store.listeners.add(setProperties);

		return () => {
			store.listeners.delete(setProperties);
		};

		// initialProperties is intentionally omitted. It's only used on the
		// first run to populate the store; subsequent consumers read the
		// store's current properties.
		// eslint-disable-next-line
	}, [namespace, propertiesURL]);

	return properties;
}
