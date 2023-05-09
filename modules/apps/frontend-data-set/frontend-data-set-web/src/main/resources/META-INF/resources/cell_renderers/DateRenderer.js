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
