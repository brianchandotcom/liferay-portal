/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes';

export default function rulesReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.RULE.ADD: {
			const {rules} = state;

			return {
				rules: [...rules, action.payload],
			};
		}
		case EVENT_TYPES.RULE.CHANGE: {
			const {rules} = state;
			const {loc, rule} = action.payload;

			rules.splice(loc, 1, rule);

			return {rules};
		}
		case EVENT_TYPES.RULE.DELETE: {
			const {rules} = state;

			return {
				rules: rules.filter((rule, index) => index !== action.payload),
			};
		}
		default:
			return state;
	}
}
