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

import {cancelDebounce, debounce} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export const TARGET_POSITION = {
	BOTTOM: 0,
	MIDDLE: 1,
	TOP: 2
};

const NESTING_LEVEL = {
	[LAYOUT_DATA_ITEM_TYPES.column]: 0,
	[LAYOUT_DATA_ITEM_TYPES.container]: 1,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: Infinity,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Infinity,
	[LAYOUT_DATA_ITEM_TYPES.root]: 0,
	[LAYOUT_DATA_ITEM_TYPES.row]: Infinity
};

const initialDragDrop = {
	dropTargetItemId: null,
	targetPosition: null
};

export const DragDropManagerImpl = React.createContext(initialDragDrop);

export const DragDropManager = ({children}) => {
	const [store, setStore] = useState(initialDragDrop);

	const dispatch = debounce(newStore => {
		if (
			store.dropTargetItemId !== newStore.dropTargetItemId ||
			store.targetPosition !== newStore.targetPosition
		) {
			setStore(newStore);
		}
	});

	useEffect(() => {
		return () => {
			cancelDebounce(dispatch);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<DragDropManagerImpl.Provider value={{dispatch, store}}>
			{children}
		</DragDropManagerImpl.Provider>
	);
};

export default function useDragAndDrop({
	accept,
	containerRef,
	dropNestedAndSibling,
	item,
	layoutData,
	onDragEnd
}) {
	const {
		dispatch,
		store: {dropTargetItemId, targetPosition}
	} = useContext(DragDropManagerImpl);

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
				(isValidMoveToMiddle(
					dropNestedAndSibling,
					layoutData.items[dropTargetItemId],
					_item
				) ||
					isValidMoveToTargetPosition({
						item: _item,
						items: layoutData.items,
						siblingOrParent: layoutData.items[dropTargetItemId],
						targetPosition
					}))
			) {
				const {parentId, position} = getParentItemIdAndPositon({
					dropNestedAndSibling,
					item: _item,
					items: layoutData.items,
					siblingOrParentId: dropTargetItemId,
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
				dispatch({
					dropTargetItemId: null,
					targetPosition: null
				});

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
					dispatch({
						dropTargetItemId: item.itemId,
						targetPosition: TARGET_POSITION.MIDDLE
					});

					return;
				}
			}

			const newTargetPosition = getTargetPosition(
				hoverClientY,
				hoverMiddleY
			);

			if (
				isElevate(
					clientOffset.y,
					hoverBoundingRect.height,
					hoverBoundingRect.top,
					hoverBoundingRect.bottom
				)
			) {
				const parent = layoutData.items[item.parentId];

				if (parent && parent.type !== LAYOUT_DATA_ITEM_TYPES.root) {
					dispatch({
						dropTargetItemId:
							parent.type !== LAYOUT_DATA_ITEM_TYPES.row &&
							parent.children.length
								? parent.parentId
								: item.parentId,
						targetPosition: newTargetPosition
					});

					return;
				}
			}

			if (
				isValidMoveToTargetPosition({
					item: _item,
					items: layoutData.items,
					siblingOrParent: item,
					targetPosition: newTargetPosition
				})
			) {
				dispatch({
					dropTargetItemId: item.itemId,
					targetPosition: newTargetPosition
				});
			} else {
				dispatch({
					dropTargetItemId: null,
					targetPosition: null
				});
			}
		}
	});

	useEffect(() => {
		if (!dropOptions.isOver || !dragOptions.isDragging) {
			dispatch({
				dropTargetItemId: null,
				targetPosition: null
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dropOptions.isOver, dragOptions.isDragging]);

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return {
		...dragOptions,
		...dropOptions,
		drag,
		drop
	};
}

function getLevel(item, items, siblingOrParent) {
	const {type} = item;

	let counter = 0;

	const traverse = currentItem => {
		const node = items[currentItem.parentId];

		if (!node) {
			return;
		}

		if (node.type === type) {
			counter++;
		}

		if (node.parentId === '') {
			return;
		}

		traverse(items[node.parentId]);
	};

	traverse(siblingOrParent);

	return counter;
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

	if (NESTING_LEVEL[item.type] !== Infinity) {
		if (
			getLevel(item, items, siblingOrParent) >= NESTING_LEVEL[item.type]
		) {
			return false;
		}
	}

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

const DISTANCE = 0.2;

/**
 * The closer you get to the edge of the element, the elevate will trigger,
 * which will move the interaction to the parent element. The calculation subtracts the
 * DISTANCE percentage from the edges to create the elevation area.
 */
function isElevate(clientOffsetY, height, top, bottom) {
	return (
		clientOffsetY < height * DISTANCE + top ||
		clientOffsetY > bottom - height * DISTANCE
	);
}

/**
 * When dragging downwards, only move when the cursor is below 50%
 * When dragging upwards, only move when the cursor is above 50%
 */
function getTargetPosition(hoverClientY, hoverMiddleY) {
	if (hoverClientY < hoverMiddleY) {
		return TARGET_POSITION.TOP;
	} else if (hoverClientY > hoverMiddleY) {
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
	} else {
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
