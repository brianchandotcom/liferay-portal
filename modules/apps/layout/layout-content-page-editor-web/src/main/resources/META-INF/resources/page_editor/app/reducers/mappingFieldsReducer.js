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

import {ADD_MAPPING_FIELDS} from '../actions/types';

export const INITIAL_STATE = {};

export default function mappedInfoItemsReducer(
	mappingFields = INITIAL_STATE,
	action
) {
	switch (action.type) {
		case ADD_MAPPING_FIELDS:
			return {...mappingFields, [action.key]: action.fields};

		default:
			return mappingFields;
	}
}
