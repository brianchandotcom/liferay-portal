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

function formatStrings(value) {
	if (typeof value === 'string') {
		return `'${value}'`;
	}

	if (Array.isArray(value)) {
		return value.map((valueEl) => {
			if (typeof valueEl === 'string') {
				return `'${valueEl}'`;
			}

			if (valueEl.value) {
				return {
					...valueEl,
					value:
						typeof value === 'string'
							? `'${valueEl.value}'`
							: valueEl.value,
				};
			}

			return valueEl;
		});
	}

	return value;
}

function createOdataFilterString(
	key,
	operator = 'eq',
	type,
	value,
	selectionType = 'multiple'
) {
	const formattedValue = formatStrings(value);

	switch (type) {
		case 'autocomplete':
			if (selectionType === 'multiple') {
				return `${key}/any(x:${formattedValue
					.map((v) => `(x eq ${v.value})`)
					.join(' or ')})`;
			}

			return `${key} eq ${formattedValue[0].value}`;
		case 'date':
			return `${key} ${operator} ${convertObjectDateToIsoString(
				formattedValue
			)}`;
		case 'dateRange':
			if (formattedValue.from && formattedValue.to) {
				return `${key} ge ${convertObjectDateToIsoString(
					formattedValue.from,
					'from'
				)}) and (${key} le ${convertObjectDateToIsoString(
					formattedValue.to,
					'to'
				)}`;
			}
			if (formattedValue.from) {
				return `${key} ge ${convertObjectDateToIsoString(
					formattedValue.from,
					'from'
				)}`;
			}
			if (formattedValue.to) {
				return `${key} le ${convertObjectDateToIsoString(
					formattedValue.to,
					'to'
				)}`;
			}
			break;
		default:
			if (Array.isArray(formattedValue)) {
				return formattedValue
					.map(
						(el) =>
							`(${createOdataFilterString(
								key,
								operator,
								type,
								el
							)})`
					)
					.join(' or ');
			}

			return `${key} ${operator} ${formattedValue}`;
	}
}

export default function createOdataFilter(filters) {
	if (!filters.length) {
		return null;
	}

	return filters
		.map((filter) =>
			createOdataFilterString(
				filter.id,
				filter.operator,
				filter.type,
				filter.value,
				filter.selectionType
			)
		)
		.map((filterString) => `(${filterString})`)
		.join(' and ');
}
