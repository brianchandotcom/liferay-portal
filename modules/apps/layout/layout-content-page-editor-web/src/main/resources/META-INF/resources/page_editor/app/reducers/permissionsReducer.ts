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

import togglePermission from '../actions/togglePermission';
import {INIT, TOGGLE_PERMISSION} from '../actions/types';

import type {PermissionKey} from '../actions/togglePermission';

export type PermissionsState = Record<PermissionKey, boolean | undefined>;

export const INITIAL_STATE: PermissionsState = {
	EDIT_SEGMENTS_ENTRY: false,
	LOCKED_SEGMENTS_EXPERIMENT: false,
	SWITCH_EDIT_MODE: true,
	UPDATE: true,
	UPDATE_LAYOUT_ADVANCED_OPTIONS: undefined,
	UPDATE_LAYOUT_BASIC: undefined,
	UPDATE_LAYOUT_CONTENT: true,
	UPDATE_LAYOUT_LIMITED: undefined,
};

export default function permissionsReducer(
	state: PermissionsState = INITIAL_STATE,
	action: ReturnType<typeof togglePermission> | {type: typeof INIT}
): PermissionsState {
	switch (action.type) {
		case TOGGLE_PERMISSION:
			return {
				...state,
				[action.key]:
					typeof action.forceNewValue === 'undefined'
						? !state[action.key]
						: action.forceNewValue,
			};
		case INIT:
			return {
				...state,
				SWITCH_EDIT_MODE:
					state.UPDATE ||
					state.UPDATE_LAYOUT_BASIC ||
					state.UPDATE_LAYOUT_LIMITED,
			};

		default:
			return state;
	}
}
