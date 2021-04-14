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

import {INIT} from '../actions/types';
import getFragmentEntryLinkIdsFromItemId from '../utils/getFragmentEntryLinkIdsFromItemId';

export default function defaultFragmentEntryLinksReducer(state, action) {
	switch (action.type) {
		case INIT: {
			const {fragmentEntryLinks, layoutData} = state;
			const deletedItemIds = layoutData.deletedItems.map(
				(deletedItem) => deletedItem.itemId
			);
			const newFragmentEntryLinks = {};

			const deletedFragments = deletedItemIds.reduce(
				(acc, deleteItemId) => [
					...acc,
					...getFragmentEntryLinkIdsFromItemId({
						itemId: deleteItemId,
						layoutData,
					}),
				],
				[]
			);

			deletedFragments.forEach((deletedFragment) => {
				newFragmentEntryLinks[deletedFragment] = {
					...fragmentEntryLinks[deletedFragment],
					removed: true,
				};
			});

			return {
				...state,
				fragmentEntryLinks: {
					...fragmentEntryLinks,
					...newFragmentEntryLinks,
				},
			};
		}

		default:
			return state;
	}
}
