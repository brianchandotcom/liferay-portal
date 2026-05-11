/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function parseGmtOffset(value: string | undefined): string {
	if (!value) {
		return '+00:00';
	}

	const match = /^GMT([+-])(\d{1,2})(?::(\d{2}))?$/.exec(value);

	if (!match) {
		return '+00:00';
	}

	return `${match[1]}${match[2].padStart(2, '0')}:${(match[3] || '00').padStart(2, '0')}`;
}

export default function toDatePart(date: Date) {
	const timeZone = Liferay.ThemeDisplay.getTimeZone();

	const parts: Record<string, string> = {};

	const formatter = new Intl.DateTimeFormat('en-US', {
		day: '2-digit',
		hour: '2-digit',
		hour12: false,
		minute: '2-digit',
		month: '2-digit',
		timeZone,
		timeZoneName: 'longOffset',
		year: 'numeric',
	});

	for (const part of formatter.formatToParts(date)) {
		if (part.type !== 'literal') {
			parts[part.type] = part.value;
		}
	}

	return {
		day: Number(parts.day),
		hour: parts.hour === '24' ? 0 : Number(parts.hour),
		minute: Number(parts.minute),
		month: Number(parts.month),
		offset: parseGmtOffset(parts.timeZoneName),
		year: Number(parts.year),
	};
}
