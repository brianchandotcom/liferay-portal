/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export function toLocalDateTimeFormatted(dateUTC: string, options: any) {
	const date = new Date(dateUTC);

	const timeZone = new Intl.DateTimeFormat('en-us', options);

	return timeZone.format(date);
}
