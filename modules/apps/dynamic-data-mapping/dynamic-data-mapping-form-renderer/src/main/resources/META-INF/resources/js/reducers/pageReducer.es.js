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
import {PagesVisitor} from '../util/visitors.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.UPDATE_PAGES:
			return {
				pages: action.payload,
			};
		case EVENT_TYPES.CHANGE_ACTIVE_PAGE:
			return {
				activePage: action.payload,
			};
		case EVENT_TYPES.PAGE_VALIDATION_FAILED: {
			const {newPages, pageIndex} = action.payload;
			const visitor = new PagesVisitor(newPages ?? state.pages);

			return {
				pages: visitor.mapFields(
					(
						field,
						fieldIndex,
						columnIndex,
						rowIndex,
						currentPageIndex
					) => {
						return {
							...field,
							displayErrors: currentPageIndex === pageIndex,
						};
					},
					true,
					true
				),
			};
		}
		default:
			return state;
	}
};
