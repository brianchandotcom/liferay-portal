/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {throttle} from 'frontend-js-web';
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

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {config} from '../../config/index';
import {
	useCollectionItemIndex,
	useParentToControlsId,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useSelectorRef} from '../../contexts/StoreContext';
import {useGetWidgets} from '../../contexts/WidgetsContext';
import {isMultistepForm} from '../../utils/isMultistepForm';
import {openFormConversionModal} from '../../utils/openFormConversionModal';
import {getFormParent} from '../getFormParent';
import {isMovementValid} from '../isMovementValid';
import isStepper from '../isStepper';
import {DRAG_DROP_TARGET_TYPE} from './constants/dragDropTargetType';
import defaultComputeHover from './defaultComputeHover';
import getDropData from './getDropData';

export const initialDragDrop = {
	canDrag: true,

	dispatch: null,

	fragmentEntryLinksRef: {
		current: {},
	},

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
	const {dropTargetItem, targetPositionWithMiddle} =
		useContext(DragAndDropContext).state;

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

export function useDragItem(sourceItems, onDragEnd, onBegin = () => {}) {
	const {canDrag, dispatch, fragmentEntryLinksRef, layoutDataRef, state} =
		useContext(DragAndDropContext);
	const sourceRef = useRef(null);
	const lastSourceItem = sourceItems[sourceItems.length - 1];

	const getWidgets = useGetWidgets();

	const item = {
		...lastSourceItem,
		id: lastSourceItem.itemId,
		namespace: config.portletNamespace,
	};

	if (!lastSourceItem.origin) {
		delete item.origin;
	}

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
				fragmentEntryLinksRef,
				getWidgets,
				layoutDataRef,
				onDragEnd,
				sourceItems,
				state,
			});
		},

		item,
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
	{fieldTypes, fragmentEntryType, icon, isWidget, label, portletId, type},
	onDragEnd
) {
	const selectItem = useSelectItem();

	const sourceItems = useMemo(
		() => [
			{
				fieldTypes,
				fragmentEntryType,
				icon,
				isSymbol: true,
				isWidget,
				itemId: label,
				name: label,
				portletId,
				type,
			},
		],
		[fieldTypes, fragmentEntryType, icon, isWidget, label, portletId, type]
	);

	const {handlerRef, isDraggingSource, sourceRef} = useDragItem(
		sourceItems,
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

	const {dispatch, fragmentEntryLinksRef, layoutDataRef, state, targetRefs} =
		useContext(DragAndDropContext);

	const getWidgets = useGetWidgets();

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
		state.dropTargetItem.toControlsId?.(state.dropTargetItem.itemId) ===
			targetItem.toControlsId(targetItem.itemId);

	const coordsRef = useRef({x: 0, y: 0});

	const [, setDropTargetRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		hover(source, monitor) {
			if (source.origin !== targetItem.origin) {
				return;
			}

			const monitorCoords = monitor.getSourceClientOffset();

			if (
				coordsRef.current.x === monitorCoords.x &&
				coordsRef.current.y === monitorCoords.y
			) {
				return;
			}

			coordsRef.current = monitorCoords;

			computeHover({
				dispatch,
				fragmentEntryLinksRef,
				getWidgets,
				layoutDataRef,
				monitor,
				sourceItem: source,
				state,
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
		targetItemId: state.dropTargetItem?.toControlsId?.(
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

	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);

	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const dragAndDropContext = useMemo(
		() => ({
			canDrag,
			dispatch,
			fragmentEntryLinksRef,
			layoutDataRef,
			setCanDrag,
			state,
			targetRefs,
		}),
		[
			canDrag,
			dispatch,
			fragmentEntryLinksRef,
			layoutDataRef,
			state,
			targetRefs,
			setCanDrag,
		]
	);

	return (
		<DragAndDropContext.Provider value={dragAndDropContext}>
			{children}
		</DragAndDropContext.Provider>
	);
}

function computeDrop({
	dispatch,
	fragmentEntryLinksRef,
	getWidgets,
	layoutDataRef,
	onDragEnd,
	sourceItems,
	state,
}) {
	const {dropItem, dropTargetItem, targetPositionWithoutMiddle} = state;

	if (!dropItem) {
		return;
	}

	// Get the definitive parent id (dropItemId) where the dragged item drops

	const {dropItemId, position} = getDropData({
		isElevation: state.elevate,
		layoutDataRef,
		sourceItemId: dropItem.itemId,
		targetItemId: dropTargetItem.itemId,
		targetPosition: targetPositionWithoutMiddle,
	});

	if (
		!isMovementValid({
			fragmentEntryLinks: fragmentEntryLinksRef.current,
			getWidgets,
			layoutData: layoutDataRef.current,
			onInvalid: () => dispatch(initialDragDrop.state),
			sources: sourceItems,
			targetId: dropItemId,
		})
	) {
		return;
	}

	if (dropItem && dropTargetItem) {
		const targetItem = layoutDataRef.current.items[dropTargetItem.itemId];
		const formParent = getFormParent(targetItem, layoutDataRef.current);

		if (
			formParent &&
			sourceItems.some((item) => isStepper(item)) &&
			!isMultistepForm(formParent)
		) {
			openFormConversionModal({
				onContinue: () => onDragEnd(dropItemId, position),
			});
		}
		else {
			onDragEnd(dropItemId, position);
		}
	}

	dispatch(initialDragDrop.state);
}
