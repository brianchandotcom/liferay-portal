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
			return Liferay.Util.sub(
				Liferay.Language.get('add-x'),
				action.itemName
			);
		case CHANGE_MASTER_LAYOUT:
			return type === UNDO_TYPES.undo
				? Liferay.Util.sub(
						Liferay.Language.get('select-x-master-layout'),
						config.masterLayouts.find(
							(masterLayout) =>
								masterLayout.masterLayoutPlid ===
								action.nextMasterLayoutPlid
						).name
				  )
				: Liferay.Util.sub(
						Liferay.Language.get('select-x-master-layout'),
						config.masterLayouts.find(
							(masterLayout) =>
								masterLayout.masterLayoutPlid ===
								action.masterLayoutPlid
						).name
				  );

		case DELETE_ITEM:
			return Liferay.Util.sub(
				Liferay.Language.get('delete-x'),
				action.itemName
			);
		case DUPLICATE_ITEM:
			return Liferay.Util.sub(
				Liferay.Language.get('duplicate-x'),
				action.itemName
			);
		case MOVE_ITEM:
			return Liferay.Util.sub(
				Liferay.Language.get('move-x'),
				action.itemName
			);
		case SELECT_SEGMENTS_EXPERIENCE:
			return type === UNDO_TYPES.undo
				? Liferay.Util.sub(
						Liferay.Language.get('select-x-experience'),
						getSegmentsExperienceName(
							action.nextSegmentsExperienceId,
							availableSegmentsExperiences
						)
				  )
				: Liferay.Util.sub(
						Liferay.Language.get('select-x-experience'),
						getSegmentsExperienceName(
							action.segmentsExperienceId,
							availableSegmentsExperiences
						)
				  );
		case SWITCH_VIEWPORT_SIZE:
			return type === UNDO_TYPES.undo
				? Liferay.Util.sub(
						Liferay.Language.get('select-x-viewport'),
						config.availableViewportSizes[action.nextSize].label
				  )
				: Liferay.Util.sub(
						Liferay.Language.get('select-x-viewport'),
						config.availableViewportSizes[action.size].label
				  );

		case UPDATE_COL_SIZE:
			return Liferay.Language.get('update-column-size');
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ITEM_CONFIG:
		case UPDATE_ROW_COLUMNS:
			return Liferay.Util.sub(
				Liferay.Language.get('update-x-configuration'),
				action.itemName
			);
		case UPDATE_EDITABLE_VALUES:
			return Liferay.Util.sub(
				Liferay.Language.get('update-x-editable-values'),
				action.itemName
			);
		case UPDATE_LANGUAGE_ID:
			return type === UNDO_TYPES.undo
				? Liferay.Util.sub(
						Liferay.Language.get('select-x-language'),
						action.nextLanguageId
				  )
				: Liferay.Util.sub(
						Liferay.Language.get('select-x-language'),
						action.languageId
				  );
		default:
			return;
	}
}
