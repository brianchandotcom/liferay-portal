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

import {sub} from 'frontend-js-web';

export const CONJUNCTIONS = {
	AND: 'and',
	OR: 'or',
} as const;

export const FUNCTIONAL_OPERATORS = {
	CONTAINS: 'contains',
} as const;

export const NOT_OPERATORS = {
	NOT_CONTAINS: 'not-contains',
	NOT_EQ: 'not-eq',
} as const;

export const HAS_OPERATORS = {
	HAS: 'has',
	NOT_HAS: 'has-not',
} as const;

export const RELATIONAL_OPERATORS = {
	EQ: 'eq',
	GE: 'ge',
	GT: 'gt',
	LE: 'le',
	LT: 'lt',
} as const;

export const DATE_OPERATORS = {
	BETWEEN: 'between',
	EVER: 'ever',
} as const;

export const SINCE_VALUES = {
	last7Days: sub(Liferay.Language.get('last-x-days'), 7),
	last24Hours: sub(Liferay.Language.get('last-x-hours'), 24),
	last28Days: sub(Liferay.Language.get('last-x-days'), 28),
	last30Days: sub(Liferay.Language.get('last-x-days'), 30),
	last90Days: sub(Liferay.Language.get('last-x-days'), 90),
	yesterday: Liferay.Language.get('yesterday'),
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
	EVENT: 'event',
	ID: 'id',
	INTEGER: 'integer',
	STRING: 'string',
} as const;

export const PROPERTY_GROUPS = {
	EVENT: 'event',
} as const;

/**
 * Constants for CriteriaBuilder component.
 */

const {AND, OR} = CONJUNCTIONS;
const {EQ, GE, GT, LE, LT} = RELATIONAL_OPERATORS;
const {NOT_CONTAINS, NOT_EQ} = NOT_OPERATORS;
const {HAS, NOT_HAS} = HAS_OPERATORS;
const {CONTAINS} = FUNCTIONAL_OPERATORS;
const {
	BOOLEAN,
	COLLECTION,
	DATE,
	DATE_TIME,
	DOUBLE,
	EVENT,
	ID,
	INTEGER,
	STRING,
} = PROPERTY_TYPES;
const {BETWEEN, EVER} = DATE_OPERATORS;
const {
	last7Days,
	last24Hours,
	last28Days,
	last30Days,
	last90Days,
	yesterday,
} = SINCE_VALUES;

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

export const SUPPORTED_EVENT_OPERATORS = [
	{
		label: Liferay.Language.get('at-least'),
		name: GE,
	},
	{
		label: Liferay.Language.get('at-most'),
		name: LE,
	},
	{
		label: Liferay.Language.get('has'),
		name: HAS,
	},
	{
		label: Liferay.Language.get('has-not'),
		name: NOT_HAS,
	},
] as const;

export const SUPPORTED_EVENT_DATE_OPERATORS = [
	{
		label: Liferay.Language.get('between'),
		name: BETWEEN,
	},
	{
		label: Liferay.Language.get('on'),
		name: EQ,
	},
	{
		label: Liferay.Language.get('ever'),
		name: EVER,
	},
	{
		label: Liferay.Language.get('after'),
		name: GT,
	},
	{
		label: Liferay.Language.get('before'),
		name: LT,
	},
] as const;

export const SUPPORTED_PROPERTY_TYPES = {
	[BOOLEAN]: [EQ, NOT_EQ],
	[COLLECTION]: [EQ, NOT_EQ, CONTAINS, NOT_CONTAINS],
	[DATE]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[DATE_TIME]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[DOUBLE]: [EQ, GE, GT, LE, LT, NOT_EQ],
	[EVENT]: {
		DATE: [BETWEEN, EQ, EVER, GT, LT],
		INTEGER: [GE, LE],
		NOT: [HAS, NOT_HAS],
		SINCE: [
			last7Days,
			last24Hours,
			last28Days,
			last30Days,
			last90Days,
			yesterday,
		],
	},
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
