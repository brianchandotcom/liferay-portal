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

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const INITIAL_STATE = {
	/**
	 * A collection of dynamically loaded reducers that may be loaded from
	 * plugins at runtime.
	 *
	 * TODO: potentially allow us to specify a ranking here to determine order
	 */
	draft: true,
	network: {
		error: null,
		lastFetch: null,
		status: SERVICE_NETWORK_STATUS_TYPES.Idle
	},
	reducers: {},
	sidebarOpen: true,
	singleSegmentsExperienceMode: false
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
export function getInitialState([data, config]) {
	const state = {
		...transformServerData(data, config)
	};

	// Exclude keys that were partitioned off into config.
	Object.keys(config).forEach(key => {
		delete state[key];
	});

	return {
		...INITIAL_STATE,
		...state
	};
}

function transformServerData(data, config) {
	const {panels} = config;

	// By default, show first panel of first section.
	const sidebarPanelId = panels[0] && panels[0][0];

	return {
		...data,
		layoutData: data.layoutData,
		sidebarPanelId
	};
}
