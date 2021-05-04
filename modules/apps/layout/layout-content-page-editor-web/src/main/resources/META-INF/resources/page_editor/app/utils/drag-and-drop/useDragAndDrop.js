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

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useCollectionItemIndex} from '../../contexts/CollectionItemContext';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import {DRAG_DROP_TARGET_TYPE} from './constants/dragDropTargetType';
import {TARGET_POSITIONS} from './constants/targetPositions';
import defaultComputeHover from './defaultComputeHover';
import toControlsId from './toControlsId';

export const initialDragDrop = {
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
			...(sourceItem.origin && {origin: sourceItem.origin}),
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

export function useDropTarget(_targetItem, computeHover = defaultComputeHover) {
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
			if (getSourceItem().origin !== targetItem.origin) {
				return;
			}
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

	const dragAndDropContext = useMemo(
		() => ({
			dispatch,
			layoutDataRef,
			state,
			targetRefs,
		}),
		[dispatch, layoutDataRef, state, targetRefs]
	);

	return (
		<DragAndDropContext.Provider value={dragAndDropContext}>
			{children}
		</DragAndDropContext.Provider>
	);
};

function computeDrop({dispatch, layoutDataRef, onDragEnd, state}) {
	if (state.droppable && state.dropItem && state.dropTargetItem) {
		if (state.elevate) {
			const parentItem =
				layoutDataRef.current.items[state.dropTargetItem.parentId];

			const position = Math.min(
				parentItem.children.includes(state.dropItem.itemId)
					? parentItem.children.length - 1
					: parentItem.children.length,
				getSiblingPosition(state, parentItem)
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

function getSiblingPosition(state, parentItem) {
	const dropItemPosition = parentItem.children.indexOf(state.dropItem.itemId);
	const siblingPosition = parentItem.children.indexOf(
		state.dropTargetItem.itemId
	);

	if (
		state.targetPositionWithoutMiddle === TARGET_POSITIONS.BOTTOM ||
		state.targetPositionWithoutMiddle === TARGET_POSITIONS.RIGHT
	) {
		return siblingPosition + 1;
	}
	else if (
		dropItemPosition != -1 &&
		dropItemPosition < siblingPosition &&
		siblingPosition > 0
	) {
		return siblingPosition - 1;
	}

	return siblingPosition;
}
