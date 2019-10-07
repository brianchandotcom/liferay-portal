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
export function getInitialState([data, config]) {
	const state = {
		...transformServerData(data)
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

/**
 * In general, we expect the sidebarPanelId to correspond with the name
 * of a plugin. Here we deal with the exceptions by mapping IDs to
 * plugin names.
 */
const SIDEBAR_PANEL_IDS_TO_PLUGINS = {
	elements: 'section-builder',

	lookAndFeel: 'look-and-feel',

	/**
	 * Not a real plugin, just a visual separator.
	 */
	separator: null
};

/**
 * Until we decompose the layout-content-page-editor module into a
 * set of smaller OSGi plugins, we "fake" it here to show how we would
 * lazily-load components from elsewhere by injecting an additional
 * "pluginEntryPoint" property.
 *
 * Note that in the final version we'll be using NPMResolver to obtain these
 * paths dynamically.
 *
 * @see transformServerData() below.
 */
const PLUGIN_ROOT = 'layout-content-page-editor-web@2.0.0/page_editor/plugins';

function transformServerData(data) {
	return {
		...data,

		sidebarPanels: data.sidebarPanels.map(panel => {
			const mapping = SIDEBAR_PANEL_IDS_TO_PLUGINS[panel.sidebarPanelId];

			if (mapping === null) {
				return panel;
			}

			const sidebarPanelId = mapping || panel.sidebarPanelId;

			return {
				...panel,

				// https://github.com/liferay/liferay-js-toolkit/issues/324
				pluginEntryPoint: `${PLUGIN_ROOT}/${sidebarPanelId}/index`,

				sidebarPanelId
			};
		})
	};
}
