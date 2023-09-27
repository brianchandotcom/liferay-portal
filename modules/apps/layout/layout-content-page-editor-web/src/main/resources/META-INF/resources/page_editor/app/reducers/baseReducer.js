/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LOAD_REDUCER, UNLOAD_REDUCER} from '../actions/types';

export const INITIAL_STATE = {};

export default function baseReducer(reducers = INITIAL_STATE, action) {
	switch (action.type) {
		case LOAD_REDUCER:
			return {...reducers, [action.key]: action.reducer};

		case UNLOAD_REDUCER: {
			const nextReducers = {...reducers};
			delete nextReducers[action.key];

			return nextReducers;
		}

		default:
			return reducers;
	}
}
