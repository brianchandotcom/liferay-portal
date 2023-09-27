/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Conver UTC date to local date
 * @param {Date} date
 * @returns {number}
 */
export function convertUTCDateToLocalDate(date = new Date()) {
	return new Date(date.getTime() - date.getTimezoneOffset() * 60 * 1000);
}

/**
 * Get timezone offset hour
 * @param {Date} date
 * @returns {number}
 */
export function getTimezoneOffsetHour(date = new Date()) {
	const offset = date.getTimezoneOffset() / 60;

	const sign = offset > 0 ? '-' : '+';

	const fractionalMinutes = Math.abs(offset % 1);

	const hourFormatted = `${sign}${String('00' + Math.abs(~~offset)).slice(
		-2
	)}`;

	if (fractionalMinutes) {
		return `${hourFormatted}:${60 * fractionalMinutes}`;
	}

	return `${hourFormatted}:00`;
}
