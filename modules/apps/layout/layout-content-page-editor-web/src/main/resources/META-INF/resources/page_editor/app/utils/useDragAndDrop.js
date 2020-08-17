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
import {ORIGIN_TYPES} from '../config/constants/originTypes';
import {useSelector} from '../store/index';

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
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: [],
	[LAYOUT_DATA_ITEM_TYPES.container]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.dropZone,
		LAYOUT_DATA_ITEM_TYPES.row,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	],
	[LAYOUT_DATA_ITEM_TYPES.row]: [LAYOUT_DATA_ITEM_TYPES.column],
	[LAYOUT_DATA_ITEM_TYPES.column]: [
		LAYOUT_DATA_ITEM_TYPES.collection,
		LAYOUT_DATA_ITEM_TYPES.container,
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

	layoutDataRef: {
		current: {
			items: [],
		},
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

export const NotDraggableArea = ({children}) => (
	<div
		draggable
		onDragStart={(e) => {
			e.preventDefault();
			e.stopPropagation();
		}}
	>
		{children}
	</div>
);

export function useDragItem(sourceItem, onDragEnd, onBegin = () => {}) {
	const getSourceItem = useCallback(() => sourceItem, [sourceItem]);
	const {dispatch, layoutDataRef, state} = useContext(DragAndDropContext);
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
				layoutDataRef,
				onDragEnd,
				state,
			});
		},

		item: {
			getSourceItem,
			icon: sourceItem.icon,
			id: sourceItem.itemId,
			name: sourceItem.name,
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

export function useDragSymbol({icon, label, type}, onDragEnd) {
	const selectItem = useSelectItem();

	const sourceItem = useMemo(
		() => ({icon, isSymbol: true, itemId: label, name: label, type}),
		[icon, label, type]
	);

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
	const {dispatch, layoutDataRef, state, targetRefs} = useContext(
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
		toControlsId(layoutDataRef, state.dropTargetItem) ===
			toControlsId(layoutDataRef, targetItem);

	const [, setDropTargetRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		hover({getSourceItem}, monitor) {
			computeHover({
				dispatch,
				layoutDataRef,
				monitor,
				sourceItem: getSourceItem(),
				targetItem,
				targetRefs,
			});
		},
	});

	useEffect(() => {
		const itemId = toControlsId(layoutDataRef, targetItem);

		targetRefs.set(itemId, targetRef);

		return () => {
			targetRefs.delete(itemId);
		};
	}, [layoutDataRef, targetItem, targetRef, targetRefs]);

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

export const DragAndDropContextProvider = ({children}) => {
	const layoutDataRef = useRef({
		items: [],
	});

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

	useSelector((state) => {
		layoutDataRef.current = state.layoutData;

		return null;
	});

	return (
		<DragAndDropContext.Provider
			value={{dispatch, layoutDataRef, state, targetRefs}}
		>
			{children}
		</DragAndDropContext.Provider>
	);
};

function computeDrop({dispatch, layoutDataRef, onDragEnd, state}) {
	if (state.droppable && state.dropItem && state.dropTargetItem) {
		if (state.elevate) {
			const parentItem =
				layoutDataRef.current.items[state.dropTargetItem.parentId];

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
			const position = state.dropTargetItem.children.includes(
				state.dropItem.itemId
			)
				? state.dropTargetItem.children.length - 1
				: state.dropTargetItem.children.length;

			onDragEnd(state.dropTargetItem.itemId, position);
		}
	}

	dispatch(initialDragDrop.state);
}

function computeHover({
	dispatch,
	layoutDataRef,
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

	if (
		(sourceItem.origin === ORIGIN_TYPES.treeview && !targetItem.origin) ||
		(!sourceItem.origin && targetItem.origin === ORIGIN_TYPES.treeview)
	) {
		return;
	}

	// Dragging over itself or a descendant

	if (itemIsAncestor(sourceItem, targetItem, layoutDataRef)) {
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
		layoutDataRef,
		targetRefs
	);

	// Drop inside target

	const validDropInsideTarget = (() => {
		const targetIsColumn =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.column;
		const targetIsFragment =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.fragment;
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;

		return (
			targetPositionWithMiddle === TARGET_POSITION.MIDDLE &&
			(targetIsEmpty || targetIsColumn) &&
			!targetIsFragment
		);
	})();

	if (!siblingItem && validDropInsideTarget) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: targetItem,
			droppable: checkAllowedChild(sourceItem, targetItem, layoutDataRef),
			elevate: null,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.INSIDE,
		});
	}

	// Valid elevation:
	// - dropItem should be child of dropTargetItem
	// - dropItem should be sibling of siblingItem

	if (
		siblingItem &&
		checkAllowedChild(sourceItem, targetItem, layoutDataRef)
	) {
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
			const parent = layoutDataRef.current.items[sibling.parentId]
				? {
						...layoutDataRef.current.items[sibling.parentId],
						collectionItemIndex: sibling.collectionItemIndex,
				  }
				: null;

			if (parent) {
				const [siblingPositionWithMiddle] = getItemPosition(
					sibling,
					monitor,
					layoutDataRef,
					targetRefs
				);

				const [parentPositionWithMiddle] = getItemPosition(
					parent,
					monitor,
					layoutDataRef,
					targetRefs
				);

				if (
					(siblingPositionWithMiddle === targetPositionWithMiddle ||
						parentPositionWithMiddle ===
							targetPositionWithMiddle) &&
					checkAllowedChild(sourceItem, parent, layoutDataRef)
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
				layoutDataRef,
				monitor,
				siblingItem,
				sourceItem,
				targetItem: targetItem.origin
					? {...elevatedTargetItem, origin: targetItem.origin}
					: elevatedTargetItem,
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
function checkAllowedChild(child, parent, layoutDataRef) {
	const parentIsInsideCollection = (function checkItemInsideCollection(item) {
		if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
			return true;
		}
		else if (item.parentId) {
			return checkItemInsideCollection(
				layoutDataRef.current.items[item.parentId]
			);
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
 * @param {object} layoutDataRef
 * @return {boolean}
 */
function itemIsAncestor(child, parent, layoutDataRef) {
	if (child && parent) {
		return child.itemId !== parent.itemId
			? itemIsAncestor(
					child,
					layoutDataRef.current.items[parent.parentId],
					layoutDataRef
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
function getItemPosition(item, monitor, layoutDataRef, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));

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
 * @param {object} layoutDataRef
 * @param {object} item
 * @return {string}
 */
function toControlsId(layoutDataRef, item) {
	const baseItem = item;

	const computeControlsId = (layoutDataRef, item) => {
		const parent = layoutDataRef.current.items[item.parentId];

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
			return computeControlsId(layoutDataRef, parent);
		}

		return baseItem.itemId;
	};

	return computeControlsId(layoutDataRef, item);
}
