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

import {
	addFragmentEntryLinkReducer,
	clearFragmentEditorReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	moveFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer
} from './fragments.es';
import {addMappingAssetEntry} from './mapping.es';
import {addPortletReducer} from './portlets.es';
import {
	addRowReducer,
	moveRowReducer,
	removeRowReducer,
	updateRowColumnsNumberReducer,
	updateRowColumnsReducer,
	updateRowConfigReducer
} from './rows.es';
import {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	selectSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer
} from './segmentsExperiences.es';
import {updateSelectedSidebarPanelId} from './sidebar.es';
import {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
} from './dialogs.es';
import {languageIdReducer} from './translations.es';
import {saveChangesReducer} from './changes.es';
import {
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateHoveredItemReducer
} from './placeholders.es';

/**
 * List of reducers
 * @type {function[]}
 */
const reducers = [
	addFragmentEntryLinkReducer,
	addMappingAssetEntry,
	addPortletReducer,
	addRowReducer,
	clearFragmentEditorReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	languageIdReducer,
	moveFragmentEntryLinkReducer,
	moveRowReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	removeFragmentEntryLinkReducer,
	removeRowReducer,
	saveChangesReducer,
	selectMappeableTypeReducer,
	selectSegmentsExperienceReducer,
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer,
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer,
	updateHoveredItemReducer,
	updateRowColumnsNumberReducer,
	updateRowColumnsReducer,
	updateRowConfigReducer,
	updateSelectedSidebarPanelId
];

export {reducers};
export default reducers;
