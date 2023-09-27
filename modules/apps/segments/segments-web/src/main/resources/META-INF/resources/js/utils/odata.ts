/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem, Property} from '../../types/Criteria';
import {
	Conjunction,
	FUNCTIONAL_OPERATORS,
	NOT_OPERATORS,
	PROPERTY_TYPES,
	PropertyType,
	RELATIONAL_OPERATORS,
} from './constants';

/**
 * Gets the type of the property from the property name.
 */
const getTypeByPropertyName = (
	propertyName: string,
	properties: Array<{name: string; type: PropertyType}>
) => {
	let type: PropertyType | undefined;

	if (propertyName && properties) {
		const property = properties.find(
			(property) => property.name === propertyName
		);

		if (property) {
			type = property.type;
		}
	}

	return type;
};

/**
 * Handles the single quotes present in the value.
 * Should escape the single quotes present in the value before hit the backend.
 */
function escapeSingleQuotes<T>(value: T) {
	if (typeof value !== 'string') {
		return value;
	}

	return `'${value.replace(/'/g, "''")}'`;
}

/**
 * Handles the single quotes present in the value.
 * Should un-escape the single quotes present in odata format before rendering.
 */
function unescapeSingleQuotes<T>(value: T) {
	if (typeof value !== 'string') {
		return value;
	}

	return value.replace(/''/g, "'");
}

/**
 * Decides whether to add quotes to value.
 */
function valueParser(value: unknown, type: PropertyType | undefined) {
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
			parsedValue = escapeSingleQuotes(value);
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
function buildQueryString(
	criteria: Array<Criteria | CriteriaItem | null>,
	queryConjunction: Conjunction,
	properties: Property[]
) {
	return (criteria.filter(Boolean) as Array<Criteria | CriteriaItem>).reduce(
		(queryString, criteriaOrCriteriaItem, index) => {
			if (index > 0) {
				queryString = queryString.concat(` ${queryConjunction} `);
			}

			if ('conjunctionName' in criteriaOrCriteriaItem) {
				queryString = queryString.concat(
					`(${buildQueryString(
						criteriaOrCriteriaItem.items,
						criteriaOrCriteriaItem.conjunctionName,
						properties
					)})`
				);
			}
			else {
				const {
					operatorName,
					propertyName,
					value,
				} = criteriaOrCriteriaItem;

				const type =
					criteriaOrCriteriaItem.type ||
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

					const baseExpression: CriteriaItem[] = [
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
							queryConjunction,
							properties
						)}))`
					);
				}
			}

			return queryString;
		},
		''
	);
}

/**
 * Checks if the value is a certain type.
 */
function isValueType<Map extends Record<K, V>, K extends string, V>(
	types: Map,
	value: unknown
): boolean {
	return Object.values(types).includes(value);
}

export {buildQueryString, unescapeSingleQuotes};
