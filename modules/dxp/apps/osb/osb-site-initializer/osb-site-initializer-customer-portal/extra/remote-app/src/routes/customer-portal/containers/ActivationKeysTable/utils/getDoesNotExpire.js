/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DNE_YEARS = 100;

export function getDoesNotExpire(date) {
	const today = new Date();
	today.setFullYear(today.getFullYear() + DNE_YEARS);

	return new Date(date) >= today;
}
