/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ETimeZoneBehaviors} from '../constants';

interface IDateRendererOptions {
	format?: any;
	timeZoneBehavior?: ETimeZoneBehaviors;
}

function DateRenderer({
	options,
	value,
}: {
	options?: IDateRendererOptions;
	value: number | string;
}) {
	if (!value) {
		return null;
	}

	let timestamp: number;

	if (typeof value === 'string') {
		const date = value.split('T')[0];

		const dateArray = date.split('-');

		if (dateArray.length === 3) {
			const [year, month, day] = dateArray;

			timestamp = Date.UTC(Number(year), Number(month) - 1, Number(day));
		}
		else {
			timestamp = Number(value);
		}
	}
	else {
		timestamp = value;
	}

	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const dateOptions = {
		day: options?.format?.day || 'numeric',
		month: options?.format?.month || 'short',
		timeZone: options?.format?.timeZone || 'UTC',
		year: options?.format?.year || 'numeric',
	};

	if (
		options?.timeZoneBehavior ===
		ETimeZoneBehaviors.APPLY_THEME_DISPLAY_TIME_ZONE
	) {
		dateOptions.timeZone = Liferay.ThemeDisplay.getTimeZone();
	}

	const formattedDate = new Intl.DateTimeFormat(locale, dateOptions).format(
		timestamp
	);

	return formattedDate;
}

export default DateRenderer;
