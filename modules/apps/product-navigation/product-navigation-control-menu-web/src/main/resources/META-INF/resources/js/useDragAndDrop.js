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

import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {AddPanelContext, updateUsedWidget} from './AddPanel';
import addPortlet from './addPortlet';
import {LAYOUT_DATA_ITEM_TYPES} from './constants/layoutDataItemTypes';
import {POSITIONS} from './constants/positions';

const DROP_ACTIVE_VALID_CLASS = 'yui3-dd-drop-active-valid';

const DROP_CLASS = 'yui3-dd-drop';

const DROP_OVER_CLASS = 'yui3-dd-drop-over';

const DROP_ZONE_CLASS = 'portlet-dropzone';

const DROP_ZONE_DISABLED_CLASS = 'portlet-dropzone-disabled';

const initialDragDrop = {
	dragIndicatorPosition: {},
	dropTargetColumn: null,
	dropTargetItem: null,
	setDragIndicatorPosition: () => null,
	setDropTargetColumn: () => null,
	setDropTargetItem: () => null,
};

const DragAndDropContext = React.createContext(initialDragDrop);
export const DragAndDropProvider = DragAndDropContext.Provider;

export function useDragItem(sourceItem) {
	const getSourceItem = useCallback(() => sourceItem, [sourceItem]);
	const sourceRef = useRef(null);

	const [{isDraggingSource}, handlerRef, previewRef] = useDrag({
		collect: (monitor) => ({
			isDraggingSource: monitor.isDragging(),
		}),

		item: {
			data: sourceItem.data,
			disabled: sourceItem.disabled,
			getSourceItem,
			icon: sourceItem.icon,
			itemId: sourceItem.itemId,
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

export function useDragSymbol({data, icon, label, portletId, type}) {
	const sourceItem = useMemo(
		() => ({
			data,
			icon,
			isSymbol: true,
			itemId: portletId,
			name: label,
			type,
		}),
		[data, icon, label, portletId, type]
	);

	const {handlerRef, isDraggingSource, sourceRef} = useDragItem(sourceItem);

	const symbolRef = (element) => {
		sourceRef.current = element;
		handlerRef(element);
	};

	return {
		isDraggingSource,
		sourceRef: symbolRef,
	};
}

export function useDropClear(targetItem) {
	const {dropTargetColumn, setDropTargetColumn} = useContext(
		DragAndDropContext
	);

	const [, setDropClearRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		hover(item, monitor) {
			if (!monitor.isOver({shallow: true})) {
				return;
			}

			if (dropTargetColumn) {
				dropTargetColumn.classList.remove(DROP_OVER_CLASS);
				dropTargetColumn.classList.remove(DROP_ACTIVE_VALID_CLASS);
			}

			setDropTargetColumn(null);
		},
	});

	setDropClearRef(targetItem);
}

const getHoverPosition = (monitor, targetItem) => {
	const clientOffset = monitor.getClientOffset();
	const targetItemBoundingRect = targetItem.getBoundingClientRect();
	const targetItemHeight = targetItem.getBoundingClientRect().height;

	const hoverTopLimit = targetItemHeight / 2;
	const hoverClientY = clientOffset.y - targetItemBoundingRect.top;

	return {hoverClientY, hoverTopLimit, targetItemBoundingRect};
};

const getDropPosition = ({monitor, targetItem}) => {
	const {hoverClientY, hoverTopLimit} = getHoverPosition(monitor, targetItem);

	return hoverClientY < hoverTopLimit ? POSITIONS.top : POSITIONS.bottom;
};

const getDropIndicatorPosition = ({
	monitor,
	targetItem,
	windowScrollPosition,
}) => {
	const {
		hoverClientY,
		hoverTopLimit,
		targetItemBoundingRect,
	} = getHoverPosition(monitor, targetItem);

	const positionY =
		hoverClientY < hoverTopLimit
			? targetItemBoundingRect.top
			: targetItemBoundingRect.bottom;

	return {
		clientX: targetItemBoundingRect.left,
		clientY: positionY + windowScrollPosition,
		width: targetItemBoundingRect.width,
	};
};

export function useDropTarget(targetItem) {
	const {
		dropTargetColumn,
		dropTargetItem,
		setDragIndicatorPosition,
		setDropTargetColumn,
		setDropTargetItem,
	} = useContext(DragAndDropContext);

	const [windowScrollPosition, setWindowScrollPosition] = useState(0);
	const [targetPosition, setTargetPosition] = useState(null);
	const {plid, setWidgets, widgets} = useContext(AddPanelContext);

	useEffect(() => {
		const handleWindowScroll = () => {
			setWindowScrollPosition(window.scrollY);
		};

		window.addEventListener('scroll', handleWindowScroll);

		return () => {
			window.removeEventListener('scroll', handleWindowScroll);
		};
	}, []);

	const itemIsDroppable = (item) => item.classList.contains(DROP_CLASS);
	const itemIsDropzone = (item) =>
		item.classList.contains(DROP_ZONE_CLASS) &&
		!item.classList.contains(DROP_ZONE_DISABLED_CLASS);

	const [{validDrop}, setDropTargetRef] = useDrop({
		accept: Object.values(LAYOUT_DATA_ITEM_TYPES),
		collect: (monitor) => ({
			validDrop: monitor.canDrop(),
		}),
		drop(item, monitor) {
			setDropTargetColumn(null);

			if (!monitor.isOver({shallow: true})) {
				return;
			}

			if (!itemIsDroppable(targetItem) && !itemIsDropzone(targetItem)) {
				return;
			}

			if (!item.disabled) {
				dropTargetColumn.classList.remove(DROP_OVER_CLASS);
				dropTargetColumn.classList.remove(DROP_ACTIVE_VALID_CLASS);

				addPortlet({item, plid, targetItem, targetPosition});

				if (!item.data.instanceable) {
					const updatedWidgets = updateUsedWidget({
						item,
						widgets,
					});
					setWidgets(updatedWidgets);
				}
			}
		},
		hover(item, monitor) {
			if (!monitor.isOver({shallow: true})) {
				return;
			}

			const targetItemIsDropzone = itemIsDropzone(targetItem);

			if (!itemIsDroppable(targetItem) && !targetItemIsDropzone) {
				return;
			}

			const parentTargetItem = targetItemIsDropzone
				? targetItem.parentElement
				: targetItem.parentElement.parentElement;

			if (dropTargetColumn !== parentTargetItem) {
				if (dropTargetColumn) {
					dropTargetColumn.classList.remove(DROP_OVER_CLASS);
					dropTargetColumn.classList.remove(DROP_ACTIVE_VALID_CLASS);
				}
				setDropTargetColumn(parentTargetItem);

				if (targetItemIsDropzone) {
					parentTargetItem.classList.add(DROP_OVER_CLASS);
				}
				else if (validDrop) {
					parentTargetItem.classList.add(DROP_ACTIVE_VALID_CLASS);
				}
			}

			const dropPosition = getDropPosition({
				monitor,
				targetItem,
			});
			setTargetPosition(dropPosition);

			if (
				dropTargetItem !== targetItem ||
				dropPosition !== targetPosition
			) {
				setDropTargetItem(targetItem);

				const dropIndicatorPosition = getDropIndicatorPosition({
					monitor,
					targetItem,
					windowScrollPosition,
				});

				setDragIndicatorPosition(dropIndicatorPosition);
			}
		},
	});

	setDropTargetRef(targetItem);
}
