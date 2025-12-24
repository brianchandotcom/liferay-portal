/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
import {Condition} from '../../types/Rule';

export function translateConditionsToScript(
	conditions: Condition[],
	conditionType: ConditionType
) {
	const conditionScript = conditions.map((condition) => {
		if (condition.type === 'field') {
			let script = `${condition.field}`;

			if (condition.options?.type === 'equal') {
				script += ` == "${condition.options?.value || ''}"`;
			}
			else {
				script += ` != "${condition.options?.value || ''}"`;
			}

			return script;
		}
		else if (condition.type === 'form') {
			let script = `input__${condition.field?.replaceAll('-', '_')}`;

			if (condition.options?.type === 'equal') {
				script += ` == "${condition.options?.value || ''}"`;
			}
			else {
				script += ` != "${condition.options?.value || ''}"`;
			}

			return script;
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
