/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Constants for OData query.
 */
export declare const CONJUNCTIONS: {
	readonly AND: 'and';
	readonly OR: 'or';
};
export declare type Conjunction = typeof CONJUNCTIONS[keyof typeof CONJUNCTIONS];
export declare const FUNCTIONAL_OPERATORS: {
	readonly CONTAINS: 'contains';
};
export declare const NOT_OPERATORS: {
	readonly NOT_CONTAINS: 'not-contains';
	readonly NOT_EQ: 'not-eq';
};
export declare const RELATIONAL_OPERATORS: {
	readonly EQ: 'eq';
	readonly GE: 'ge';
	readonly GT: 'gt';
	readonly LE: 'le';
	readonly LT: 'lt';
};

/**
 * Constants to match property types in the passed in supportedProperties array.
 */
export declare const PROPERTY_TYPES: {
	readonly BOOLEAN: 'boolean';
	readonly COLLECTION: 'collection';
	readonly DATE: 'date';
	readonly DATE_TIME: 'date-time';
	readonly DOUBLE: 'double';
	readonly ID: 'id';
	readonly INTEGER: 'integer';
	readonly STRING: 'string';
};
export declare type PropertyType = typeof PROPERTY_TYPES[keyof typeof PROPERTY_TYPES];
export declare const SUPPORTED_CONJUNCTIONS: readonly [
	{
		readonly label: string;
		readonly name: 'and';
	},
	{
		readonly label: string;
		readonly name: 'or';
	}
];
export declare const SUPPORTED_OPERATORS: readonly [
	{
		readonly label: string;
		readonly name: 'eq';
	},
	{
		readonly label: string;
		readonly name: 'not-eq';
	},
	{
		readonly label: string;
		readonly name: 'gt';
	},
	{
		readonly label: string;
		readonly name: 'ge';
	},
	{
		readonly label: string;
		readonly name: 'lt';
	},
	{
		readonly label: string;
		readonly name: 'le';
	},
	{
		readonly label: string;
		readonly name: 'contains';
	},
	{
		readonly label: string;
		readonly name: 'not-contains';
	}
];
export declare const SUPPORTED_PROPERTY_TYPES: {
	readonly 'boolean': readonly ['eq', 'not-eq'];
	readonly 'collection': readonly [
		'eq',
		'not-eq',
		'contains',
		'not-contains'
	];
	readonly 'date': readonly ['eq', 'ge', 'gt', 'le', 'lt', 'not-eq'];
	readonly 'date-time': readonly ['eq', 'ge', 'gt', 'le', 'lt', 'not-eq'];
	readonly 'double': readonly ['eq', 'ge', 'gt', 'le', 'lt', 'not-eq'];
	readonly 'id': readonly ['eq', 'not-eq'];
	readonly 'integer': readonly ['eq', 'ge', 'gt', 'le', 'lt', 'not-eq'];
	readonly 'string': readonly ['eq', 'not-eq', 'contains', 'not-contains'];
};

/**
 * Values for criteria row inputs.
 */
export declare const BOOLEAN_OPTIONS: readonly [
	{
		readonly label: string;
		readonly value: 'true';
	},
	{
		readonly label: string;
		readonly value: 'false';
	}
];
