/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useRef, useState} from 'react';
import {DragObjectWithType, useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {config} from '../../../../app/config/index';
import {
	DRAG_OVER_POSITIONS,
	DragOverPosition,
} from '../../config/constants/dragOverPositions';
import {Item} from './Item';

const ACCEPTING_ITEM_TYPE = 'acceptingItemType';

export function useMouseDragItem(item: Item) {
	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {},
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			...item,
			namespace: config.portletNamespace,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDragging,
	};
}

export function useMouseDropTarget(
	itemId: string,
	itemIndex: number,
	onDropItem: (
		itemId: string,
		index: number,
		dragOverPosition?: DragOverPosition
	) => void
) {
	const [dragOverPosition, setDragOverPosition] = useState<
		DragOverPosition | undefined
	>();
	const targetRef = useRef<HTMLElement | null>(null);
	const targetRectRef = useRef<DOMRect | null>(null);

	const [{isOver}, internalSetTargetRef] = useDrop<
		DragObjectWithType & Item,
		void,
		{isOver: boolean}
	>({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(sourceItem, monitor) {
			return sourceItem.id !== itemId && monitor.isOver();
		},
		collect(monitor) {
			return {
				isOver: monitor.isOver(),
			};
		},
		drop(source, monitor) {
			targetRectRef.current = null;

			if (monitor.canDrop()) {
				onDropItem(source.id, itemIndex, dragOverPosition);
			}
		},
		hover(source, monitor) {
			if (!monitor.isOver()) {
				targetRectRef.current = null;

				return;
			}

			targetRectRef.current =
				targetRectRef.current ||
				targetRef.current!.getBoundingClientRect();

			const targetMiddlePosition =
				targetRectRef.current!.top + targetRectRef.current!.height / 2;

			if (monitor.getClientOffset()!.y < targetMiddlePosition) {
				setDragOverPosition(DRAG_OVER_POSITIONS.top);
			}
			else {
				setDragOverPosition(DRAG_OVER_POSITIONS.bottom);
			}
		},
	});

	const setTargetRef = useCallback(
		(targetElement) => {
			internalSetTargetRef(targetElement);
			targetRef.current = targetElement;
		},
		[internalSetTargetRef]
	);

	return {
		dragOverPosition: isOver ? dragOverPosition : null,
		targetRef: setTargetRef,
	};
}
