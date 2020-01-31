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

import {TOGGLE_PERMISSION} from '../actions/types';

/**
 * @type {import('../../types/ActionKeys').ActionKeysMap}
 */
const INITIAL_STATE = {
	UNLOCKED_SEGMENTS_EXPERIENCE: true,
	UPDATE: true,
	UPDATE_CONTENT: true
};

/**
 *
 * @param {import('../../types/ActionKeys').ActionKeysMap} state
 * @param {object} action
 * @param {string} action.type
 * @param {import('../../types/ActionKeys').ACTION_KEYS} action.key
 * @param {boolean} [action.forceNewValue]
 */
export default function permissionsReducer(state = INITIAL_STATE, action) {
	if (action.type === TOGGLE_PERMISSION) {
		return {
			...state,
			[action.key]:
				typeof action.forceNewValue === 'undefined'
					? !state[action.key]
					: action.forceNewValue
		};
	}

	return state;
}
