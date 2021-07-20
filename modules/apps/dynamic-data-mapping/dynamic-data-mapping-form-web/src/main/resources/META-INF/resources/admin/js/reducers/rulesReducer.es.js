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

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action) => {
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
};
