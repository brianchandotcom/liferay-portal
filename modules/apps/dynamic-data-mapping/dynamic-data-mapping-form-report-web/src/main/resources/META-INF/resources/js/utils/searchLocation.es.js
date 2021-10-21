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
	let newData = {};
	let newFields = [...fields];
	let searchFields = [];

	Object.values(fields).forEach(({label, name, type}) => {
		if (type === 'search_location') {
			const labels = parse(label, {
				'address': 'Address',
				'city': 'City',
				'country': 'Country',
				'place': 'Search Location',
				'postal-code': 'Postal Code',
				'state': 'State',
			});

			const visibleFields = Object.keys(labels);

			const searchLocationFieldValues = {};
			const dataSearchLocationFields = {};
			const {values} = data[name];

			const searchLocationFields = visibleFields.map((visibleField) => {
				if (Array.isArray(values)) {
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
				}

				return {
					columns: {},
					label: labels[visibleField],
					name: visibleField,
					options: {},
					parentFieldName: name,
					rows: {},
					type: visibleField,
				};
			});

			newData[name] = dataSearchLocationFields;

			searchFields = searchFields.concat([...searchLocationFields]);
		}
		else {
			newData = data;
			newFields = fields;
		}
	});

	return {
		data: newData,
		fields: searchFields.length
			? newFields
					.filter((field) => field.type !== 'search_location')
					.concat(searchFields)
			: newFields,
	};
};

export {transformSearchLocationValues};
