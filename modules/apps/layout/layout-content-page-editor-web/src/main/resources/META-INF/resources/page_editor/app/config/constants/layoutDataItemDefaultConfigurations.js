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

export const LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: {
		size: 0
	},

	[LAYOUT_DATA_ITEM_TYPES.container]: {
		backgroundColorCssClass: '',
		backgroundImage: {},
		paddingBottom: 3,
		paddingHorizontal: 3,
		paddingTop: 3,
		type: 'fluid'
	},

	[LAYOUT_DATA_ITEM_TYPES.fragment]: {
		fragmentEntryLinkId: ''
	},

	[LAYOUT_DATA_ITEM_TYPES.root]: {},

	[LAYOUT_DATA_ITEM_TYPES.row]: {
		gutters: true,
		numberOfColumns: 3
	}
};
