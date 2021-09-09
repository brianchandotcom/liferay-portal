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

import baseReducer from './baseReducer';
import collectionsReducer from './collectionsReducer';
import defaultFragmentEntryLinksReducer from './defaultFragmentEntryLinksReducer';
import fragmentEntryLinksReducer from './fragmentEntryLinksReducer';
import fragmentsReducer from './fragmentsReducer';
import languageIdReducer from './languageIdReducer';
import layoutDataReducer from './layoutDataReducer';
import mappingFieldsReducer from './mappingFieldsReducer';
import masterLayoutReducer from './masterLayoutReducer';
import networkReducer from './networkReducer';
import pageContentsReducer from './pageContentsReducer';
import permissionsReducer from './permissionsReducer';
import selectedViewportSizeReducer from './selectedViewportSizeReducer';
import showResolvedCommentsReducer from './showResolvedCommentsReducer';
import sidebarReducer from './sidebarReducer';
import undoReducer from './undoReducer';

const combinedReducer = (state, action) =>
	Object.entries({
		collections: collectionsReducer,
		fragmentEntryLinks: fragmentEntryLinksReducer,
		fragments: fragmentsReducer,
		languageId: languageIdReducer,
		layoutData: layoutDataReducer,
		mappingFields: mappingFieldsReducer,
		masterLayout: masterLayoutReducer,
		network: networkReducer,
		pageContents: pageContentsReducer,
		permissions: permissionsReducer,
		reducers: baseReducer,
		selectedViewportSize: selectedViewportSizeReducer,
		showResolvedComments: showResolvedCommentsReducer,
		sidebar: sidebarReducer,
	}).reduce(
		(nextState, [namespace, reducer]) => ({
			...nextState,
			[namespace]: reducer(nextState[namespace], action),
		}),
		state
	);

/**
 * Runs the base reducer plus any dynamically loaded reducers that have
 * been registered from plugins.
 */
export function reducer(state, action) {
	let nextState = undoReducer(state, action);
	nextState = defaultFragmentEntryLinksReducer(nextState, action);

	return [combinedReducer, ...Object.values(state.reducers || {})].reduce(
		(nextState, nextReducer) => {
			return nextReducer(nextState, action);
		},
		nextState
	);
}
