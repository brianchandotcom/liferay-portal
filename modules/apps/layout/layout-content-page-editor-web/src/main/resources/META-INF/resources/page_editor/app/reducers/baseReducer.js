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

import {TYPES} from '../actions/index';

export default function baseReducer(state, action) {
	let nextState = state;

	switch (action.type) {
		case TYPES.DISCARD:
			break;

		case TYPES.LOAD_REDUCER:
			{
				const {key, reducer: reducerToAdd} = action;

				nextState = {
					...state,
					reducers: {
						...state.reducers,
						[key]: reducerToAdd
					}
				};
			}
			break;

		case TYPES.PUBLISH:
			break;

		case TYPES.UNLOAD_REDUCER:
			{
				const {key} = action;

				// eslint-disable-next-line no-unused-vars
				const {[key]: reducerToRemove, ...reducers} = state.reducers;

				nextState = {
					...state,
					reducers
				};
			}
			break;

		default:
			break;
	}

	return nextState;
}
