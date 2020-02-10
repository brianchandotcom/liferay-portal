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

import {useEffect, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export const TARGET_POSITION = {
	BOTTOM: 0,
	MIDDLE: 1,
	TOP: 2
};

export default function useDragAndDrop({
	accept,
	containerRef,
	dropNestedAndSibling,
	item,
	layoutData,
	onDragEnd
}) {
	const [targetPosition, setTargetPosition] = useState(null);

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
			if (
				!_monitor.didDrop() &&
				(isValidMoveToMiddle(dropNestedAndSibling, item, _item) ||
					isValidMoveToTargetPosition({
						item: _item,
						items: layoutData.items,
						siblingOrParent: item,
						targetPosition
					}))
			) {
				const {parentId, position} = getParentItemIdAndPositon({
					dropNestedAndSibling,
					item: _item,
					items: layoutData.items,
					siblingOrParentId: item.itemId,
					targetPosition
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
			if (_item.itemId === item.itemId || rootVoid(item)) {
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

			if (isValidMoveToMiddle(dropNestedAndSibling, item, _item)) {
				if (isMiddle(hoverClientY, hoverMiddleY)) {
					setTargetPosition(TARGET_POSITION.MIDDLE);

					return;
				}
			}

			const newTargetPosition = getTargetPosition(
				hoverClientY,
				hoverMiddleY
			);

			if (
				isValidMoveToTargetPosition({
					item: _item,
					items: layoutData.items,
					siblingOrParent: item,
					targetPosition: newTargetPosition
				})
			) {
				setTargetPosition(newTargetPosition);
			}
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
		targetPosition
	};
}

function isValidMoveToMiddle(enable, item, dragItem) {
	return (
		enable &&
		!item.children.length &&
		isNestingSupported(dragItem.type, item.type)
	);
}

function isValidMoveToTargetPosition({
	item,
	items,
	siblingOrParent,
	targetPosition
}) {
	const {children} = items[
		siblingOrParent.parentId !== ''
			? siblingOrParent.parentId
			: siblingOrParent.itemId
	];

	if (typeof targetPosition !== 'number' && !rootVoid(siblingOrParent)) {
		return false;
	}

	if (children.includes(item.itemId)) {
		return !(
			isSibling(children, item, targetPosition, siblingOrParent.itemId) ||
			(isFirstItem(children, item) &&
				targetPosition === TARGET_POSITION.TOP) ||
			(isLastItem(children, item) &&
				targetPosition === TARGET_POSITION.BOTTOM)
		);
	}

	return true;
}

const MIDDLE_PERCENTAGE = 0.3;

/**
 * The calculation to identify when the mouse position is over the middle of the element,
 * the middle region is evaluated according to the MIDDLE_PERCENTAGE constant multiplied
 * by two, creating the region from the middle.
 */
function isMiddle(hoverClientY, hoverMiddleY) {
	if (
		hoverClientY > hoverMiddleY - hoverMiddleY * MIDDLE_PERCENTAGE &&
		hoverClientY < hoverMiddleY + hoverMiddleY * MIDDLE_PERCENTAGE
	) {
		return true;
	}

	return false;
}

/**
 * When dragging downwards, only move when the cursor is below 50%
 * When dragging upwards, only move when the cursor is above 50%
 */
function getTargetPosition(hoverClientY, hoverMiddleY) {
	if (hoverClientY < hoverMiddleY) {
		return TARGET_POSITION.TOP;
	}
	else if (hoverClientY > hoverMiddleY) {
		return TARGET_POSITION.BOTTOM;
	}
}

function isSibling(children, item, targetPosition, hoverId) {
	const itemIndex = children.findIndex(id => id === item.itemId);

	return (
		(children[itemIndex + 1] === hoverId &&
			targetPosition === TARGET_POSITION.TOP) ||
		(children[itemIndex - 1] === hoverId &&
			targetPosition === TARGET_POSITION.BOTTOM)
	);
}

function isLastItem(children, item) {
	return item.itemId === children[children.length - 1];
}

function isFirstItem(children, item) {
	return item.itemId === children[0];
}

function rootVoid(item) {
	return (
		item.type === LAYOUT_DATA_ITEM_TYPES.root && item.children.length === 0
	);
}

function getParentItemIdAndPositon({
	dropNestedAndSibling,
	item,
	items,
	siblingOrParentId,
	targetPosition
}) {
	const siblingOrParent = items[siblingOrParentId];

	if (
		(!dropNestedAndSibling &&
			isNestingSupported(item.type, siblingOrParent.type)) ||
		(dropNestedAndSibling && targetPosition === TARGET_POSITION.MIDDLE)
	) {
		return {
			parentId: siblingOrParentId,
			position: siblingOrParent.children.length
		};
	}
	else {
		const parent = items[siblingOrParent.parentId];

		const siblingIndex = parent.children.indexOf(siblingOrParentId);

		let position =
			targetPosition === TARGET_POSITION.TOP
				? siblingIndex
				: siblingIndex + 1;

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
