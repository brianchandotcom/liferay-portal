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
