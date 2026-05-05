/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

function getDateOptions(): Intl.DateTimeFormatOptions {
	return {
		day: 'numeric',
		month: 'short',
		timeZone: Liferay.ThemeDisplay.getTimeZone(),
		year: 'numeric',
	};
}

function getCalendarDayOptions(): Intl.DateTimeFormatOptions {
	return {
		day: '2-digit',
		month: '2-digit',
		timeZone: Liferay.ThemeDisplay.getTimeZone(),
		year: 'numeric',
	};
}

let _calendarDayFormatter: Intl.DateTimeFormat | null = null;

function getCalendarDayFormatter(): Intl.DateTimeFormat {
	if (!_calendarDayFormatter) {
		_calendarDayFormatter = new Intl.DateTimeFormat(
			'en-US',
			getCalendarDayOptions()
		);
	}

	return _calendarDayFormatter;
}

function getCalendarDayKey(date: Date): number {
	const parts: Record<string, string> = {};

	for (const part of getCalendarDayFormatter().formatToParts(date)) {
		parts[part.type] = part.value;
	}

	return (
		Number(parts.year) * 10000 +
		Number(parts.month) * 100 +
		Number(parts.day)
	);
}

const ReviewDateRenderer = ({
	itemData,
	value,
}: {
	itemData?: {dateReview?: string};
	value?: string;
}) => {
	const rawValue = value || itemData?.dateReview;

	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const formatter = useMemo(
		() => new Intl.DateTimeFormat(locale, getDateOptions()),
		[locale]
	);

	if (!rawValue) {
		return <span className="text-secondary">--</span>;
	}

	const date = new Date(rawValue);
	const reviewed = getCalendarDayKey(date) <= getCalendarDayKey(new Date());

	return (
		<span className={reviewed ? 'text-warning' : 'text-dark'}>
			{formatter.format(date)}
		</span>
	);
};

export default ReviewDateRenderer;
