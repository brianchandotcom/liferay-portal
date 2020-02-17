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

import * as TYPES from '../actions/types';

export const INITIAL_STATE = 'en-US';

export default function languageReducer(languageId = INITIAL_STATE, action) {
	switch (action.type) {
		case TYPES.UPDATE_LANGUAGE_ID:
			return action.languageId;

		default:
			return languageId;
	}
}
