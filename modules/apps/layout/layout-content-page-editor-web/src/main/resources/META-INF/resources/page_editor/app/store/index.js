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

const INITIAL_STATE = {
	/**
	 * Example state value.
	 */
	example: 'value'
};

export const StoreContext = React.createContext(INITIAL_STATE);

/**
 * Prepares the initial contents of the store based on server data.
 *
 * Data that goes in the store should be of "global" interest across the
 * app. Actions (user interactions, network activity etc) update that state, and
 * any interested component can obtain updates via the `StoreContext`.
 *
 * This is in contrast to immutable config, managed in the neigboring
 * config directory. Immutable config may be of interest across the app,
 * but it does not change in response to actions or events; it remains
 * static over the lifetime of the app.
 *
 * State which is not of "global" interest should go in component-local state
 * (see the `useState` hook) or a in more localized context objects that apply
 * to specific subtrees.
 */
export function getInitialState(data = {}) {
	// TODO: Transform/normalize.
	const state = {
		data
	};

	return {
		...INITIAL_STATE,
		...state
	};
}
