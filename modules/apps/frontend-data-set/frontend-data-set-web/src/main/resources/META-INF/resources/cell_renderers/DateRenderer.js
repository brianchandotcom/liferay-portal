/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropType from 'prop-types';

function DateRenderer({options, value}) {
	if (!value) {
		return null;
	}

	let timestamp = value;

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

	const locale = themeDisplay.getBCP47LanguageId();

	const dateOptions = {
		day: options?.format?.day || 'numeric',
		month: options?.format?.month || 'short',
		timeZone: options?.format?.timeZone || 'UTC',
		year: options?.format?.year || 'numeric',
	};

	const formattedDate = new Intl.DateTimeFormat(locale, dateOptions).format(
		timestamp
	);

	return formattedDate;
}

DateRenderer.propTypes = {
	options: PropType.shape({
		format: PropType.object,
	}),
	value: PropType.string.isRequired,
};

export default DateRenderer;
