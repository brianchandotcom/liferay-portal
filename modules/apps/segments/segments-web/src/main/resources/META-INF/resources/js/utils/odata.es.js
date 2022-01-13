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

import {
	FUNCTIONAL_OPERATORS,
	NOT_OPERATORS,
	PROPERTY_TYPES,
	RELATIONAL_OPERATORS,
} from './constants.es';

/**
 * Gets the type of the property from the property name.
 * @param {string} propertyName The property name to find.
 * @param {array} properties The list of defined properties to search in.
 * @returns {string} The property type.
 */
const getTypeByPropertyName = (propertyName, properties) => {
	let type = null;

	if (propertyName && properties) {
		const property = properties.find(
			(property) => property.name === propertyName
		);

		type = property ? property.type : null;
	}

	return type;
};

/**
 * Decides whether to add quotes to value.
 * @param {boolean | string} value
 * @param {boolean | date | number | string} type
 * @returns {string}
 */
function valueParser(value, type) {
	let parsedValue;

	switch (type) {
		case PROPERTY_TYPES.BOOLEAN:
		case PROPERTY_TYPES.DATE:
		case PROPERTY_TYPES.DATE_TIME:
		case PROPERTY_TYPES.INTEGER:
		case PROPERTY_TYPES.DOUBLE:
			parsedValue = value;
			break;
		case PROPERTY_TYPES.COLLECTION:
		case PROPERTY_TYPES.STRING:
		default:
			parsedValue = `'${value}'`;
			break;
	}

	return parsedValue;
}

/**
 * Recursively traverses the criteria object to build an oData filter query
 * string. Properties is required to parse the correctly with or without quotes
 * and formatting the query differently for certain types like collection.
 * @param {object} criteria The criteria object.
 * @param {string} queryConjunction The conjunction name value to be used in the
 * query.
 * @param {array} properties The list of property objects. See
 * ContributorBuilder for valid property object shape.
 * @returns An OData query string built from the criteria object.
 */
function buildQueryString(criteria, queryConjunction, properties) {
	return criteria.filter(Boolean).reduce((queryString, criterion, index) => {
		const {
			conjunctionName,
			items,
			operatorName,
			propertyName,
			value,
		} = criterion;

		if (index > 0) {
			queryString = queryString.concat(` ${queryConjunction} `);
		}

		if (conjunctionName) {
			queryString = queryString.concat(
				`(${buildQueryString(items, conjunctionName, properties)})`
			);
		}
		else {
			const type =
				criterion.type ||
				getTypeByPropertyName(propertyName, properties);

			const parsedValue = valueParser(value, type);

			if (isValueType(RELATIONAL_OPERATORS, operatorName)) {
				if (type === PROPERTY_TYPES.COLLECTION) {
					queryString = queryString.concat(
						`${propertyName}/any(c:c ${operatorName} ${parsedValue})`
					);
				}
				else {
					queryString = queryString.concat(
						`${propertyName} ${operatorName} ${parsedValue}`
					);
				}
			}
			else if (isValueType(FUNCTIONAL_OPERATORS, operatorName)) {
				if (type === PROPERTY_TYPES.COLLECTION) {
					queryString = queryString.concat(
						`${propertyName}/any(c:${operatorName}(c, ${parsedValue}))`
					);
				}
				else {
					queryString = queryString.concat(
						`${operatorName}(${propertyName}, ${parsedValue})`
					);
				}
			}
			else if (isValueType(NOT_OPERATORS, operatorName)) {
				const baseOperator = operatorName.replace(/not-/g, '');

				const baseExpression = [
					{
						operatorName: baseOperator,
						propertyName,
						type,
						value,
					},
				];

				// Not is wrapped in a group to simplify AST parsing.

				queryString = queryString.concat(
					`(not (${buildQueryString(
						baseExpression,
						conjunctionName,
						properties
					)}))`
				);
			}
		}

		return queryString;
	}, '');
}

/**
 * Checks if the value is a certain type.
 * @param {object} types A map of supported types.
 * @param {*} value The value to validate.
 * @returns {boolean}
 */
function isValueType(types, value) {
	return Object.values(types).includes(value);
}

export {buildQueryString};
