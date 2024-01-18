/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import loadReducer, {Reducer} from '../actions/loadReducer';
import unloadReducer from '../actions/unloadReducer';
export declare const INITIAL_STATE: Record<string, Reducer<any, any>>;
export default function baseReducer(
	reducers: Record<string, Reducer<any, any>> | undefined,
	action: ReturnType<typeof loadReducer> | ReturnType<typeof unloadReducer>
): {
	[x: string]:
		| Reducer<any, any>
		| Reducer<unknown, import('../actions/loadReducer').BaseAction>;
};
