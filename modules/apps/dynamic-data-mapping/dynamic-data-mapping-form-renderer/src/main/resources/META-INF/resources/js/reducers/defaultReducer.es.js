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

import {EVENT_TYPES} from '../actions/types.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.ALL:
			return {
				...action.payload,
				activePage:
					state.activePage > action.payload?.pages.length - 1
						? 0
						: state.activePage,
			};
		default:
			return state;
	}
};
