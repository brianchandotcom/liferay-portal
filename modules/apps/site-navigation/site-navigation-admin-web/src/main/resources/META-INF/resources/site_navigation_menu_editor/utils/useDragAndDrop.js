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

import React, {useContext, useEffect, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {ACCEPTING_ITEM_TYPE} from '../constants/acceptingItemType';
import {NESTING_MARGIN} from '../constants/nestingMargin';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import updateItemParent from '../utils/updateItemParent';
import getItemPath from './getItemPath';

const DIRECTIONS = {
	inside: 'inside',
	outside: 'outside',
};

const DragDropContext = React.createContext({});

export const DragDropProvider = ({children}) => {
	const [parentId, setParentId] = useState(null);
	const [horizontalOffset, setHorizontalOffset] = useState(0);

	const dragDropValues = {
		horizontalOffset,
		parentId,
		setHorizontalOffset,
		setParentId,
	};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
};

export function useDragItem(item) {
	const {parentSiteNavigationMenuItemId, siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const {setHorizontalOffset, setParentId} = useContext(DragDropContext);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {
			setParentId(parentSiteNavigationMenuItemId);
		},
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end() {
			setHorizontalOffset(0);
			setParentId(null);
		},
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
	const setItems = useSetItems();

	const {languageDirection, languageId} = useConstants();
	const rtl = languageDirection[languageId] === 'rtl';

	const {horizontalOffset, setHorizontalOffset, setParentId} = useContext(
		DragDropContext
	);

	const [, targetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return monitor.isOver();
		},
		drop(source, monitor) {
			if (monitor.canDrop()) {

				// TODO

			}
		},
		hover(source, monitor) {
			if (monitor.canDrop(source, monitor)) {
				if (itemPath.includes(source.id)) {
					const data = computeHoverItself({
						initialOffset: horizontalOffset,
						items,
						monitor,
						rtl,
						source,
					});

					if (data) {
						const {currentOffset, newParentId} = data;

						setParentId(newParentId);
						setHorizontalOffset(currentOffset);

						const newItems = updateItemParent(
							items,
							source.id,
							newParentId
						);

						setItems(newItems);
					}
				}
			}
		},
	});

	return {
		targetRef,
	};
}

function getHorizontalMovementDirection(initialOffset, currentOffset, rtl) {
	if (rtl) {
		return initialOffset < currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
	else {
		return initialOffset > currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
}

function computeHoverItself({initialOffset, items, monitor, rtl, source}) {
	const currentOffset = monitor.getDifferenceFromInitialOffset().x;

	if (Math.abs(initialOffset - currentOffset) < NESTING_MARGIN) {
		return;
	}

	const direction = getHorizontalMovementDirection(
		initialOffset,
		currentOffset,
		rtl
	);

	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const sourceItemIndex = items.indexOf(sourceItem);

	let newParentId;

	if (direction === DIRECTIONS.inside) {
		const previousSibling = items
			.filter(
				(item, index) =>
					item.parentSiteNavigationMenuItemId ===
						sourceItem.parentSiteNavigationMenuItemId &&
					item.siteNavigationMenuItemId !== source.id &&
					index < sourceItemIndex
			)
			.pop();

		newParentId = previousSibling?.siteNavigationMenuItemId;
	}
	else {
		const nextSiblings = items.filter(
			(item, index) =>
				item.parentSiteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId &&
				item.siteNavigationMenuItemId !== source.id &&
				index > sourceItemIndex
		);

		if (!nextSiblings.length) {
			const parent = items.find(
				(item) =>
					item.siteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId
			);

			newParentId = parent?.parentSiteNavigationMenuItemId;
		}
	}

	if (!newParentId || newParentId === sourceItem.siteNavigationMenuItemId) {
		return;
	}

	return {currentOffset, newParentId};
}
