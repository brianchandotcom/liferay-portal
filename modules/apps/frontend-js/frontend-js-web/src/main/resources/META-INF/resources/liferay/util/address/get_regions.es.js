/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns a list of regions by country
 * @callback callback
 * @param {!string} selectKey The selected region ID
 * @return {array} Array of regions by country
 */
export default function getRegions(callback, selectKey) {
	if (typeof callback !== 'function') {
		throw new TypeError('Parameter callback must be a function');
	}

	if (typeof selectKey !== 'string') {
		throw new TypeError('Parameter selectKey must be a string');
	}

	Liferay.Service(
		'/region/get-regions',
		{
			active: true,
			countryId: parseInt(selectKey, 10),
		},
		callback
	);
}
