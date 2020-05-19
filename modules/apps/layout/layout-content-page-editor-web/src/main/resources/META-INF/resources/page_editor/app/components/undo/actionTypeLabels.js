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

export const ACTION_TYPE_LABELS = {
	[ADD_FRAGMENT_ENTRY_LINKS]: Liferay.Language.get('add-fragment'),
	[ADD_ITEM]: Liferay.Language.get('add-item'),
	[DELETE_ITEM]: Liferay.Language.get('delete-item'),
	[DUPLICATE_ITEM]: Liferay.Language.get('duplicate-item'),
	[MOVE_ITEM]: Liferay.Language.get('move-item'),
	[SELECT_SEGMENTS_EXPERIENCE]: Liferay.Language.get('change-experience'),
	[UPDATE_COL_SIZE_START]: Liferay.Language.get('update-column-size'),
	[UPDATE_EDITABLE_VALUES]: Liferay.Language.get('update-editable-values'),
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]: Liferay.Language.get(
		'update-fragment-configuration'
	),
	[UPDATE_ITEM_CONFIG]: Liferay.Language.get('update-item-configuration'),
	[UPDATE_LANGUAGE_ID]: Liferay.Language.get('change-language'),
};
