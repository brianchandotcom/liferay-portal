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
import permissionsReducer from './permissionsReducer';
import restrictedItemIdsReducer from './restrictedItemIdsReducer';
import selectedViewportSizeReducer from './selectedViewportSizeReducer';
import showResolvedCommentsReducer from './showResolvedCommentsReducer';
import sidebarReducer from './sidebarReducer';
import widgetsReducer from './widgetsReducer';
declare const REDUCER_MAP: {
	readonly collections: typeof collectionsReducer;
	readonly draft: typeof draftReducer;
	readonly fragmentEntryLinks: typeof fragmentEntryLinksReducer;
	readonly fragments: typeof fragmentsReducer;
	readonly languageId: typeof languageReducer;
	readonly layoutData: typeof layoutDataReducer;
	readonly mappingFields: typeof mappingFieldsReducer;
	readonly masterLayout: typeof masterLayoutReducer;
	readonly network: typeof networkReducer;
	readonly permissions: typeof permissionsReducer;
	readonly reducers: typeof baseReducer;
	readonly restrictedItemIds: typeof restrictedItemIdsReducer;
	readonly selectedViewportSize: typeof selectedViewportSizeReducer;
	readonly showResolvedComments: typeof showResolvedCommentsReducer;
	readonly sidebar: typeof sidebarReducer;
	readonly widgets: typeof widgetsReducer;
};
export declare type Action = Parameters<
	typeof REDUCER_MAP[keyof typeof REDUCER_MAP]
>[1];
export declare type State = {
	[key in keyof typeof REDUCER_MAP]: ReturnType<typeof REDUCER_MAP[key]>;
} & {
	segmentsExperienceId: string | null;
};

/**
 * Runs the base reducer plus any dynamically loaded reducers that have
 * been registered from plugins.
 */
export declare function reducer(state: State, action: Action): State;
export {};
