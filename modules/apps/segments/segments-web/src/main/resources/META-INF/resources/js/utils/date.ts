/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getUtcDate(date: Date = new Date()) {
	return new Date(
		Date.UTC(
			date.getUTCFullYear(),
			date.getUTCMonth(),
			date.getUTCDate(),
			0,
			0,
			0,
			0
		)
	);
}

export function convertTimezoneToUTC(dateString: string) {
	const utcDate = new Date(dateString);
	const offsetMinutes = utcDate.getTimezoneOffset();
	const offsetMilliseconds = offsetMinutes * 60 * 1000;

	const localTimestamp =
		offsetMinutes > 0
			? utcDate.getTime() + offsetMilliseconds
			: utcDate.getTime() - Math.abs(offsetMilliseconds);

	return new Date(localTimestamp);
}
