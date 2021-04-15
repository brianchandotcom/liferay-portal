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

import {EVENT_TYPES} from '../eventTypes';

export default (state, action) => {
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
};
