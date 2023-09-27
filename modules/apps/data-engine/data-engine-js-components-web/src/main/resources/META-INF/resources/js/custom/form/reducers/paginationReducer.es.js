/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default function paginationReducer(state, action) {
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
}
