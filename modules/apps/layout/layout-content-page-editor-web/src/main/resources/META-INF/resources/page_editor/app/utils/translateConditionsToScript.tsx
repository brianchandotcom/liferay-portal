/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
import {Condition} from '../../types/Rule';

export function translateConditionsToScript(
	conditions: Condition[],
	conditionType: ConditionType,
	fieldTypes: Record<string, string> = {}
) {
	const conditionScript = conditions.map((condition) => {
		if (condition.type === 'field') {
			return _toFieldComparison(
				condition.field || '',
				condition.options?.type,
				condition.options?.value || '',
				fieldTypes[condition.field || '']
			);
		}
		else if (condition.type === 'form') {
			return _toFieldComparison(
				`input__${condition.field?.replaceAll('-', '_')}`,
				condition.options?.type,
				condition.options?.value || ''
			);
		}
		else if (condition.type === 'user') {
			if (condition.field === 'role') {
				if (condition.options?.type === 'equal') {
					return `contains(roleIds, ${condition.options.value || ''})`;
				}
				else {
					return `NOT(contains(roleIds, ${condition.options?.value || ''}))`;
				}
			}
			else if (condition.field === 'segment') {
				if (condition.options?.type === 'equal') {
					return `contains(segmentsEntryIds, ${condition.options.value || ''})`;
				}
				else {
					return `NOT(contains(segmentsEntryIds, ${condition.options?.value || ''}))`;
				}
			}
			else {
				return `userId == ${condition.options?.value}`;
			}
		}
	});

	if (!conditionScript.length) {
		return '';
	}

	if (conditionType === 'all') {
		return conditionScript.join(' AND ');
	}
	else {
		return conditionScript.join(' OR ');
	}
}

function _toFieldComparison(
	field: string,
	type: NonNullable<Condition['options']>['type'] | undefined,
	value: string,
	fieldType?: string
) {
	if (type === 'is-empty') {
		return `isEmpty(${field})`;
	}

	if (type === 'is-not-empty') {
		return `NOT(isEmpty(${field}))`;
	}

	if (type === 'contains') {
		return `contains(${field}, "${value}")`;
	}

	if (type === 'does-not-contain') {
		return `NOT(contains(${field}, "${value}"))`;
	}

	if (fieldType === 'number') {
		const operator = _toNumericOperator(type);

		return `${field} ${operator} ${value || '0'}`;
	}

	if (type === 'greater-than') {
		return `(futureDates(${field}, "${value}") AND ${field} != "${value}")`;
	}

	if (type === 'greater-than-or-equals') {
		return `futureDates(${field}, "${value}")`;
	}

	if (type === 'less-than') {
		return `(pastDates(${field}, "${value}") AND ${field} != "${value}")`;
	}

	if (type === 'less-than-or-equals') {
		return `pastDates(${field}, "${value}")`;
	}

	const operator = type === 'not-equal' ? '!=' : '==';

	return `${field} ${operator} "${value}"`;
}

function _toNumericOperator(
	type: NonNullable<Condition['options']>['type'] | undefined
) {
	switch (type) {
		case 'greater-than':
			return '>';
		case 'greater-than-or-equals':
			return '>=';
		case 'less-than':
			return '<';
		case 'less-than-or-equals':
			return '<=';
		case 'not-equal':
			return '!=';
		default:
			return '==';
	}
}
