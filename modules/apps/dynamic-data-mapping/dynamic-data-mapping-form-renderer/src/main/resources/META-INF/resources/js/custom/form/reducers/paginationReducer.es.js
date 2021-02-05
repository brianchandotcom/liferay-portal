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

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.PAGINATION.NEXT: {
			const {activePage, pages} = state;

			return {
				activePage: Math.min(activePage + 1, pages.length - 1),
			};
		}
		case EVENT_TYPES.PAGINATION.CHANGE: {
			const {activePage} = state;

			return {
				activePage: Math.max(activePage - 1, 0),
			};
		}
		default:
			return state;
	}
};
