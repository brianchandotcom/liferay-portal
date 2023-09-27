/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import togglePermission from '../actions/togglePermission';
import {INIT} from '../actions/types';
import type {PermissionKey} from '../actions/togglePermission';
export declare type PermissionsState = Record<
	PermissionKey,
	boolean | undefined
>;
export declare const INITIAL_STATE: PermissionsState;
export default function permissionsReducer(
	state: PermissionsState | undefined,
	action:
		| ReturnType<typeof togglePermission>
		| {
				type: typeof INIT;
		  }
): PermissionsState;
