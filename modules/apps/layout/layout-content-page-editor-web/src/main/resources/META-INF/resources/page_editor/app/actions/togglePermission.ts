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

import {TOGGLE_PERMISSION} from './types';

export type PermissionKey =
	| 'EDIT_SEGMENTS_ENTRY'
	| 'LOCKED_SEGMENTS_EXPERIMENT'
	| 'SWITCH_EDIT_MODE'
	| 'UPDATE'
	| 'UPDATE_LAYOUT_ADVANCED_OPTIONS'
	| 'UPDATE_LAYOUT_BASIC'
	| 'UPDATE_LAYOUT_CONTENT'
	| 'UPDATE_LAYOUT_LIMITED';

export default function togglePermission(
	key: PermissionKey,
	value: boolean | undefined = undefined
) {
	const action: {
		forceNewValue?: boolean;
		key: PermissionKey;
		type: typeof TOGGLE_PERMISSION;
	} = {
		key,
		type: TOGGLE_PERMISSION,
	};

	if (typeof value === 'boolean') {
		action.forceNewValue = value;
	}

	return action;
}
