/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	PropsWithChildren,
	RefObject,
	SetStateAction,
	createContext,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {
	DRAG_OVER_POSITIONS,
	DragOverPosition,
} from '../../config/constants/dragOverPositions';
import {Item} from './Item';

interface Context {
	dragOverPosition: DragOverPosition | null;
	itemElementMap: Map<string, HTMLDivElement | null>;
	itemListRef: RefObject<Item[]>;
	setDragOverPosition: Dispatch<SetStateAction<DragOverPosition | null>>;
	setSourceItem: Dispatch<SetStateAction<Item | null>>;
	setTargetItem: Dispatch<SetStateAction<Item | null>>;
	sourceItem: Item | null;
	targetItem: Item | null;
}

const KeyboardDragAndDropContext = createContext<Context>({
	dragOverPosition: null,
	itemElementMap: new Map(),
	itemListRef: {current: []},
	setDragOverPosition: () => {},
	setSourceItem: () => {},
	setTargetItem: () => {},
	sourceItem: null,
	targetItem: null,
});

export function KeyboardDragAndDropContextProvider({
	children,
	itemList,
}: PropsWithChildren<{itemList: Item[]}>) {
	const [
		dragOverPosition,
		setDragOverPosition,
	] = useState<DragOverPosition | null>(null);
	const itemElementMap: Context['itemElementMap'] = useMemo(
		() => new Map(),
		[]
	);
	const [sourceItem, setSourceItem] = useState<Item | null>(null);
	const [targetItem, setTargetItem] = useState<Item | null>(null);

	const itemListRef = useRef(itemList);
	itemListRef.current = itemList;

	const contextValue: Context = {
		dragOverPosition,
		itemElementMap,
		itemListRef,
		setDragOverPosition,
		setSourceItem,
		setTargetItem,
		sourceItem,
		targetItem,
	};

	useEffect(() => {
		if (!targetItem) {
			return;
		}

		const targetElement = itemElementMap.get(targetItem.id);

		if (!targetElement) {
			return;
		}

		targetElement.scrollIntoView({
			behavior: 'smooth',
			block: 'center',
		});
	}, [itemElementMap, dragOverPosition, targetItem]);

	return (
		<KeyboardDragAndDropContext.Provider value={contextValue}>
			{children}
		</KeyboardDragAndDropContext.Provider>
	);
}

export function useKeyboardDragItem(
	item: Item,
	onDropItem: (
		itemId: string,
		index: number,
		dragOverPosition: DragOverPosition
	) => void
) {
	const {
		dragOverPosition,
		itemElementMap,
		itemListRef,
		setDragOverPosition,
		setSourceItem,
		setTargetItem,
		sourceItem,
		targetItem,
	} = useContext(KeyboardDragAndDropContext);

	const dragOverPositionRef = useRef(dragOverPosition);
	const handlerRef = useRef<HTMLButtonElement | null>(null);
	const onDropItemRef = useRef(onDropItem);
	const sourceItemRef = useRef(sourceItem);
	const targetItemRef = useRef(targetItem);

	dragOverPositionRef.current = dragOverPosition;
	onDropItemRef.current = onDropItem;
	sourceItemRef.current = sourceItem;
	targetItemRef.current = targetItem;

	const targetRef = useCallback(
		(element: HTMLDivElement | null) => {
			itemElementMap.set(item.id, element);
		},
		[item.id, itemElementMap]
	);

	useEffect(() => {
		const button = handlerRef.current;

		if (!button) {
			return;
		}

		const onKeyUp = (event: KeyboardEvent) => {
			if (
				['ArrowDown', 'ArrowUp', 'Enter', 'Escape', ' '].includes(
					event.key
				)
			) {
				event.preventDefault();
				event.stopImmediatePropagation();
			}
			else {
				return;
			}

			if (event.key === 'Escape') {
				document.activeElement?.scrollIntoView({
					behavior: 'smooth',
					block: 'center',
				});

				setDragOverPosition(null);
				setSourceItem(null);
				setTargetItem(null);

				return;
			}

			const itemList = itemListRef.current;
			const position = dragOverPositionRef.current;
			const targetItem = targetItemRef.current;

			if (!itemList) {
				return;
			}

			if (event.key === 'ArrowUp' && targetItem) {
				const targetItemIndex = itemList.indexOf(targetItem);

				if (position === DRAG_OVER_POSITIONS.bottom) {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
				}
				else if (targetItemIndex > 0) {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
					setTargetItem(itemListRef.current[targetItemIndex - 1]);
				}
			}
			else if (event.key === 'ArrowDown' && targetItem) {
				const targetItemIndex = itemList.indexOf(targetItem);

				if (targetItemIndex < itemList.length - 1) {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
					setTargetItem(itemListRef.current[targetItemIndex + 1]);
				}
				else if (position === DRAG_OVER_POSITIONS.top) {
					setDragOverPosition(DRAG_OVER_POSITIONS.bottom);
				}
			}
			else if (event.key === 'Enter' || event.key === ' ') {
				if (sourceItemRef.current === item) {
					const dragOverPosition = dragOverPositionRef.current;
					const itemList = itemListRef.current;
					const onDropItem = onDropItemRef.current;
					const targetItem = targetItemRef.current;

					if (
						!dragOverPosition ||
						!itemList ||
						!onDropItem ||
						!targetItem
					) {
						return;
					}

					const targetIndex = itemList.indexOf(targetItem);

					if (targetIndex < 0) {
						return;
					}

					onDropItem(item.id, targetIndex, dragOverPosition);

					setDragOverPosition(null);
					setSourceItem(null);
					setTargetItem(null);
				}
				else {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
					setSourceItem(item);
					setTargetItem(item);
				}
			}
		};

		const onBlur = () => {
			setDragOverPosition(null);
			setSourceItem(null);
			setTargetItem(null);
		};

		button.addEventListener('blur', onBlur);
		button.addEventListener('keyup', onKeyUp);

		return () => {
			button.removeEventListener('blur', onBlur);
			button.removeEventListener('keyup', onKeyUp);
		};
	}, [item, itemListRef, setDragOverPosition, setSourceItem, setTargetItem]);

	return {
		dragOverPosition: targetItem === item ? dragOverPosition : null,
		handlerRef,
		isDragging: sourceItem === item,
		targetRef,
	};
}
