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
import getDescendantsCount from './getDescendantsCount';
import getItemPath from './getItemPath';
import moveItem from './moveItem';

const DIRECTIONS = {
	down: 'down',
	inside: 'inside',
	outside: 'outside',
	up: 'up',
};

const DragDropContext = React.createContext({});

export function DragDropProvider({children}) {
	const [parentId, setParentId] = useState(null);
	const [horizontalOffset, setHorizontalOffset] = useState(0);
	const [verticalOffset, setVerticalOffset] = useState(0);

	const dragDropValues = {
		horizontalOffset,
		parentId,
		setHorizontalOffset,
		setParentId,
		setVerticalOffset,
		verticalOffset,
	};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
}

export function useDragItem(item, onDragEnd) {
	const {parentSiteNavigationMenuItemId, siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const {
		parentId,
		setHorizontalOffset,
		setParentId,
		setVerticalOffset,
	} = useContext(DragDropContext);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {
			setParentId(parentSiteNavigationMenuItemId);
		},
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end() {
			onDragEnd(item.siteNavigationMenuItemId, parentId);

			setHorizontalOffset(0);
			setParentId(null);
			setVerticalOffset(null);
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

	const {languageId} = useConstants();
	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const {
		horizontalOffset,
		setHorizontalOffset,
		setParentId,
		setVerticalOffset,
		verticalOffset,
	} = useContext(DragDropContext);

	const [, targetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return monitor.isOver();
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
				else {
					const {
						currentOffset,
						direction,
						newIndex,
						newParentId,
					} = computeHoverAnotherItem({
						initialOffset: verticalOffset,
						items,
						monitor,
						source,
						targetId: siteNavigationMenuItemId,
					});

					if (newParentId) {
						setParentId(newParentId);
						setHorizontalOffset(0);
						setVerticalOffset(currentOffset);

						const newItems = moveItem(
							items,
							source.id,
							newParentId,
							newIndex,
							direction
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

	if (
		!newParentId ||
		newParentId === sourceItem.siteNavigationMenuItemId ||
		itemIsDynamic(newParentId, items)
	) {
		return;
	}

	return {currentOffset, newParentId};
}

function computeHoverAnotherItem({
	initialOffset,
	items,
	monitor,
	source,
	targetId,
}) {
	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const targetItem = items.find(
		(item) => item.siteNavigationMenuItemId === targetId
	);

	const currentOffset = monitor.getDifferenceFromInitialOffset().y;

	if (initialOffset === currentOffset) {
		return;
	}

	const direction =
		initialOffset > currentOffset ? DIRECTIONS.up : DIRECTIONS.down;

	const newIndex = items.indexOf(targetItem);

	if (newIndex === items.indexOf(sourceItem)) {
		return;
	}

	let newParentId;

	if (direction === DIRECTIONS.up) {
		newParentId = targetItem.parentSiteNavigationMenuItemId;
	}

	if (direction === DIRECTIONS.down) {
		const targetItemDescendantsCount = getDescendantsCount(items, targetId);

		newParentId = targetItemDescendantsCount
			? targetItem.siteNavigationMenuItemId
			: targetItem.parentSiteNavigationMenuItemId;
	}

	if (itemIsDynamic(newParentId, items)) {
		return;
	}

	return {
		currentOffset,
		direction,
		newIndex,
		newParentId,
	};
}

function itemIsDynamic(siteNavigationMenuItemId, items) {
	const item = items.find(
		(item) => item.siteNavigationMenuItemId === siteNavigationMenuItemId
	);

	return item?.dynamic;
}
