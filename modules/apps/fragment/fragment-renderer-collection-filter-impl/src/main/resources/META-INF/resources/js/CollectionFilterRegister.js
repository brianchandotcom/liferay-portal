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

const COLLECTION_FILTER_PARAMETER_PREFIX = 'filter_';

/**
 * @type {Map<string, {filterFragmentEntryLinkId: string, filterType: string, value: *}>}
 */
const collectionFilterValues = new Map();

/**
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @return {*}
 */
export const getCollectionFilterValue = (
	filterType,
	filterFragmentEntryLinkId
) => {
	let value = new URL(window.location.href).searchParams.getAll(
		`${COLLECTION_FILTER_PARAMETER_PREFIX}${filterType}_${filterFragmentEntryLinkId}`
	);

	if (value.length === 0) {
		value = null;
	}
	else if (value.length === 1) {
		value = value[0];
	}

	return value;
};

/**
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @param {*} value
 */
export const setCollectionFilterValue = (
	filterType,
	filterFragmentEntryLinkId,
	value
) => {
	if (collectionFilterValues.has(filterFragmentEntryLinkId)) {
		collectionFilterValues.set(filterFragmentEntryLinkId, {
			filterFragmentEntryLinkId,
			filterType,
			value,
		});

		if (document.body.classList.contains('has-edit-mode-menu')) {
			return;
		}

		const url = new URL(window.location.href);

		url.searchParams.forEach((_, paramName) => {
			if (paramName.startsWith(COLLECTION_FILTER_PARAMETER_PREFIX)) {
				const [
					,
					urlFilterType,
					urlFilterFragmentEntryLinkId,
				] = paramName.split('_');

				const entry = collectionFilterValues.get(
					urlFilterFragmentEntryLinkId
				);

				if (
					entry &&
					entry.filterType === urlFilterType &&
					entry.filterFragmentEntryLinkId ===
						urlFilterFragmentEntryLinkId
				) {
					url.searchParams.delete(paramName);
				}
			}
		});

		collectionFilterValues.forEach((entry) => {
			if (entry.value === null) {
				return;
			}

			const key = `${COLLECTION_FILTER_PARAMETER_PREFIX}${entry.filterType}_${entry.filterFragmentEntryLinkId}`;

			if (Array.isArray(entry.value)) {
				entry.value.forEach((valueChunk) => {
					url.searchParams.append(key, valueChunk);
				});
			}
			else {
				url.searchParams.set(key, value);
			}
		});

		window.location.href = url.toString();
	}
	else if (process.env.NODE_ENV === 'development') {
		console.error(
			`Collection Filter "${filterFragmentEntryLinkId}" has not been registered`
		);
	}
};

/**
 *
 * @param {object} data
 * @param {string} data.fragmentEntryLinkId
 */
export default function CollectionFilterRegister({
	fragmentEntryLinkId: filterFragmentEntryLinkId,
}) {
	collectionFilterValues.set(filterFragmentEntryLinkId, {
		filterFragmentEntryLinkId,
		filterType: null,
		value: null,
	});

	return () => {
		collectionFilterValues.delete(filterFragmentEntryLinkId);
	};
}
