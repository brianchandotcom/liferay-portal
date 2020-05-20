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
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE_START,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
} from '../../actions/types';

export function getActionLabel(action) {
	switch (action.type) {
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
			return Liferay.Util.sub(
				Liferay.Language.get('add-x'),
				action.itemName
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
			return Liferay.Language.get('change-experience');
		case UPDATE_COL_SIZE_START:
			return Liferay.Language.get('update-column-size');
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ITEM_CONFIG:
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
			return Liferay.Language.get('change-language');
		default:
			return;
	}
}
