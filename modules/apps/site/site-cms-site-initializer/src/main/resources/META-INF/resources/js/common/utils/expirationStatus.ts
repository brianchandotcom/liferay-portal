/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EXPIRING_SOON_THRESHOLD_DAYS, MS_PER_DAY} from './constants';
import dateFormat from './dateFormat';

export function isExpiringSoon(expirationDate?: string | Date): boolean {
	if (!expirationDate) {
		return false;
	}

	const expirationTime = new Date(expirationDate).getTime();

	if (Number.isNaN(expirationTime)) {
		return false;
	}

	const diff = expirationTime - Date.now();

	return diff <= EXPIRING_SOON_THRESHOLD_DAYS * MS_PER_DAY;
}

export function formatExpirationDate(
	expirationDate?: string | Date
): string | null {
	if (!expirationDate) {
		return null;
	}

	return dateFormat(
		{
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
			month: '2-digit',
			timeZone: Liferay.ThemeDisplay.getTimeZone(),
			year: 'numeric',
		},
		expirationDate instanceof Date
			? expirationDate.toISOString()
			: expirationDate
	);
}

export function formatExpirationDateLong(
	expirationDate?: string | Date
): string | null {
	if (!expirationDate) {
		return null;
	}

	return dateFormat(
		{
			dateStyle: 'long',
			timeStyle: 'short',
			timeZone: Liferay.ThemeDisplay.getTimeZone(),
		},
		expirationDate instanceof Date
			? expirationDate.toISOString()
			: expirationDate
	);
}
