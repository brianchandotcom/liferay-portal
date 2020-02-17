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
