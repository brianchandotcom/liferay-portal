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
import fragmentEntryLinksReducer from './fragmentEntryLinksReducer';
import languageReducer from './languageReducer';
import layoutDataReducer from './layoutDataReducer';
import mappingReducer from './mappingReducer';
import networkReducer from './networkReducer';
import permissionsReducer from './permissionsReducer';
import resolvedCommentsReducer from './resolvedCommentsReducer';
import sidebarReducer from './sidebarReducer';
import widgetsReducer from './widgetsReducer';

function combineReducers(reducersObject) {
	return (state, action) =>
		Object.entries(reducersObject).reduce(
			(nextState, [namespace, reducer]) => ({
				...nextState,
				[namespace]: reducer(nextState[namespace], action)
			}),
			state
		);
}

/**
 * Runs the base reducer plus any dynamically loaded reducers that have
 * been registered from plugins.
 */
export function reducer(state, action) {
	return [
		baseReducer,
		sidebarReducer,
		combineReducers({
			fragmentEntryLinks: fragmentEntryLinksReducer,
			languageId: languageReducer,
			layoutData: layoutDataReducer,
			mappedInfoItems: mappingReducer,
			network: networkReducer,
			permissions: permissionsReducer,
			showResolvedComments: resolvedCommentsReducer,
			widgets: widgetsReducer
		}),
		...Object.values(state.reducers)
	].reduce((nextState, nextReducer) => {
		return nextReducer(nextState, action);
	}, state);
}
