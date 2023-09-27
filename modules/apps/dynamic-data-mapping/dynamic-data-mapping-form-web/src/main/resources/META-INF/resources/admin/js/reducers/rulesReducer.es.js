/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default function rulesReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.RULE.ADD: {
			const {rules} = state;

			return {
				currentRuleLoc: null,
				rules: [...rules, action.payload],
			};
		}
		case EVENT_TYPES.RULE.CHANGE: {
			const {
				loc,
				rule: {actions, conditions, logicalOperator, ...otherRule},
			} = action.payload;
			const {rules} = state;

			rules.splice(loc, 1, {
				actions,
				conditions,

				// Compatibility with the Forms backend

				'logical-operator': otherRule['logical-operator']
					? otherRule['logical-operator']
					: logicalOperator,
			});

			return {
				currentRuleLoc: null,
				rules,
			};
		}
		case EVENT_TYPES.RULE.DELETE: {
			const {rules} = state;

			return {
				rules: rules.filter((rule, index) => index !== action.payload),
			};
		}
		case EVENT_TYPES.RULE.EDIT: {
			const {loc} = action.payload;

			return {
				currentRuleLoc: loc,
			};
		}
		default:
			return state;
	}
}
