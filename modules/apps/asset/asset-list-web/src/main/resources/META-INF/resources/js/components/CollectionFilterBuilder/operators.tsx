/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {GenericOperator, GenericProperty} from './condition_builder/types';

// Booleans use an implicit `eq` operator (auto-set when the field is selected
// in ConditionBuilder) and render their value as a single Is True / Is False
// picker via DefaultValueInput. No explicit operator picker is needed.

const BOOLEAN_OPERATORS: GenericOperator[] = [];

const COMPARISON_OPERATORS: GenericOperator[] = [
	{label: Liferay.Language.get('equals'), value: 'eq'},
	{label: Liferay.Language.get('not-equals'), value: 'not-eq'},
	{label: Liferay.Language.get('greater-than'), value: 'gt'},
	{label: Liferay.Language.get('greater-than-or-equals'), value: 'ge'},
	{label: Liferay.Language.get('less-than'), value: 'lt'},
	{label: Liferay.Language.get('less-than-or-equals'), value: 'le'},
];

const DEFAULT_OPERATORS: GenericOperator[] = [
	{label: Liferay.Language.get('contains'), value: 'contains'},
	{label: Liferay.Language.get('does-not-contain'), value: 'not-contains'},
];

export function getCollectionOperators(
	property: GenericProperty
): GenericOperator[] {
	switch (property.type) {
		case 'boolean':
			return BOOLEAN_OPERATORS;
		case 'date':
		case 'date-time':
		case 'double':
		case 'integer':
			return COMPARISON_OPERATORS;
		case 'picklist':
		case 'string':
		default:
			return DEFAULT_OPERATORS;
	}
}
