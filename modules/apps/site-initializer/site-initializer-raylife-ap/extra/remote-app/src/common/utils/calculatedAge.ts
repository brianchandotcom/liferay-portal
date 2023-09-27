/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function calculatedAge(dateOfBirth: string) {
	return Math.floor(
		Math.ceil(
			Math.abs(Date.parse(dateOfBirth) - Date.now()) / (1000 * 3600 * 24)
		) / 365.25
	);
}

export default calculatedAge;
