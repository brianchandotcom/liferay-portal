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

import {throttle} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useReducer} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {
	useCanElevate,
	useFromControlsId,
	useToControlsId,
} from './../components/CollectionItemContext';

const LAYOUT_DATA_ALLOWED_CHILDREN_TYPES = {
	[LAYOUT_DATA_ITEM_TYPES.root]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.collection]: [],
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: [
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: [],
	[LAYOUT_DATA_ITEM_TYPES.container]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.row]: [LAYOUT_DATA_ITEM_TYPES.column],
	[LAYOUT_DATA_ITEM_TYPES.column]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.fragment]: [],
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
};

const DISTANCE = 0.2;
const MAX_DIFFERENCE = 50;

export const TARGET_POSITION = {
	BOTTOM: 0,
	MIDDLE: 1,
	TOP: 2,
};

const RULES_TYPE = {
	ELEVATE: 3,
	INVALID_MOVE_WITH_FEEDBACK: 5,
	MIDDLE: 1,
	VALID_MOVE: 4,
};

const RULES = {
	[RULES_TYPE.MIDDLE]: ({hoverClientY, hoverMiddleY, ...args}) =>
		isValidMoveToMiddle(args) && isMiddle(hoverClientY, hoverMiddleY),
	[RULES_TYPE.ELEVATE]: checkElevate,
	[RULES_TYPE.VALID_MOVE]: isValidMoveToTargetPosition,
	[RULES_TYPE.INVALID_MOVE_WITH_FEEDBACK]: isInvalidMoveWithFeedback,
};

const initialDragDrop = {
	dispatch: null,
	state: {
		dropItem: null,
		dropTargetItemId: null,
		droppable: true,
		targetPositionWithMiddle: null,
		targetPositionWithoutMiddle: null,
	},
};

const getAncestorId = (parent, toControlsId) => {
	const ancestorId =
		parent.type === LAYOUT_DATA_ITEM_TYPES.column ||
		parent.type === LAYOUT_DATA_ITEM_TYPES.collectionItem
			? parent.parentId
			: parent.itemId;

	return parent.type === LAYOUT_DATA_ITEM_TYPES.collectionItem ||
		parent.type === LAYOUT_DATA_ITEM_TYPES.collection
		? ancestorId
		: toControlsId(ancestorId);
};

const dropTargetIsAncestor = (dropItem, layoutData, dropTargetId) => {
	const dropTarget = layoutData.items[dropTargetId];

	if (dropTarget) {
		return dropTarget.itemId !== dropItem.itemId
			? dropTargetIsAncestor(dropItem, layoutData, dropTarget.parentId)
			: true;
	}

	return false;
};

const DragAndDropContext = React.createContext(initialDragDrop);

export const DragAndDropContextProvider = ({children}) => {
	const [state, reducerDispatch] = useReducer((prevState, nextState) => {
		if (
			prevState.dropTargetItemId !== nextState.dropTargetItemId ||
			prevState.targetPositionWithMiddle !==
				nextState.targetPositionWithMiddle ||
			prevState.targetPositionWithoutMiddle !==
				nextState.targetPositionWithoutMiddle
		) {
			return nextState;
		}

		return prevState;
	}, initialDragDrop.state);

	const dispatch = useMemo(() => throttle(reducerDispatch, 100), [
		reducerDispatch,
	]);

	return (
		<DragAndDropContext.Provider value={{dispatch, state}}>
			{children}
		</DragAndDropContext.Provider>
	);
};

export default function useDragAndDrop({
	containerRef,
	dropTargetItem,
	layoutData,
	onDragEnd,
}) {
	const {dispatch, state} = useContext(DragAndDropContext);

	const {
		dropTargetItemId,
		droppable,
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
	} = state;

	const collectionItemCanElevate = useCanElevate();
	const fromControlsId = useFromControlsId();
	const toControlsId = useToControlsId();

	const [dragOptions, drag, preview] = useDrag({
		collect: (_monitor) => {
			return {
				isDragging: _monitor.isDragging(),
			};
		},

		end(dropItem, _monitor) {
			const result = _monitor.getDropResult();

			if (!result) {
				return;
			}

			const {itemId, parentId, position} = result;

			if (itemId !== parentId) {
				onDragEnd({
					itemId,
					parentItemId: parentId,
					position,
				});
			}
		},

		item: dropTargetItem,
	});

	const [dropOptions, drop] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),

		collect(_monitor) {
			return {
				canDrop: _monitor.canDrop(),
				isOver: _monitor.isOver({shallow: true}),
			};
		},

		drop(dropItem, _monitor) {
			if (!droppable) {
				return;
			}

			if (!_monitor.didDrop() && dropTargetItemId) {
				const {parentId, position} = getParentItemIdAndPositon({
					dropItem,
					dropTargetItemId: fromControlsId(dropTargetItemId),
					items: layoutData.items,
					targetPositionWithMiddle,
				});

				return {
					itemId: dropItem.itemId,
					itemType: _monitor.getItemType(),
					parentId,
					position,
				};
			}
		},

		hover(dropItem, _monitor) {
			if (
				!_monitor.isOver({shallow: true}) ||
				dropItem.itemId === dropTargetItem.itemId
			) {
				return;
			}

			if (rootVoid(dropTargetItem)) {
				dispatch({
					dropTargetItemId: dropTargetItem.itemId,
					droppable: true,
					targetPositionWithMiddle: TARGET_POSITION.MIDDLE,
					targetPositionWithoutMiddle,
				});

				return;
			}

			if (
				dropTargetIsAncestor(
					dropItem,
					layoutData,
					dropTargetItem.itemId
				)
			) {
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

			const [
				newTargetPositionWithMiddle,
				newTargetPositionWithoutMiddle,
			] = getTargetPosition(hoverClientY, hoverMiddleY);

			const result = checkRules(RULES, {
				clientOffset,
				collectionItemCanElevate,
				dropItem,
				dropTargetItem,
				hoverBoundingRect,
				hoverClientY,
				hoverMiddleY,
				items: layoutData.items,
				targetPositionWithMiddle: newTargetPositionWithMiddle,
				targetPositionWithoutMiddle: newTargetPositionWithoutMiddle,
				toControlsId,
			});

			switch (result) {
				case RULES_TYPE.MIDDLE:
					dispatch({
						dropItem,
						dropTargetItemId: toControlsId(dropTargetItem.itemId),
						droppable: true,
						targetPositionWithMiddle: TARGET_POSITION.MIDDLE,
						targetPositionWithoutMiddle: newTargetPositionWithoutMiddle,
					});
					break;
				case RULES_TYPE.ELEVATE: {
					dispatch({
						dropItem,
						dropTargetItemId:
							getAncestorId(
								layoutData.items[dropTargetItem.parentId],
								toControlsId
							) || toControlsId(dropTargetItem.itemId),
						droppable: true,
						targetPositionWithMiddle: newTargetPositionWithMiddle,
						targetPositionWithoutMiddle: newTargetPositionWithoutMiddle,
					});
					break;
				}
				case RULES_TYPE.VALID_MOVE:
					dispatch({
						dropItem,
						dropTargetItemId: toControlsId(dropTargetItem.itemId),
						droppable: true,
						targetPositionWithMiddle: newTargetPositionWithMiddle,
						targetPositionWithoutMiddle: newTargetPositionWithoutMiddle,
					});
					break;
				case RULES_TYPE.INVALID_MOVE_WITH_FEEDBACK:
					dispatch({
						dropItem,
						dropTargetItemId:
							dropTargetItem.type ===
							LAYOUT_DATA_ITEM_TYPES.fragment
								? toControlsId(dropTargetItem.parentId)
								: dropTargetItem.itemId !== dropItem.parentId &&
								  toControlsId(dropTargetItem.itemId),
						droppable:
							(dropItem.type !==
								LAYOUT_DATA_ITEM_TYPES.container &&
								dropItem.parentId ===
									dropTargetItem.parentId) ||
							(isNestingSupported(
								dropItem.type,
								dropTargetItem.type
							) &&
								!draggingCollectionInCollection(
									dropItem,
									dropTargetItem,
									layoutData.items
								)),
						targetPositionWithMiddle: TARGET_POSITION.MIDDLE,
						targetPositionWithoutMiddle: newTargetPositionWithoutMiddle,
					});
					break;
				default:
					break;
			}
		},
	});

	useEffect(() => {
		if (!dropOptions.isOver) {
			dispatch({
				dropTargetItemId: null,
				droppable: true,
				targetPositionWithMiddle: null,
				targetPositionWithoutMiddle: null,
			});
		}
	}, [dispatch, dropOptions.isOver]);

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return {
		...dragOptions,
		...dropOptions,
		drag,
		drop,
		state,
	};
}

function checkRules(rules, args) {
	const rulesType = Object.keys(rules);
	let passed = null;

	for (let index = 0; index < rulesType.length; index++) {
		const key = rulesType[index];
		const rule = rules[key];

		if (passed) {
			break;
		}

		if (rule(args)) {
			passed = Number(key);
		}
	}

	return passed;
}

/**
 * The closer you get to the edge of the element, the elevate will trigger,
 * which will move the interaction to the parent element. The calculation subtracts the
 * DISTANCE percentage from the edges to create the elevation area.
 */
function checkElevate({
	clientOffset,
	collectionItemCanElevate,
	dropItem,
	dropTargetItem,
	hoverBoundingRect,
	items,
	targetPositionWithoutMiddle,
	toControlsId,
}) {
	const parent = items[dropTargetItem.parentId];

	let isElevate = false;

	if (parent) {
		if (parent.type === LAYOUT_DATA_ITEM_TYPES.root) {
			return false;
		}

		if (
			!isValidCollectionItemElevate(
				collectionItemCanElevate,
				targetPositionWithoutMiddle
			)
		) {
			return false;
		}

		if (draggingCollectionInCollection(dropItem, dropTargetItem, items)) {
			return false;
		}

		const difference = Math.min(
			hoverBoundingRect.height * DISTANCE,
			MAX_DIFFERENCE
		);

		const parentIsRow = parent.type === LAYOUT_DATA_ITEM_TYPES.row;

		if (
			!isElevate &&
			(parentIsRow || isFirstItem(parent.children, dropTargetItem))
		) {
			isElevate = clientOffset.y < difference + hoverBoundingRect.top;
		}

		if (
			!isElevate &&
			(parentIsRow || isLastItem(parent.children, dropTargetItem))
		) {
			isElevate = clientOffset.y > hoverBoundingRect.bottom - difference;
		}

		if (dropItem.type === LAYOUT_DATA_ITEM_TYPES.container) {
			const ancestor =
				items[getAncestorId(parent, toControlsId)] || parent;

			return (
				isElevate &&
				(ancestor.type === LAYOUT_DATA_ITEM_TYPES.root ||
					(items[ancestor.parentId] &&
						items[ancestor.parentId].type ===
							LAYOUT_DATA_ITEM_TYPES.root))
			);
		}
	}

	return isElevate;
}

function isValidCollectionItemElevate(
	collectionItemCanElevate,
	targetPosition
) {
	return (
		!collectionItemCanElevate ||
		(collectionItemCanElevate.top &&
			targetPosition === TARGET_POSITION.TOP) ||
		(collectionItemCanElevate.bottom &&
			targetPosition === TARGET_POSITION.BOTTOM)
	);
}

function isValidMoveToMiddle({dropItem, dropTargetItem, items}) {
	return (
		!dropTargetItem.children.length &&
		!draggingCollectionInCollection(dropItem, dropTargetItem, items) &&
		isNestingSupported(dropItem.type, dropTargetItem.type)
	);
}

function isValidMoveToTargetPosition({
	dropItem,
	dropTargetItem,
	items,
	targetPositionWithMiddle,
	targetPositionWithoutMiddle,
	toControlsId,
}) {
	const {children} = items[
		dropTargetItem.parentId !== ''
			? dropTargetItem.parentId
			: dropTargetItem.itemId
	];

	if (
		typeof targetPositionWithMiddle !== 'number' &&
		!rootVoid(dropTargetItem)
	) {
		return false;
	}

	if (
		dropItem.type === LAYOUT_DATA_ITEM_TYPES.container &&
		dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.container &&
		targetPositionWithMiddle === TARGET_POSITION.MIDDLE
	) {
		return false;
	}

	if (dropItem.type === LAYOUT_DATA_ITEM_TYPES.container) {
		const parent = items[dropTargetItem.parentId];

		if (parent) {
			const ancestor =
				items[getAncestorId(parent, toControlsId)] || parent;

			if (!ancestor || ancestor.type !== LAYOUT_DATA_ITEM_TYPES.root) {
				return false;
			}
		}
	}

	if (draggingCollectionInCollection(dropItem, dropTargetItem, items)) {
		return false;
	}

	if (children.includes(dropItem.itemId)) {
		return !droppingInSamePosition({
			children,
			dropItem,
			dropTargetItem,
			targetPositionWithoutMiddle,
		});
	}

	return true;
}

function droppingInSamePosition({
	children,
	dropItem,
	dropTargetItem,
	targetPositionWithoutMiddle,
}) {
	const itemIndex = children.findIndex((id) => id === dropItem.itemId);
	const hoverId = dropTargetItem.itemId;

	return (
		(children[itemIndex + 1] === hoverId &&
			targetPositionWithoutMiddle === TARGET_POSITION.TOP) ||
		(children[itemIndex - 1] === hoverId &&
			targetPositionWithoutMiddle === TARGET_POSITION.BOTTOM) ||
		(isFirstItem(children, dropItem) &&
			targetPositionWithoutMiddle === TARGET_POSITION.TOP) ||
		(isLastItem(children, dropItem) &&
			targetPositionWithoutMiddle === TARGET_POSITION.BOTTOM)
	);
}

function isInvalidMoveWithFeedback({
	dropItem,
	dropTargetItem,
	items,
	targetPositionWithoutMiddle,
}) {
	const {children} = items[
		dropTargetItem.parentId !== ''
			? dropTargetItem.parentId
			: dropTargetItem.itemId
	];

	if (children.includes(dropItem.itemId)) {
		return !droppingInSamePosition({
			children,
			dropItem,
			dropTargetItem,
			targetPositionWithoutMiddle,
		});
	}

	return true;
}

/**
 * The calculation to identify when the mouse position is over the middle of the element,
 * the middle region is evaluated according to the MIDDLE_PERCENTAGE constant multiplied
 * by two, creating the region from the middle.
 */
function isMiddle(hoverClientY, hoverMiddleY) {
	const difference = Math.min(MAX_DIFFERENCE, hoverMiddleY * DISTANCE);

	return (
		hoverClientY > hoverMiddleY - difference &&
		hoverClientY < hoverMiddleY + difference
	);
}

/**
 * When dragging downwards, only move when the cursor is below 50%
 * When dragging upwards, only move when the cursor is above 50%
 */
function getTargetPosition(hoverClientY, hoverMiddleY) {
	const targetPositionWithoutMiddle =
		hoverClientY < hoverMiddleY
			? TARGET_POSITION.TOP
			: TARGET_POSITION.BOTTOM;

	const targetPositionWithMiddle = isMiddle(hoverClientY, hoverMiddleY)
		? TARGET_POSITION.MIDDLE
		: targetPositionWithoutMiddle;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle];
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
	dropItem,
	dropTargetItemId,
	items,
	targetPositionWithMiddle,
}) {
	const dropTargetItem = items[dropTargetItemId];

	if (
		(dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.column ||
			dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.collectionItem) &&
		targetPositionWithMiddle !== TARGET_POSITION.MIDDLE
	) {
		targetPositionWithMiddle = TARGET_POSITION.MIDDLE;
	}

	if (
		(dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.root ||
			targetPositionWithMiddle === TARGET_POSITION.MIDDLE) &&
		isNestingSupported(dropItem.type, dropTargetItem.type)
	) {
		return {
			parentId: dropTargetItem.itemId,
			position:
				targetPositionWithMiddle !== TARGET_POSITION.TOP
					? dropTargetItem.children.length
					: 0,
		};
	}

	const parent = items[dropTargetItem.parentId];

	if (parent) {
		const dropTargetItemIndex = parent.children.indexOf(
			dropTargetItem.itemId
		);

		let position =
			targetPositionWithMiddle === TARGET_POSITION.TOP
				? dropTargetItemIndex
				: dropTargetItemIndex + 1;

		// Moving an item in the same parent

		if (parent.children.includes(dropItem.itemId)) {
			const itemIndex = parent.children.indexOf(dropItem.itemId);

			if (parent.children.length === 1) {
				position = 0;
			}
			else {
				position =
					itemIndex < dropTargetItemIndex ? position - 1 : position;
			}
		}

		return {
			parentId: parent.itemId,
			position,
		};
	}
}

function isNestingSupported(itemType, parentType) {
	return LAYOUT_DATA_ALLOWED_CHILDREN_TYPES[parentType].includes(itemType);
}

function draggingCollectionInCollection(item, dropTargetItem, items) {
	return (
		item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
		hasCollectionItemAncestor(dropTargetItem, items)
	);
}

function hasCollectionItemAncestor(parent, items) {
	if (!parent) {
		return false;
	}

	return (
		parent.type === LAYOUT_DATA_ITEM_TYPES.collectionItem ||
		hasCollectionItemAncestor(items[parent.parentId], items)
	);
}
