/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function isValidUSZip(zipCode: string) {
	const zipCodePattern = /(^\d{5}$)|(^\d{5}-\d{4}$)/;

	return zipCodePattern.test(zipCode);
}

export function isValidEmail(email: string) {
	const emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	return emailPattern.test(email);
}

export function isValidPhone(elementValue: string) {
	const phoneNumberPattern = /^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/;

	return phoneNumberPattern.test(elementValue);
}
