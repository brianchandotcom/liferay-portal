/**
 * Constants for OData query.
 */

export const CONJUNCTIONS = {
	AND: 'and',
	OR: 'or'
};

export const FUNCTIONAL_OPERATORS = {
	CONTAINS: 'contains'
};

export const GROUP = 'GROUP';

export const RELATIONAL_OPERATORS = {
	EQ: 'eq',
	GE: 'ge',
	GT: 'gt',
	LE: 'le',
	LT: 'lt',
	NE: 'ne'
};

/**
 * Constants to match property types in the passed in supportedProperties array.
 */

export const PROPERTY_TYPES = {
	BOOLEAN: 'boolean',
	DATE: 'date',
	NUMBER: 'number',
	STRING: 'string'
};

/**
 * Constants for CriteriaBuilder component.
 */

const {AND, OR} = CONJUNCTIONS;
const {EQ, GE, GT, LE, LT, NE} = RELATIONAL_OPERATORS;
const {BOOLEAN, DATE, NUMBER, STRING} = PROPERTY_TYPES;

export const SUPPORTED_CONJUNCTIONS = [
	{
		label: Liferay.Language.get('and'),
		name: AND
	},
	{
		label: Liferay.Language.get('or'),
		name: OR
	}
];

export const SUPPORTED_OPERATORS = [
	{
		label: Liferay.Language.get('equals'),
		name: EQ
	},
	{
		label: Liferay.Language.get('greater-than-or-equals'),
		name: GE
	},
	{
		label: Liferay.Language.get('greater-than'),
		name: GT
	},
	{
		label: Liferay.Language.get('less-than-or-equals'),
		name: LE
	},
	{
		label: Liferay.Language.get('less-than'),
		name: LT
	},
	{
		label: Liferay.Language.get('not-equals'),
		name: NE
	}
];

export const SUPPORTED_PROPERTY_TYPES = {
	[BOOLEAN]: [EQ, NE],
	[DATE]: [EQ, GE, GT, LE, LT, NE],
	[NUMBER]: [EQ, GE, GT, LE, LT, NE],
	[STRING]: [EQ, NE]
};