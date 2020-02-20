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

	const sign = Math.sign(offset) > 0 ? '-' : '+';

	const fractionalMinutes = Math.abs(offset % 1);

	const hourFormatted = `${sign}${String(
		Math.abs(Math.trunc(offset))
	).padStart(2, '0')}`;

	if (fractionalMinutes) {
		return `${hourFormatted}:${60 * fractionalMinutes}`;
	}

	return `${hourFormatted}:00`;
}
