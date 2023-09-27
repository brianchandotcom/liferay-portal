/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {SELECT_SEGMENTS_EXPERIENCE} from '../../../plugins/experience/actions';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	CHANGE_MASTER_LAYOUT,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
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
} from '../../actions/types';
import {UNDO_TYPES} from '../../config/constants/undoTypes';
import {config} from '../../config/index';
import getSegmentsExperienceName from '../../utils/getSegmentsExperienceName';

export default function getActionLabel(
	action,
	type,
	{availableSegmentsExperiences}
) {
	switch (action.originalType || action.type) {
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
			return sub(Liferay.Language.get('add-x'), action.itemName);
		case CHANGE_MASTER_LAYOUT:
			return type === UNDO_TYPES.undo
				? sub(
						Liferay.Language.get('select-x-master-layout'),
						config.masterLayouts.find(
							(masterLayout) =>
								masterLayout.masterLayoutPlid ===
								action.nextMasterLayoutPlid
						).name
				  )
				: sub(
						Liferay.Language.get('select-x-master-layout'),
						config.masterLayouts.find(
							(masterLayout) =>
								masterLayout.masterLayoutPlid ===
								action.masterLayoutPlid
						).name
				  );

		case DELETE_ITEM:
			return sub(Liferay.Language.get('delete-x'), action.itemName);
		case DUPLICATE_ITEM:
			return sub(Liferay.Language.get('duplicate-x'), action.itemName);
		case MOVE_ITEM:
			return sub(Liferay.Language.get('move-x'), action.itemName);
		case SELECT_SEGMENTS_EXPERIENCE:
			return type === UNDO_TYPES.undo
				? sub(
						Liferay.Language.get('select-x-experience'),
						getSegmentsExperienceName(
							action.nextSegmentsExperienceId,
							availableSegmentsExperiences
						)
				  )
				: sub(
						Liferay.Language.get('select-x-experience'),
						getSegmentsExperienceName(
							action.segmentsExperienceId,
							availableSegmentsExperiences
						)
				  );
		case SWITCH_VIEWPORT_SIZE:
			return type === UNDO_TYPES.undo
				? sub(
						Liferay.Language.get('select-x-viewport'),
						config.availableViewportSizes[action.nextSize].label
				  )
				: sub(
						Liferay.Language.get('select-x-viewport'),
						config.availableViewportSizes[action.size].label
				  );

		case TOGGLE_FRAGMENT_HIGHLIGHTED:
			return action.initiallyHighlighted
				? Liferay.Language.get('add-fragment-to-favorites')
				: Liferay.Language.get('remove-fragment-from-favorites');

		case TOGGLE_WIDGET_HIGHLIGHTED:
			return action.initiallyHighlighted
				? Liferay.Language.get('add-widget-to-favorites')
				: Liferay.Language.get('remove-widget-from-favorites');

		case UPDATE_COL_SIZE:
			return Liferay.Language.get('update-column-size');
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_FORM_ITEM_CONFIG:
		case UPDATE_ITEM_CONFIG:
		case UPDATE_ROW_COLUMNS:
			return sub(
				Liferay.Language.get('update-x-configuration'),
				action.itemName
			);
		case UPDATE_EDITABLE_VALUES:
			return sub(
				Liferay.Language.get('update-x-editable-values'),
				action.itemName
			);
		case UPDATE_LANGUAGE_ID:
			return type === UNDO_TYPES.undo
				? sub(
						Liferay.Language.get('select-x-language'),
						action.nextLanguageId
				  )
				: sub(
						Liferay.Language.get('select-x-language'),
						action.languageId
				  );
		default:
			return;
	}
}
