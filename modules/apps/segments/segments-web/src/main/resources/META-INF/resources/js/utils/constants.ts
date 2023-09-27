/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Constants for OData query.
 */

export const CONJUNCTIONS = {
	AND: 'and',
	OR: 'or',
} as const;

export type Conjunction = typeof CONJUNCTIONS[keyof typeof CONJUNCTIONS];

export const FUNCTIONAL_OPERATORS = {
	CONTAINS: 'contains',
} as const;

export const NOT_OPERATORS = {
	NOT_CONTAINS: 'not-contains',
	NOT_EQ: 'not-eq',
} as const;

export const RELATIONAL_OPERATORS = {
	EQ: 'eq',
	GE: 'ge',
	GT: 'gt',
	LE: 'le',
	LT: 'lt',
} as const;

/**
 * Constants to match property types in the passed in supportedProperties array.
 */

export const PROPERTY_TYPES = {
	BOOLEAN: 'boolean',
	COLLECTION: 'collection',
	DATE: 'date',
	DATE_TIME: 'date-time',
	DOUBLE: 'double',
	ID: 'id',
	INTEGER: 'integer',
	STRING: 'string',
} as const;

export type PropertyType = typeof PROPERTY_TYPES[keyof typeof PROPERTY_TYPES];

/**
 * Constants for CriteriaBuilder component.
 */

const {AND, OR} = CONJUNCTIONS;
const {EQ, GE, GT, LE, LT} = RELATIONAL_OPERATORS;
const {NOT_CONTAINS, NOT_EQ} = NOT_OPERATORS;
const {CONTAINS} = FUNCTIONAL_OPERATORS;
const {
	BOOLEAN,
	COLLECTION,
	DATE,
	DATE_TIME,
	DOUBLE,
	ID,
	INTEGER,
	STRING,
} = PROPERTY_TYPES;

export const SUPPORTED_CONJUNCTIONS = [
	{
		label: Liferay.Language.get('and'),
		name: AND,
	},
	{
		label: Liferay.Language.get('or'),
		name: OR,
	},
] as const;

export const SUPPORTED_OPERATORS = [
	{
		label: Liferay.Language.get('equals'),
		name: EQ,
	},
	{
		label: Liferay.Language.get('not-equals'),
		name: NOT_EQ,
	},
	{
		label: Liferay.Language.get('greater-than'),
		name: GT,
	},
	{
		label: Liferay.Language.get('greater-than-or-equals'),
		name: GE,
	},
	{
		label: Liferay.Language.get('less-than'),
		name: LT,
	},
	{
		label: Liferay.Language.get('less-than-or-equals'),
		name: LE,
	},
	{
		label: Liferay.Language.get('contains'),
		name: CONTAINS,
	},
	{
		label: Liferay.Language.get('not-contains'),
		name: NOT_CONTAINS,
	},
] as const;

export const SUPPORTED_PROPERTY_TYPES = {
	[BOOLEAN]: [EQ, NOT_EQ],
	[COLLECTION]: [EQ, NOT_EQ, CONTAINS, NOT_CONTAINS],
	[DATE]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[DATE_TIME]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[DOUBLE]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[ID]: [EQ, NOT_EQ],
	[INTEGER]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[STRING]: [EQ, NOT_EQ, CONTAINS, NOT_CONTAINS],
} as const;

/**
 * Values for criteria row inputs.
 */

export const BOOLEAN_OPTIONS = [
	{
		label: Liferay.Language.get('true'),
		value: 'true',
	},
	{
		label: Liferay.Language.get('false'),
		value: 'false',
	},
] as const;
