/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import baseReducer from './baseReducer';
import collectionsReducer from './collectionsReducer';
import draftReducer from './draftReducer';
import fragmentEntryLinksReducer from './fragmentEntryLinksReducer';
import fragmentsReducer from './fragmentsReducer';
import languageReducer from './languageReducer';
import layoutDataReducer from './layoutDataReducer';
import mappingFieldsReducer from './mappingFieldsReducer';
import masterLayoutReducer from './masterLayoutReducer';
import networkReducer from './networkReducer';
import pageContentsReducer from './pageContentsReducer';
import permissionsReducer from './permissionsReducer';
import restrictedItemIdsReducer from './restrictedItemIdsReducer';
import selectedViewportSizeReducer from './selectedViewportSizeReducer';
import showResolvedCommentsReducer from './showResolvedCommentsReducer';
import sidebarReducer from './sidebarReducer';
import undoReducer from './undoReducer';
import widgetsReducer from './widgetsReducer';

const combinedReducer = (state, action) =>
	Object.entries({
		collections: collectionsReducer,
		draft: draftReducer,
		fragmentEntryLinks: fragmentEntryLinksReducer,
		fragments: fragmentsReducer,
		languageId: languageReducer,
		layoutData: layoutDataReducer,
		mappingFields: mappingFieldsReducer,
		masterLayout: masterLayoutReducer,
		network: networkReducer,
		pageContents: pageContentsReducer,
		permissions: permissionsReducer,
		reducers: baseReducer,
		restrictedItemIds: restrictedItemIdsReducer,
		selectedViewportSize: selectedViewportSizeReducer,
		showResolvedComments: showResolvedCommentsReducer,
		sidebar: sidebarReducer,
		widgets: widgetsReducer,
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
	const nextState = undoReducer(state, action);

	return [combinedReducer, ...Object.values(state.reducers || {})].reduce(
		(nextState, nextReducer) => {
			return nextReducer(nextState, action);
		},
		nextState
	);
}
