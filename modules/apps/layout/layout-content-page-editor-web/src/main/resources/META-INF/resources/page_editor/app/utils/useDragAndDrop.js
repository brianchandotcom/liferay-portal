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
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useReducer,
	useRef,
} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {useCollectionItemIndex} from '../components/CollectionItemContext';
import {useSelectItem} from '../components/Controls';
import {getToControlsId} from '../components/layout-data-items/Collection';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

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

export const TARGET_POSITION = {
	BOTTOM: 'bottom',
	MIDDLE: 'middle',
	TOP: 'top',
};

const DRAG_DROP_TARGET_TYPE = {
	DRAGGING_TO_ITSELF: 'itself',
	ELEVATE: 'elevate',
	INITIAL: 'initial',
	INSIDE: 'inside',
};

const initialDragDrop = {
	dispatch: null,

	layoutData: {
		items: [],
	},

	state: {

		/**
		 * Item that is being dragged
		 */
		dropItem: null,

		/**
		 * Target item where the item is being dragged true.
		 * If elevate is true, dropTargetItem is the sibling
		 * of drop item, otherwise is it's parent.
		 */
		dropTargetItem: null,

		/**
		 * When false, an "invalid drop" advise should be shown
		 * to users.
		 */
		droppable: true,

		/**
		 * If true, dropTargetItem is the sibling of dropItem
		 * and targetPosition determines the item index.
		 */
		elevate: false,

		/**
		 * Vertical position relative to dropTargetItem
		 * (bottom, middle, top)
		 */
		targetPositionWithMiddle: null,

		/**
		 * Vertical position relative to dropTargetItem
		 * (bottom, top)
		 */
		targetPositionWithoutMiddle: null,

		/**
		 * Source of the Drag and Drop status
		 * (where the drag and drop status have been generated)
		 */
		type: DRAG_DROP_TARGET_TYPE.INITIAL,
	},

	targetRefs: new Map(),
};

const DragAndDropContext = React.createContext(initialDragDrop);

export function useDragItem(sourceItem, onDragEnd, onBegin = () => {}) {
	const getSourceItem = useCallback(() => sourceItem, [sourceItem]);
	const {dispatch, layoutData, state} = useContext(DragAndDropContext);
	const sourceRef = useRef(null);

	const [{isDraggingSource}, handlerRef, previewRef] = useDrag({
		begin() {
			onBegin();
		},

		collect: (monitor) => ({
			isDraggingSource: monitor.isDragging(),
		}),

		end() {
			computeDrop({
				dispatch,
				layoutData,
				onDragEnd,
				state,
			});
		},

		item: {
			getSourceItem,
			id: sourceItem.itemId,
			type: sourceItem.type,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDraggingSource,
		sourceRef,
	};
}

export function useDragSymbol({label, type}, onDragEnd) {
	const selectItem = useSelectItem();

	const sourceItem = useMemo(() => ({isSymbol: true, itemId: label, type}), [
		label,
		type,
	]);

	const {handlerRef, isDraggingSource, sourceRef} = useDragItem(
		sourceItem,
		onDragEnd,
		() => selectItem(null)
	);

	const symbolRef = (element) => {
		sourceRef.current = element;
		handlerRef(element);
	};

	return {
		isDraggingSource,
		sourceRef: symbolRef,
	};
}

export function useDropClear() {
	const {dispatch} = useContext(DragAndDropContext);

	const [, dropClearRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		hover() {
			dispatch(initialDragDrop.state);
		},
	});

	return dropClearRef;
}

export function useDropTarget(_targetItem) {
	const collectionItemIndex = useCollectionItemIndex();
	const {dispatch, layoutData, state, targetRefs} = useContext(
		DragAndDropContext
	);

	const targetRef = useRef(null);

	const targetItem = useMemo(
		() => ({
			..._targetItem,
			collectionItemIndex,
		}),
		[_targetItem, collectionItemIndex]
	);

	const isOverTarget =
		state.dropTargetItem &&
		targetItem &&
		toControlsId(layoutData, state.dropTargetItem) ===
			toControlsId(layoutData, targetItem);

	const [, setDropTargetRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		hover({getSourceItem}, monitor) {
			computeHover({
				dispatch,
				layoutData,
				monitor,
				sourceItem: getSourceItem(),
				targetItem,
				targetRefs,
			});
		},
	});

	useEffect(() => {
		if (!isOverTarget) {
			dispatch(initialDragDrop.state);
		}
	}, [dispatch, isOverTarget]);

	useEffect(() => {
		const itemId = toControlsId(layoutData, targetItem);

		targetRefs.set(itemId, targetRef);

		return () => {
			targetRefs.delete(itemId);
		};
	}, [layoutData, targetItem, targetRef, targetRefs]);

	const setTargetRef = useCallback(
		(element) => {
			setDropTargetRef(element);

			targetRef.current = element;
		},
		[setDropTargetRef]
	);

	return {
		canDropOverTarget: state.droppable,
		isOverTarget,
		sourceItem: state.dropItem,
		targetPosition: state.targetPositionWithMiddle,
		targetRef: setTargetRef,
	};
}

export const DragAndDropContextProvider = ({children, layoutData}) => {
	const [state, reducerDispatch] = useReducer(
		(state, nextState) =>
			Object.keys(state).some((key) => state[key] !== nextState[key])
				? nextState
				: state,
		initialDragDrop.state
	);

	const targetRefs = useMemo(() => new Map(), []);

	const dispatch = useMemo(() => {
		return throttle(reducerDispatch, 100);
	}, [reducerDispatch]);

	return (
		<DragAndDropContext.Provider
			value={{dispatch, layoutData, state, targetRefs}}
		>
			{children}
		</DragAndDropContext.Provider>
	);
};

function computeDrop({dispatch, layoutData, onDragEnd, state}) {
	if (state.droppable && state.dropItem && state.dropTargetItem) {
		if (state.elevate) {
			const parentItem = layoutData.items[state.dropTargetItem.parentId];

			const siblingPosition = parentItem.children.indexOf(
				state.dropTargetItem.itemId
			);

			const position = Math.min(
				parentItem.children.includes(state.dropItem.itemId)
					? parentItem.children.length - 1
					: parentItem.children.length,
				state.targetPositionWithoutMiddle === TARGET_POSITION.BOTTOM
					? siblingPosition + 1
					: siblingPosition
			);

			onDragEnd(parentItem.itemId, position);
		}
		else {
			onDragEnd(
				state.dropTargetItem.itemId,
				state.dropTargetItem.children.length
			);
		}
	}

	dispatch(initialDragDrop.state);
}

function computeHover({
	dispatch,
	layoutData,
	monitor,
	siblingItem = null,
	sourceItem,
	targetItem,
	targetRefs,
}) {

	// Not dragging over direct child
	// We do not want to alter state here,
	// as dnd generate extra hover events when
	// items are being dragged over nested children

	if (!monitor.isOver({shallow: true})) {
		return;
	}

	// Dragging over itself or a descendant

	if (itemIsAncestor(sourceItem, targetItem, layoutData)) {
		return dispatch({
			...initialDragDrop.state,
			type: DRAG_DROP_TARGET_TYPE.DRAGGING_TO_ITSELF,
		});
	}

	// Apparently valid drag, calculate vertical position and
	// nesting validation

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevationDepth,
	] = getItemPosition(
		siblingItem || targetItem,
		monitor,
		layoutData,
		targetRefs
	);

	// Drop inside target

	if (!siblingItem && targetPositionWithMiddle === TARGET_POSITION.MIDDLE) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: targetItem,
			droppable: checkAllowedChild(sourceItem, targetItem, layoutData),
			elevate: null,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.INSIDE,
		});
	}

	// Valid elevation:
	// - dropItem should be child of dropTargetItem
	// - dropItem should be sibling of siblingItem

	if (siblingItem && checkAllowedChild(sourceItem, targetItem, layoutData)) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: siblingItem,
			droppable: true,
			elevate: true,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.ELEVATE,
		});
	}

	// Try to elevate to some valid ancestor
	// Using dropTargetItem parent as target and dropTargetItem as sibling
	// It will try elevate multiple levels if elevationDepth is enough and
	// there are valid ancestors

	if (elevationDepth) {
		const getElevatedTargetItem = (sibling, maximumDepth) => {
			const parent = layoutData.items[sibling.parentId]
				? {
						...layoutData.items[sibling.parentId],
						collectionItemIndex: sibling.collectionItemIndex,
				  }
				: null;

			if (parent) {
				const [siblingPositionWithMiddle] = getItemPosition(
					sibling,
					monitor,
					layoutData,
					targetRefs
				);

				const [parentPositionWithMiddle] = getItemPosition(
					parent,
					monitor,
					layoutData,
					targetRefs
				);

				if (
					(siblingPositionWithMiddle === targetPositionWithMiddle ||
						parentPositionWithMiddle ===
							targetPositionWithMiddle) &&
					checkAllowedChild(sourceItem, parent, layoutData)
				) {
					if (maximumDepth > 1) {
						const [
							grandParent,
							parentSibling,
						] = getElevatedTargetItem(parent, maximumDepth - 1);

						if (grandParent) {
							return [grandParent, parentSibling];
						}
					}

					return [parent, sibling];
				}
				else {
					return getElevatedTargetItem(parent, maximumDepth);
				}
			}

			return [null, null];
		};

		const [elevatedTargetItem, siblingItem] = getElevatedTargetItem(
			targetItem,
			elevationDepth
		);

		if (elevatedTargetItem && elevatedTargetItem !== targetItem) {
			return computeHover({
				dispatch,
				layoutData,
				monitor,
				siblingItem,
				sourceItem,
				targetItem: elevatedTargetItem,
				targetRefs,
			});
		}
	}
}

/**
 * Checks if the given child can be nested inside given parent
 * @param {object} child
 * @param {object} parent
 * @return {boolean}
 */
function checkAllowedChild(child, parent, layoutData) {
	const parentIsInsideCollection = (function checkItemInsideCollection(item) {
		if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
			return true;
		}
		else if (item.parentId) {
			return checkItemInsideCollection(layoutData.items[item.parentId]);
		}
	})(parent);

	if (
		parentIsInsideCollection &&
		child.type === LAYOUT_DATA_ITEM_TYPES.collection
	) {
		return false;
	}

	return LAYOUT_DATA_ALLOWED_CHILDREN_TYPES[parent.type].includes(child.type);
}

/**
 * Checks if the given parent is indeed parent of given child.
 * @param {object} child
 * @param {object} parent
 * @param {object} layoutData
 * @return {boolean}
 */
function itemIsAncestor(child, parent, layoutData) {
	if (child && parent) {
		return child.itemId !== parent.itemId
			? itemIsAncestor(
					child,
					layoutData.items[parent.parentId],
					layoutData
			  )
			: true;
	}

	return false;
}

const ELEVATION_BORDER_SIZE = 15;
const MAXIMUM_ELEVATION_STEPS = 3;

/**
 * Returns the cursor vertical position (extracted from provided dnd monitor)
 * relative to the given item, taking into account configured elevation steps
 * with ELEVATION_BORDER_SIZE and MAXIMUM_ELEVATION_STEPS.
 *
 * For each elevation step, a border on the top/bottom of the element is added.
 * The first elevation step (being the nearest to the element's center)
 * elevates two its first valid ancestor, the second to the next one, and all
 * the way up until MAXIMUM_ELEVATION_STEPS has been reached or there are no
 * more valid ancestors.
 */
function getItemPosition(item, monitor, layoutData, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutData, item));

	if (!targetRef || !targetRef.current) {
		return [null, null, 0];
	}

	const hoverBoundingRect = targetRef.current.getBoundingClientRect();

	const clientOffsetY = monitor.getClientOffset().y;
	const hoverMiddleY = hoverBoundingRect.top + hoverBoundingRect.height / 2;

	const elevationStepSize = Math.min(
		hoverBoundingRect.height / (2 * (MAXIMUM_ELEVATION_STEPS + 1)),
		ELEVATION_BORDER_SIZE
	);

	const totalElevationBorderSize =
		elevationStepSize * MAXIMUM_ELEVATION_STEPS;

	const targetPositionWithoutMiddle =
		clientOffsetY < hoverMiddleY
			? TARGET_POSITION.TOP
			: TARGET_POSITION.BOTTOM;

	const targetPositionWithMiddle =
		clientOffsetY < hoverBoundingRect.bottom - totalElevationBorderSize &&
		clientOffsetY > hoverBoundingRect.top + totalElevationBorderSize
			? TARGET_POSITION.MIDDLE
			: targetPositionWithoutMiddle;

	let elevationDepth = 0;

	if (targetPositionWithMiddle !== TARGET_POSITION.MIDDLE) {
		const distanceFromBorder =
			targetPositionWithMiddle === TARGET_POSITION.TOP
				? clientOffsetY - hoverBoundingRect.top
				: hoverBoundingRect.bottom - clientOffsetY;

		elevationDepth =
			MAXIMUM_ELEVATION_STEPS -
			Math.floor(
				(distanceFromBorder / totalElevationBorderSize) *
					MAXIMUM_ELEVATION_STEPS
			);
	}

	return [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevationDepth,
	];
}

/**
 * Translates the given item ID into a collectionId-itemId if the item is
 * inside a collection. Otherwise, returns the plain itemId.
 * @param {object} layoutData
 * @param {object} item
 * @return {string}
 */
function toControlsId(layoutData, item) {
	const baseItem = item;

	const computeControlsId = (layoutData, item) => {
		const parent = layoutData.items[item.parentId];

		if (
			item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			baseItem.collectionItemIndex &&
			parent
		) {
			return getToControlsId(
				parent.itemId,
				baseItem.collectionItemIndex
			)(baseItem.itemId);
		}
		else if (parent) {
			return computeControlsId(layoutData, parent);
		}

		return baseItem.itemId;
	};

	return computeControlsId(layoutData, item);
}
