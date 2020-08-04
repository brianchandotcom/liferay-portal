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

import {SET_FRAGMENT_EDITABLES, UPDATE_LAYOUT_DATA} from '../actions/types';

export default function editablesReducer(editables = {}, action) {
	switch (action.type) {
		case SET_FRAGMENT_EDITABLES: {
			const editablesMap = {};

			action.editables.forEach((editable) => {
				const editableUniqueId = `${action.fragmentEntryLinkId}-${editable.editableId}`;

				editablesMap[editableUniqueId] = {
					...editable,
					fragmentEntryLinkId: action.fragmentEntryLinkId,
					itemId: editableUniqueId,
				};
			});

			return {
				...editables,
				[action.itemId]: editablesMap,
			};
		}

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
