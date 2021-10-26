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

export function formatDateObject(dateObject) {
	return `${dateObject.year}-${('0' + dateObject.month).slice(-2)}-${(
		'0' + dateObject.day
	).slice(-2)}`;
}

export function getDateFromDateString(dateString) {
	const [year, month, day] = dateString.split('-');

	return {
		day: Number(day),
		month: Number(month),
		year: Number(year),
	};
}

export function prettifyDateObject(dateObject) {
	const date = new Date(
		dateObject.year,
		dateObject.month - 1,
		dateObject.day
	);

	return date.toLocaleDateString();
}

export function formatDateRangeObject(dateRangeObject) {
	if (dateRangeObject.from && dateRangeObject.to) {
		return `${prettifyDateObject(
			dateRangeObject.from
		)} - ${prettifyDateObject(dateRangeObject.to)}`;
	}
	if (dateRangeObject.from) {
		return `${Liferay.Language.get('from')} ${prettifyDateObject(
			dateRangeObject.from
		)}`;
	}
	if (dateRangeObject.to) {
		return `${Liferay.Language.get('to')} ${prettifyDateObject(
			dateRangeObject.to
		)}`;
	}
}

export function convertObjectDateToIsoString(objDate, direction) {
	const time = direction === 'from' ? [0, 0, 0, 0] : [23, 59, 59, 999];
	const date = new Date(
		objDate.year,
		objDate.month - 1,
		objDate.day,
		...time
	);

	return date.toISOString();
}
