/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const COMPARISON_OPERATORS = [
	{label: Liferay.Language.get('equals'), value: 'eq'},
	{label: Liferay.Language.get('not-equals'), value: 'not-eq'},
	{label: Liferay.Language.get('greater-than'), value: 'gt'},
	{label: Liferay.Language.get('greater-than-or-equals'), value: 'ge'},
	{label: Liferay.Language.get('less-than'), value: 'lt'},
	{label: Liferay.Language.get('less-than-or-equals'), value: 'le'},
];

const DATE_OPERATORS = [
	{label: Liferay.Language.get('equals'), value: 'eq'},
	{label: Liferay.Language.get('not-equals'), value: 'not-eq'},
	{label: Liferay.Language.get('after'), value: 'gt'},
	{label: Liferay.Language.get('on-or-after'), value: 'ge'},
	{label: Liferay.Language.get('before'), value: 'lt'},
	{label: Liferay.Language.get('on-or-before'), value: 'le'},
];

const BOOLEAN_OPERATORS = [
	{label: Liferay.Language.get('is'), value: 'eq'},
	{label: Liferay.Language.get('is-not'), value: 'not-eq'},
];

const PICKLIST_OPERATORS = [
	{label: Liferay.Language.get('includes'), value: 'includes'},
	{label: Liferay.Language.get('excludes'), value: 'excludes'},
];

const STRING_OPERATORS = [
	{label: Liferay.Language.get('equals'), value: 'eq'},
	{label: Liferay.Language.get('not-equals'), value: 'not-eq'},
	{label: Liferay.Language.get('contains'), value: 'contains'},
	{label: Liferay.Language.get('does-not-contain'), value: 'not-contains'},
];

const ID_OPERATORS = [
	{label: Liferay.Language.get('equals'), value: 'eq'},
	{label: Liferay.Language.get('not-equals'), value: 'not-eq'},
];

export function getCollectionOperators(property) {
	switch (property.type) {
		case 'boolean':
			return BOOLEAN_OPERATORS;
		case 'date':
		case 'date-time':
			return DATE_OPERATORS;
		case 'double':
		case 'integer':
			return COMPARISON_OPERATORS;
		case 'picklist':
			return PICKLIST_OPERATORS;
		case 'id':
			return ID_OPERATORS;
		case 'string':
		default:
			return STRING_OPERATORS;
	}
}
