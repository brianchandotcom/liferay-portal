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

import getDescendantsCount from './getDescendantsCount';
import updateItemParent from './updateItemParent';

export default function moveItem(items, itemId, parentId, newIndex, direction) {
	const itemIndex = items.findIndex(
		(item) => item.siteNavigationMenuItemId === itemId
	);

	const newItems = updateItemParent(items, itemId, parentId);

	const movedItems = newItems.filter(
		(item, index) =>
			index >= itemIndex &&
			index <= itemIndex + getDescendantsCount(items, itemId)
	);

	return newItems.reduce((acc, item, index) => {
		if (index === newIndex) {
			return direction === 'up'
				? [...acc, ...movedItems, item]
				: [...acc, item, ...movedItems];
		}
		if (
			movedItems.find(
				(movedItem) =>
					movedItem.siteNavigationMenuItemId ===
					item.siteNavigationMenuItemId
			)
		) {
			return acc;
		}
		else {
			return [...acc, item];
		}
	}, []);
}
