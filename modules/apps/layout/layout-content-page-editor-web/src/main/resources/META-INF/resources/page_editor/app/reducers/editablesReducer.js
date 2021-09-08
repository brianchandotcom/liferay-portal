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

import {UPDATE_LAYOUT_DATA} from '../actions/types';

export default function editablesReducer(editables = {}, action) {
	switch (action.type) {
		case UPDATE_LAYOUT_DATA: {
			let nextEditables = editables;

			action.deletedFragmentEntryLinkIds.forEach(
				(fragmentEntryLinkId) => {
					nextEditables = {...nextEditables};

					delete nextEditables[fragmentEntryLinkId];
				}
			);

			return nextEditables;
		}

		default:
			return editables;
	}
}
