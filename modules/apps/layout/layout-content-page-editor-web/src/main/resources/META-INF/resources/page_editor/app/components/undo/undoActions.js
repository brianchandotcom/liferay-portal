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
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE_START,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
} from '../../actions/types';
import * as undoDuplicateItem from './undoDuplicateItem';
import * as undoEditableValuesAction from './undoEditableValuesAction';
import * as undoFragmentConfiguration from './undoFragmentConfiguration';
import * as undoFragmentEntryLinks from './undoFragmentEntryLinks';
import * as undoLayoutDataAction from './undoLayoutDataAction';
import * as undoUpdateLanguage from './undoUpdateLanguage';

const UNDO_ACTIONS = {
	[ADD_FRAGMENT_ENTRY_LINKS]: undoFragmentEntryLinks,
	[ADD_ITEM]: undoLayoutDataAction,
	[DUPLICATE_ITEM]: undoDuplicateItem,
	[MOVE_ITEM]: undoLayoutDataAction,
	[UPDATE_COL_SIZE_START]: undoLayoutDataAction,
	[UPDATE_EDITABLE_VALUES]: undoEditableValuesAction,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]: undoFragmentConfiguration,
	[UPDATE_ITEM_CONFIG]: undoLayoutDataAction,
	[UPDATE_LANGUAGE_ID]: undoUpdateLanguage,
};

export function canUndoAction(action) {
	return Object.keys(UNDO_ACTIONS).includes(action.type) && !action.isUndo;
}

export function getDerivedStateForUndo({action, state, type}) {
	const undoAction = UNDO_ACTIONS[type];

	return {...undoAction.getDerivedStateForUndo({action, state}), type};
}

export function undoAction({action, store}) {
	const {type} = action;

	const undoAction = UNDO_ACTIONS[type];

	return undoAction.undoAction({action, store});
}
