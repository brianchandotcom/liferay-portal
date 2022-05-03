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

import {LAYOUT_DATA_ITEM_TYPES} from './layoutDataItemTypes';

export const LAYOUT_DATA_ITEM_TYPE_LABELS = {
	[LAYOUT_DATA_ITEM_TYPES.collection]: Liferay.Language.get(
		'collection-display'
	),
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: Liferay.Language.get(
		'collection-item'
	),
	[LAYOUT_DATA_ITEM_TYPES.column]: Liferay.Language.get('module'),
	[LAYOUT_DATA_ITEM_TYPES.container]: Liferay.Language.get('container'),
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: Liferay.Language.get('drop-zone'),
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Liferay.Language.get('fragment'),
	[LAYOUT_DATA_ITEM_TYPES.form]: Liferay.Language.get('form-container'),
	[LAYOUT_DATA_ITEM_TYPES.root]: Liferay.Language.get('root'),
	[LAYOUT_DATA_ITEM_TYPES.row]: Liferay.Language.get('grid'),
};
