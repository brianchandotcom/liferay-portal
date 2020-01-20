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

import {useState, useEffect} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export const EDGE = {
	BOTTOM: 1,
	TOP: 0
};

export default function useDragAndDrop({
	accept,
	containerRef,
	item,
	layoutData,
	onDragEnd
}) {
	const [edge, setEdge] = useState(null);

	const [dragOptions, drag, preview] = useDrag({
		collect: _monitor => ({
			isDragging: _monitor.isDragging()
		}),
		end(_item, _monitor) {
			const result = _monitor.getDropResult();

			if (!result) {
				return;
			}

			const {itemId, parentId, position} = result;

			if (itemId !== parentId) {
				onDragEnd({
					itemId,
					parentItemId: parentId,
					position
				});
			}
		},
		item
	});

	const [dropOptions, drop] = useDrop({
		accept,
		collect(_monitor) {
			return {
				canDrop: _monitor.canDrop(),
				isOver: _monitor.isOver({shallow: true})
			};
		},
		drop(_item, _monitor) {
			if (!_monitor.didDrop()) {
				const {parentId, position} = getParentItemIdAndPositon({
					edge,
					item: _item,
					items: layoutData.items,
					siblingOrParentId: item.itemId
				});

				return {
					itemId: _item.itemId,
					itemType: _monitor.getItemType(),
					parentId,
					position
				};
			}
		},
		hover(_item, _monitor) {
			const dragId = _item.itemId;
			const hoverId = item.itemId;

			// Don't replace items with themselves
			if (dragId === hoverId) {
				setEdge(null);

				return;
			}

			// Determine rectangle on screen
			const hoverBoundingRect = containerRef.current.getBoundingClientRect();

			// Get vertical middle
			const hoverMiddleY =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			// Determine mouse position
			const clientOffset = _monitor.getClientOffset();

			// Get pixels to the top
			const hoverClientY = clientOffset.y - hoverBoundingRect.top;

			const hoverItem = layoutData.items[hoverId];

			const hoverParentOrItemId =
				hoverItem.parentId !== '' ? hoverItem.parentId : hoverId;

			const parentOrItemChildren =
				layoutData.items[hoverParentOrItemId].children;

			if (parentOrItemChildren.includes(dragId)) {
				// Get the index of the draggable element in the children if the drag is
				// happening inside the segment.
				const dragIndex = parentOrItemChildren.findIndex(
					child => child === dragId
				);

				// When dragging downwards, only move when the cursor is below 50%
				// When dragging upwards, only move when the cursor is above 50%
				// Dragging downwards
				if (
					parentOrItemChildren[dragIndex] !==
						parentOrItemChildren[0] &&
					parentOrItemChildren[dragIndex + 1] !== hoverId &&
					hoverClientY < hoverMiddleY
				) {
					setEdge(EDGE.TOP);
					return;
				}

				// Dragging upwards
				if (
					parentOrItemChildren[dragIndex] !==
						parentOrItemChildren[parentOrItemChildren.length - 1] &&
					parentOrItemChildren[dragIndex - 1] !== hoverId &&
					hoverClientY > hoverMiddleY
				) {
					setEdge(EDGE.BOTTOM);
					return;
				}
			} else {
				if (hoverClientY < hoverMiddleY) {
					setEdge(EDGE.TOP);
					return;
				}

				if (hoverClientY > hoverMiddleY) {
					setEdge(EDGE.BOTTOM);
					return;
				}
			}

			setEdge(null);
		}
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return {
		...dragOptions,
		...dropOptions,
		drag,
		drop,
		edge
	};
}

function getParentItemIdAndPositon({edge, item, items, siblingOrParentId}) {
	const siblingOrParent = items[siblingOrParentId];

	if (isNestingSupported(item.type, siblingOrParent.type)) {
		return {
			parentId: siblingOrParentId,
			position: siblingOrParent.children.length
		};
	} else {
		const parent = items[siblingOrParent.parentId];

		const siblingIndex = parent.children.indexOf(siblingOrParentId);

		let position = edge === EDGE.TOP ? siblingIndex : siblingIndex + 1;

		// Moving an item in the same parent
		if (parent.children.includes(item.itemId)) {
			const itemIndex = parent.children.indexOf(item.itemId);

			position = itemIndex < siblingIndex ? position - 1 : position;
		}

		return {
			parentId: parent.itemId,
			position
		};
	}
}

function isNestingSupported(itemType, parentType) {
	return LAYOUT_DATA_ALLOWED_PARENT_TYPES[itemType].includes(parentType);
}
