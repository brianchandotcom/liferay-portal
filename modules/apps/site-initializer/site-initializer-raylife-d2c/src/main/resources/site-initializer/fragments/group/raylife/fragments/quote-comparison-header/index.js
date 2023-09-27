/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

try {
	const firstName = JSON.parse(
		Liferay.Util.LocalStorage.getItem(
			'raylife-application-form',
			Liferay.Util.LocalStorage.TYPES.NECESSARY
		)
	).basics.businessInformation.firstName;

	if (firstName) {
		document.getElementById(
			'quote-comparison-user-first-name'
		).innerHTML = firstName;
	}
}
catch (error) {
	document.getElementById('quote-comparison-user-first-name').innerHTML =
		'Sam';
}
