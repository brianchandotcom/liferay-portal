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

import {SELECT_SEGMENTS_EXPERIENCE} from '../../../plugins/experience/actions';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	CHANGE_MASTER_LAYOUT,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	SWITCH_VIEWPORT_SIZE,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_COL_SIZE,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
	UPDATE_ROW_COLUMNS,
} from '../../actions/types';
import {getItemNameFromAction} from './getItemNameFromAction';
import * as undoAddFragmentEntryLinks from './undoAddFragmentEntryLinks';
import * as undoAddItem from './undoAddItem';
import * as undoChangeMasterLayout from './undoChangeMasterLayout';
import * as undoDeleteItem from './undoDeleteItem';
import * as undoDuplicateItem from './undoDuplicateItem';
import * as undoMoveItem from './undoMoveItem';
import * as undoSelectExperience from './undoSelectExperience';
import * as undoSwitchViewportSize from './undoSwitchViewportSize';
import * as undoUpdateColSize from './undoUpdateColSize';
import * as undoUpdateCollectionDisplayCollection from './undoUpdateCollectionDisplayCollection';
import * as undoUpdateEditableValuesAction from './undoUpdateEditableValuesAction';
import * as undoUpdateFragmentConfiguration from './undoUpdateFragmentConfiguration';
import * as undoUpdateItemConfig from './undoUpdateItemConfig';
import * as undoUpdateLanguage from './undoUpdateLanguage';
import * as undoUpdateRowColumns from './undoUpdateRowColumns';

const UNDO_ACTIONS = {
	[ADD_FRAGMENT_ENTRY_LINKS]: undoAddFragmentEntryLinks,
	[ADD_ITEM]: undoAddItem,
	[CHANGE_MASTER_LAYOUT]: undoChangeMasterLayout,
	[DELETE_ITEM]: undoDeleteItem,
	[DUPLICATE_ITEM]: undoDuplicateItem,
	[MOVE_ITEM]: undoMoveItem,
	[SELECT_SEGMENTS_EXPERIENCE]: undoSelectExperience,
	[SWITCH_VIEWPORT_SIZE]: undoSwitchViewportSize,
	[UPDATE_COL_SIZE]: undoUpdateColSize,
	[UPDATE_COLLECTION_DISPLAY_COLLECTION]: undoUpdateCollectionDisplayCollection,
	[UPDATE_EDITABLE_VALUES]: undoUpdateEditableValuesAction,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]: undoUpdateFragmentConfiguration,
	[UPDATE_ITEM_CONFIG]: undoUpdateItemConfig,
	[UPDATE_LANGUAGE_ID]: undoUpdateLanguage,
	[UPDATE_ROW_COLUMNS]: undoUpdateRowColumns,
};

export function canUndoAction(action) {
	return Object.keys(UNDO_ACTIONS).includes(action.type);
}

export function getDerivedStateForUndo({action, state, type}) {
	const undoAction = UNDO_ACTIONS[type];

	return {
		...undoAction.getDerivedStateForUndo({action, state}),
		itemName: getItemNameFromAction({action, state}),
		segmentsExperienceId: state.segmentsExperienceId,
		type,
	};
}

export function undoAction({action, store}) {
	const {type} = action;

	const undoAction = UNDO_ACTIONS[type];

	return undoAction.undoAction({action, store});
}
