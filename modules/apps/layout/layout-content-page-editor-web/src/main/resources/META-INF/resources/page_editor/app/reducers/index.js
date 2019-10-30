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

import React from 'react';

import baseReducer from './baseReducer';
import layoutDataReducer from './layoutDataReducer';

export const DispatchContext = React.createContext(() => {});

/**
 * Runs the base reducer plus any dynamically loaded reducers that have
 * been registered from plugins.
 */
export function reducer(state, action) {
	return [
		baseReducer,
		layoutDataReducer,
		...Object.values(state.reducers)
	].reduce((nextState, nextReducer) => {
		return nextReducer(nextState, action);
	}, state);
}
