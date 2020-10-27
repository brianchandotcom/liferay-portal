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

import React, {useEffect, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {ACCEPTING_ITEM_TYPE} from '../constants/acceptingItemType';
import {useItems} from '../contexts/ItemsContext';
import getItemPath from './getItemPath';

const DragDropContext = React.createContext({});

export const DragDropProvider = ({children}) => {
	const [parentId, setParentId] = useState(null);

	const dragDropValues = {parentId, setParentId};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
};

export function useDragItem(item) {
	const {siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		isDragging(monitor) {
			return itemPath.includes(monitor.getItem().id);
		},
		item: {
			id: siteNavigationMenuItemId,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDragging,
	};
}

export function useDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

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
