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

import {useDrag, useDrop} from 'react-dnd';

import {ACCEPTING_ITEM_TYPE} from '../constants/acceptingItemType';

export function useDragItem(item) {
	const {siteNavigationMenuItemId} = item;

	const [, handlerRef] = useDrag({
		item: {
			id: siteNavigationMenuItemId,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	return {
		handlerRef,
	};
}

export function useDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const itemsMap = useItemsMap();
	const itemPath = getItemPath(siteNavigationMenuItemId, itemsMap);

	const [, targetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return (
				monitor.isOver() &&
				(!itemPath.includes(source.id) ||
					source.id === siteNavigationMenuItemId)
			);
		},
		drop(source, monitor) {
			if (monitor.canDrop()) {

				// TODO

			}
		},
	});

	return {
		targetRef,
	};
}
