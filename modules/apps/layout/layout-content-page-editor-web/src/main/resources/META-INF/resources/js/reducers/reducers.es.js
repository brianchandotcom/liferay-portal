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
	deleteFragmentEntryLinkCommentReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	moveFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkCommentReducer,
	updateFragmentEntryLinkConfigReducer,
	updateFragmentEntryLinkContentReducer
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

import {
	ADD_FRAGMENT_ENTRY_LINK,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS,
	OPEN_ASSET_TYPE_DIALOG,
	OPEN_MAPPING_FIELDS_DIALOG,
	SELECT_MAPPEABLE_TYPE,
	HIDE_MAPPING_TYPE_DIALOG,
	DELETE_FRAGMENT_ENTRY_LINK_COMMENT,
	CLEAR_FRAGMENT_EDITOR,
	DISABLE_FRAGMENT_EDITOR,
	ENABLE_FRAGMENT_EDITOR,
	MOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_CONFIG_ATTRIBUTES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONTENT,
	ADD_MAPPED_ASSET_ENTRY,
	CLEAR_ACTIVE_ITEM,
	UPDATE_ACTIVE_ITEM,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET,
	UPDATE_HOVERED_ITEM,
	CLEAR_HOVERED_ITEM,
	ADD_PORTLET,
	ADD_ROW,
	MOVE_ROW,
	REMOVE_ROW,
	UPDATE_ROW_COLUMNS_NUMBER_SUCCESS,
	UPDATE_ROW_CONFIG,
	CREATE_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	SELECT_SEGMENTS_EXPERIENCE,
	EDIT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY,
	UPDATE_SELECTED_SIDEBAR_PANEL_ID,
	CHANGE_LANGUAGE_ID,
	UPDATE_EDITABLE_VALUE_ERROR,
	UPDATE_EDITABLE_VALUE_LOADING,
	UPDATE_FRAGMENT_ENTRY_LINK_COMMENT_REPLY,
	UPDATE_FRAGMENT_ENTRY_LINK_COMMENT,
	UPDATE_ROW_COLUMNS_ERROR,
	UPDATE_ROW_COLUMNS_LOADING
} from '../actions/actions.es';

/**
 * List of reducers
 * @type {{ [key: string]: function }}
 */
const reducers = {
	[ADD_FRAGMENT_ENTRY_LINK]: addFragmentEntryLinkReducer,
	[ADD_MAPPED_ASSET_ENTRY]: addMappingAssetEntry,
	[ADD_PORTLET]: addPortletReducer,
	[ADD_ROW]: addRowReducer,
	[CHANGE_LANGUAGE_ID]: languageIdReducer,
	[CLEAR_ACTIVE_ITEM]: updateActiveItemReducer,
	[CLEAR_DROP_TARGET]: updateDropTargetReducer,
	[CLEAR_FRAGMENT_EDITOR]: clearFragmentEditorReducer,
	[CLEAR_HOVERED_ITEM]: updateHoveredItemReducer,
	[CREATE_SEGMENTS_EXPERIENCE]: createSegmentsExperienceReducer,
	[DELETE_FRAGMENT_ENTRY_LINK_COMMENT]: deleteFragmentEntryLinkCommentReducer,
	[DELETE_SEGMENTS_EXPERIENCE]: deleteSegmentsExperienceReducer,
	[DISABLE_FRAGMENT_EDITOR]: disableFragmentEditorReducer,
	[EDIT_SEGMENTS_EXPERIENCE]: editSegmentsExperienceReducer,
	[ENABLE_FRAGMENT_EDITOR]: enableFragmentEditorReducer,
	[HIDE_MAPPING_TYPE_DIALOG]: hideMappingTypeDialogReducer,
	[MOVE_FRAGMENT_ENTRY_LINK]: moveFragmentEntryLinkReducer,
	[MOVE_ROW]: moveRowReducer,
	[OPEN_ASSET_TYPE_DIALOG]: openAssetTypeDialogReducer,
	[OPEN_MAPPING_FIELDS_DIALOG]: openMappingFieldsDialogReducer,
	[REMOVE_FRAGMENT_ENTRY_LINK]: removeFragmentEntryLinkReducer,
	[REMOVE_ROW]: removeRowReducer,
	[SELECT_MAPPEABLE_TYPE]: selectMappeableTypeReducer,
	[SELECT_SEGMENTS_EXPERIENCE]: selectSegmentsExperienceReducer,
	[UPDATE_ACTIVE_ITEM]: updateActiveItemReducer,
	[UPDATE_CONFIG_ATTRIBUTES]: updateFragmentEntryLinkConfigReducer,
	[UPDATE_DROP_TARGET]: updateDropTargetReducer,
	[UPDATE_EDITABLE_VALUE_ERROR]: updateEditableValueReducer,
	[UPDATE_EDITABLE_VALUE_LOADING]: updateEditableValueReducer,
	[UPDATE_FRAGMENT_ENTRY_LINK_COMMENT_REPLY]: updateFragmentEntryLinkCommentReducer,
	[UPDATE_FRAGMENT_ENTRY_LINK_COMMENT]: updateFragmentEntryLinkCommentReducer,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONTENT]: updateFragmentEntryLinkContentReducer,
	[UPDATE_HOVERED_ITEM]: updateHoveredItemReducer,
	[UPDATE_LAST_SAVE_DATE]: saveChangesReducer,
	[UPDATE_ROW_COLUMNS_ERROR]: updateRowColumnsReducer,
	[UPDATE_ROW_COLUMNS_LOADING]: updateRowColumnsReducer,
	[UPDATE_ROW_COLUMNS_NUMBER_SUCCESS]: updateRowColumnsNumberReducer,
	[UPDATE_ROW_CONFIG]: updateRowConfigReducer,
	[UPDATE_SAVING_CHANGES_STATUS]: saveChangesReducer,
	[UPDATE_SEGMENTS_EXPERIENCE_PRIORITY]: updateSegmentsExperiencePriorityReducer,
	[UPDATE_SELECTED_SIDEBAR_PANEL_ID]: updateSelectedSidebarPanelId
};

export {reducers};
export default reducers;
