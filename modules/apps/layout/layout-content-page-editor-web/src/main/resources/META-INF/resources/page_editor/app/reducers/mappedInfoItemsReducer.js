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

import {ADD_MAPPED_INFO_ITEM} from '../actions/types';

export const INITIAL_STATE = [];

export default function mappedInfoItemsReducer(
	mappedInfoItems = INITIAL_STATE,
	action
) {
	switch (action.type) {
		case ADD_MAPPED_INFO_ITEM:
			return [
				...mappedInfoItems,
				{
					className: action.className,
					classNameId: action.classNameId,
					classPK: action.classPK,
					classTypeId: action.classTypeId,
					subtype: action.itemSubtype,
					title: action.title,
					type: action.itemType,
				},
			];

		default:
			return mappedInfoItems;
	}
}
