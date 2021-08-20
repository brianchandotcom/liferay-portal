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
 * @return {*}
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
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @param {*} value
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
