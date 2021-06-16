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

import {toArray} from './data.es';

const hasSearchLocationValue = (fields) => {
	const searchLocationField = fields.find(
		(field) => field.type === 'search_location'
	);

	return !!searchLocationField;
};

const parse = (value, defaultValue) => {
	try {
		return JSON.parse(value);
	}
	catch (error) {
		console.error(error);

		return defaultValue !== undefined ? defaultValue : {};
	}
};

const transformSearchLocationValues = (fields, data) => {
	if (hasSearchLocationValue(fields)) {
		const labels = {
			...{place: 'Search Location'},
			address: 'Address',
			city: 'City',
			country: 'Country',
			['postal-code']: 'Postal Code',
			state: 'State',
		};
		const searchLocationFieldName = Object.keys(data).find((key) => {
			if (typeof data[key] === 'object') {
				return data[key]?.type === 'search_location';
			}

			return false;
		});
		const visibleFields = Object.keys(labels);
		const searchLocationFieldValues = {};
		const dataSearchLocationFields = {};
		const {values} = data[searchLocationFieldName];

		const searchLocationFields = visibleFields.map((visibleField) => {
			toArray(values).forEach((value) => {
				const existentValues =
					searchLocationFieldValues[visibleField] || [];
				const searchLocationValue = parse(value, {});

				searchLocationFieldValues[visibleField] = [
					...existentValues,
					{value: searchLocationValue[visibleField]},
				];
			});

			dataSearchLocationFields[visibleField] = {
				totalEntries: Object.keys(
					searchLocationFieldValues[visibleField]
				).length,
				type: visibleField,
				values: searchLocationFieldValues[visibleField],
			};

			return {
				columns: {},
				label: labels[visibleField],
				name: visibleField,
				options: {},
				rows: {},
				type: visibleField,
			};
		});

		const newFields = [...fields];
		const searchLocationFieldIndex = newFields.findIndex(
			(field) => field.type === 'search_location'
		);

		newFields.splice(searchLocationFieldIndex, 1, ...searchLocationFields);

		delete data[searchLocationFieldName];

		const newData = {
			...data,
			...dataSearchLocationFields,
		};

		newData.totalItems = Object.keys(newData).length - 1;

		return {
			data: newData,
			fields: newFields,
		};
	}
	else {
		return {
			data,
			fields,
		};
	}
};

export {transformSearchLocationValues};
