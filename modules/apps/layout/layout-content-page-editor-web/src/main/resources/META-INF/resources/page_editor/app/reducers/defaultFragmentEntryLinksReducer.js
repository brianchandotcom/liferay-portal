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
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function defaultFragmentEntryLinksReducer(state, action) {
	switch (action.type) {
		case INIT: {
			const {fragmentEntryLinks, layoutData} = state;

			const getDeletedFragments = (items, deletedItemId) => {
				return items
					.filter((item) => item.itemId === deletedItemId)
					.reduce((acc, item) => {
						return item.type === LAYOUT_DATA_ITEM_TYPES.fragment
							? [...acc, item]
							: item.children.flatMap((item) =>
									getDeletedFragments(items, item)
							  );
					}, []);
			};

			const deletedFragments = layoutData.deletedItems.reduce(
				(acc, deletedItem) => {
					return [
						...acc,
						...getDeletedFragments(
							Object.values(layoutData.items),
							deletedItem.itemId
						),
					];
				},
				[]
			);

			const newFragmentEntryLinks = Object.keys(
				fragmentEntryLinks
			).reduce((acc, key) => {
				return {
					...acc,
					[key]: {
						...fragmentEntryLinks[key],
						removed: deletedFragments.some(
							(item) => item.config.fragmentEntryLinkId === key
						),
					},
				};
			}, {});

			return {
				...state,
				fragmentEntryLinks: newFragmentEntryLinks,
			};
		}

		default:
			return state;
	}
}
