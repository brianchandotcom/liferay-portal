/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns a list of countries
 * @callback callback
 * @return {array} Array of countries
 */
export default function getCountries(callback) {
	if (typeof callback !== 'function') {
		throw new TypeError('Parameter callback must be a function');
	}

	Liferay.Service(
		'/country/get-company-countries',
		{
			active: true,
			companyId: Liferay.ThemeDisplay.getCompanyId(),
		},
		callback
	);
}
