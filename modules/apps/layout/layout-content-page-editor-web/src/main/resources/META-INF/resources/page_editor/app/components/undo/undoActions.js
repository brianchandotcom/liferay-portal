/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SELECT_SEGMENTS_EXPERIENCE} from '../../../plugins/experience/actions';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	ADD_RULE,
	ADD_STEPPER,
	CHANGE_MASTER_LAYOUT,
	DELETE_ITEM,
	DELETE_RULE,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	MOVE_STEPPER,
	PASTE_ITEM,
	SWITCH_VIEWPORT_SIZE,
	TOGGLE_FRAGMENT_HIGHLIGHTED,
	TOGGLE_WIDGET_HIGHLIGHTED,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_COL_SIZE,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FORM_ITEM_CONFIG,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
	UPDATE_ROW_COLUMNS,
	UPDATE_RULE,
} from '../../actions/types';
import {getItemNameFromAction} from './getItemNameFromAction';
import * as undoAddFragmentEntryLinks from './undoAddFragmentEntryLinks';
import * as undoAddItem from './undoAddItem';
import * as undoAddRule from './undoAddRule';
import * as undoChangeMasterLayout from './undoChangeMasterLayout';
import * as undoDeleteItem from './undoDeleteItem';
import * as undoDeleteRule from './undoDeleteRule';
import * as undoDuplicateItem from './undoDuplicateItem';
import * as undoMoveItems from './undoMoveItems';
import * as undoPasteItems from './undoPasteItems';
import * as undoSelectExperience from './undoSelectExperience';
import * as undoStepperAction from './undoStepperAction';
import * as undoSwitchViewportSize from './undoSwitchViewportSize';
import * as undoToggleFragmentHighlighted from './undoToggleFragmentHighlighted';
import * as undoToggleWidgetHighlighted from './undoToggleWidgetHighlighted';
import * as undoUpdateColSize from './undoUpdateColSize';
import * as undoUpdateCollectionDisplayCollection from './undoUpdateCollectionDisplayCollection';
import * as undoUpdateEditableValuesAction from './undoUpdateEditableValuesAction';
import * as undoUpdateFormItemConfig from './undoUpdateFormItemConfig';
import * as undoUpdateFragmentConfiguration from './undoUpdateFragmentConfiguration';
import * as undoUpdateItemConfig from './undoUpdateItemConfig';
import * as undoUpdateLanguage from './undoUpdateLanguage';
import * as undoUpdateRowColumns from './undoUpdateRowColumns';
import * as undoUpdateRule from './undoUpdateRule';

const UNDO_ACTIONS = {
	[ADD_FRAGMENT_ENTRY_LINKS]: undoAddFragmentEntryLinks,
	[ADD_ITEM]: undoAddItem,
	[ADD_RULE]: undoAddRule,
	[ADD_STEPPER]: undoStepperAction,
	[CHANGE_MASTER_LAYOUT]: undoChangeMasterLayout,
	[DELETE_ITEM]: undoDeleteItem,
	[DELETE_RULE]: undoDeleteRule,
	[DUPLICATE_ITEM]: undoDuplicateItem,
	[MOVE_ITEM]: undoMoveItems,
	[MOVE_STEPPER]: undoStepperAction,
	[PASTE_ITEM]: undoPasteItems,
	[SELECT_SEGMENTS_EXPERIENCE]: undoSelectExperience,
	[SWITCH_VIEWPORT_SIZE]: undoSwitchViewportSize,
	[TOGGLE_FRAGMENT_HIGHLIGHTED]: undoToggleFragmentHighlighted,
	[TOGGLE_WIDGET_HIGHLIGHTED]: undoToggleWidgetHighlighted,
	[UPDATE_COL_SIZE]: undoUpdateColSize,
	[UPDATE_COLLECTION_DISPLAY_COLLECTION]:
		undoUpdateCollectionDisplayCollection,
	[UPDATE_EDITABLE_VALUES]: undoUpdateEditableValuesAction,
	[UPDATE_FORM_ITEM_CONFIG]: undoUpdateFormItemConfig,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]: undoUpdateFragmentConfiguration,
	[UPDATE_ITEM_CONFIG]: undoUpdateItemConfig,
	[UPDATE_LANGUAGE_ID]: undoUpdateLanguage,
	[UPDATE_ROW_COLUMNS]: undoUpdateRowColumns,
	[UPDATE_RULE]: undoUpdateRule,
};

export function canUndoAction(action) {
	return (
		Object.keys(UNDO_ACTIONS).includes(action.type) && !action.disableUndo
	);
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
