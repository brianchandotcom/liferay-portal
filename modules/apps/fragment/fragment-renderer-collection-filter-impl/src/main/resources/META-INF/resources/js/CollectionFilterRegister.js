/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
export function getCollectionFilterValue(
	filterType,
	filterFragmentEntryLinkId
) {
	let value = new URL(window.location.href).searchParams.getAll(
		`${_filterPrefix}${filterType}_${filterFragmentEntryLinkId}`
	);

	if (!value.length) {
		value = null;
	}
	else if (value.length === 1) {
		value = value[0];
	}

	return value;
}

/**
 * Replaces all existing filter values with the new one, only for given
 * filterType (which corresponds to Java's filterKey) and fragmentEntryLinkId.
 * @param {string} filterType
 * @param {string} filterFragmentEntryLinkId
 * @param {string|string[]} value
 */
export function setCollectionFilterValue(
	filterType,
	filterFragmentEntryLinkId,
	value
) {
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
}

/**
 *
 * @param {object} data
 * @param {string} data.filterPrefix
 */
export default function CollectionFilterRegister({filterPrefix}) {
	_filterPrefix = filterPrefix;
}
