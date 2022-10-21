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

import {openToast, throttle} from 'frontend-js-web';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useReducer,
	useRef,
	useState,
} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {FRAGMENT_ENTRY_TYPES} from '../../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useCollectionItemIndex,
	useParentToControlsId,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useSelectorRef} from '../../contexts/StoreContext';
import {formIsMapped} from '../formIsMapped';
import {hasFormParent} from '../hasFormParent';
import {DRAG_DROP_TARGET_TYPE} from './constants/dragDropTargetType';
import {TARGET_POSITIONS} from './constants/targetPositions';
import defaultComputeHover from './defaultComputeHover';

export const initialDragDrop = {
	canDrag: true,

	dispatch: null,

	layoutDataRef: {
		current: {
			items: [],
		},
	},

	setCanDrag: () => {},

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

export function useDropTargetData() {
	const {dropTargetItem, targetPositionWithMiddle} = useContext(
		DragAndDropContext
	).state;

	return {
		item: dropTargetItem,
		position: targetPositionWithMiddle,
	};
}

export function useIsDroppable() {
	return useContext(DragAndDropContext).state.droppable;
}

export function useSetCanDrag() {
	return useContext(DragAndDropContext).setCanDrag;
}

export function NotDraggableArea({children}) {
	return (
		<div
			draggable
			onDragStart={(event) => {
				event.preventDefault();
				event.stopPropagation();
			}}
		>
			{children}
		</div>
	);
}

export function useDragItem(sourceItem, onDragEnd, onBegin = () => {}) {
	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);

	const getSourceItem = useCallback(
		() => ({
			...sourceItem,
			isWidget: sourceItem.isSymbol
				? sourceItem.isWidget
				: isWidget(sourceItem, fragmentEntryLinksRef.current),
		}),
		[fragmentEntryLinksRef, sourceItem]
	);

	const {canDrag, dispatch, layoutDataRef, state} = useContext(
		DragAndDropContext
	);
	const sourceRef = useRef(null);

	const [{isDraggingSource}, handlerRef, previewRef] = useDrag({
		begin() {
			onBegin();
		},

		canDrag,

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

export function useDragSymbol(
	{fragmentEntryType, icon, isWidget, label, type},
	onDragEnd
) {
	const selectItem = useSelectItem();

	const sourceItem = useMemo(
		() => ({
			fragmentEntryType,
			icon,
			isSymbol: true,
			isWidget,
			itemId: label,
			name: label,
			type,
		}),
		[fragmentEntryType, icon, isWidget, label, type]
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
	const toControlsId = useToControlsId();
	const parentToControlsId = useParentToControlsId();

	const {dispatch, layoutDataRef, state, targetRefs} = useContext(
		DragAndDropContext
	);
	const targetRef = useRef(null);

	const targetItem = useMemo(
		() => ({
			..._targetItem,
			collectionItemIndex,
			parentToControlsId,
			toControlsId,
		}),
		[_targetItem, collectionItemIndex, toControlsId, parentToControlsId]
	);

	const isOverTarget =
		state.dropTargetItem &&
		targetItem &&
		state.dropTargetItem.toControlsId(state.dropTargetItem.itemId) ===
			targetItem.toControlsId(targetItem.itemId);

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
				toControlsId,
			});
		},
	});

	useEffect(() => {
		const itemId = toControlsId(targetItem.itemId);

		targetRefs.set(itemId, targetRef);

		return () => {
			targetRefs.delete(itemId);
		};
	}, [layoutDataRef, targetItem, targetRef, targetRefs, toControlsId]);

	const setTargetRef = useCallback(
		(element) => {
			setDropTargetRef(element);

			targetRef.current = element;
		},
		[setDropTargetRef]
	);

	return {
		isOverTarget,
		sourceItem: state.dropItem,
		targetItemId: state.dropTargetItem?.toControlsId(
			state.dropTargetItem.itemId
		),
		targetPosition: state.targetPositionWithMiddle,
		targetRef: setTargetRef,
	};
}

export function DragAndDropContextProvider({children}) {
	const [canDrag, setCanDrag] = useState(true);

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

	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const dragAndDropContext = useMemo(
		() => ({
			canDrag,
			dispatch,
			layoutDataRef,
			setCanDrag,
			state,
			targetRefs,
		}),
		[canDrag, dispatch, layoutDataRef, state, targetRefs, setCanDrag]
	);

	return (
		<DragAndDropContext.Provider value={dragAndDropContext}>
			{children}
		</DragAndDropContext.Provider>
	);
}

function computeDrop({dispatch, layoutDataRef, onDragEnd, state}) {
	if (!state.droppable) {
		let message = '';

		if (state.dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			message = Liferay.Language.get(
				'fragments-and-widgets-cannot-be-placed-inside-this-area'
			);
		}
		else if (
			state.dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.collection
		) {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-collection-display-fragment'
			);
		}
		else if (
			state.dropTargetItem.type === LAYOUT_DATA_ITEM_TYPES.form &&
			!formIsMapped(state.dropTargetItem)
		) {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-form-container'
			);
		}
		else if (
			state.dropItem.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input
		) {
			message = Liferay.Language.get(
				'form-components-can-only-be-placed-inside-a-mapped-form-container'
			);
		}
		else if (
			state.dropItem.isWidget &&
			hasFormParent(state.dropItem, layoutDataRef.current)
		) {
			message = Liferay.Language.get(
				'widgets-cannot-be-placed-inside-a-form-container'
			);
		}
		else if (state.dropItem.parentId !== state.dropTargetItem.itemId) {
			message = Liferay.Language.get('an-unexpected-error-occurred');
		}

		if (message) {
			openToast({
				message,
				type: 'danger',
			});
		}

		dispatch(initialDragDrop.state);

		return;
	}

	if (state.dropItem && state.dropTargetItem) {
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
		dropItemPosition !== -1 &&
		dropItemPosition < siblingPosition &&
		siblingPosition > 0
	) {
		return siblingPosition - 1;
	}

	return siblingPosition;
}

function isWidget(item, fragmentEntryLinks) {
	const {fragmentEntryLinkId} = item.config;

	if (!fragmentEntryLinkId) {
		return false;
	}

	const fragmentEntryLink = fragmentEntryLinks[fragmentEntryLinkId];

	return Boolean(fragmentEntryLink.portletId);
}
