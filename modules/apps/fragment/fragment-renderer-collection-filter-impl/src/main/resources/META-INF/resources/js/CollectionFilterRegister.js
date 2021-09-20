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

let _filterPrefix = '';

/**
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @return {null|string|string[]} Returns null if there are no filter values,
 *  a string if there is a single one, or an Array of strings if multiple values
 *  are found for the given filterType (Java's filterKey) and
 *  fragmentEntryLinkId.
 */
export const getCollectionFilterValue = (
	filterType,
	filterFragmentEntryLinkId
) => {
	let value = new URL(window.location.href).searchParams.getAll(
		`${_filterPrefix}${filterType}_${filterFragmentEntryLinkId}`
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
 * Replaces all existing filter values with the new one, only for given
 * filterType (which corresponds to Java's filterKey) and fragmentEntryLinkId.
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @param {string|string[]} value
 */
export const setCollectionFilterValue = (
	filterType,
	filterFragmentEntryLinkId,
	value
) => {
	if (document.body.classList.contains('has-edit-mode-menu')) {
		return;
	}

	const paramName = `${_filterPrefix}${filterType}_${filterFragmentEntryLinkId}`;
	const url = new URL(window.location.href);

	if (Array.isArray(value)) {
		url.searchParams.delete(paramName);

		value.forEach((valueChunk) => {
			url.searchParams.append(paramName, valueChunk);
		});
	}
	else {
		url.searchParams.set(paramName, value);
	}

	window.location.href = url.toString();
};

/**
 *
 * @param {object} data
 * @param {string} data.filterPrefix
 */
export default function CollectionFilterRegister({filterPrefix}) {
	_filterPrefix = filterPrefix;
}
