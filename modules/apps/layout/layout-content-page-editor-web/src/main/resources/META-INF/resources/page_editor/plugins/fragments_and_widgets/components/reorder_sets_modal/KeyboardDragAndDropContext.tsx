/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {
	Dispatch,
	KeyboardEventHandler,
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
import {flushSync} from 'react-dom';

import {
	DRAG_OVER_POSITIONS,
	DRAG_OVER_POSITIONS_LABELS,
	DragOverPosition,
} from '../../config/constants/dragOverPositions';
import {Item} from './Item';

interface Context {
	dragOverPosition: DragOverPosition | null;
	itemElementMap: Map<string, HTMLDivElement | null>;
	itemHandlerMap: Map<string, HTMLButtonElement | null>;
	itemListRef: RefObject<Item[]>;
	sendMessage: (message: string) => void;
	setDragOverPosition: Dispatch<SetStateAction<DragOverPosition | null>>;
	setSourceItem: Dispatch<SetStateAction<Item | null>>;
	setTargetItem: Dispatch<SetStateAction<Item | null>>;
	sourceItem: Item | null;
	targetItem: Item | null;
}

const KeyboardDragAndDropContext = createContext<Context>({
	dragOverPosition: null,
	itemElementMap: new Map(),
	itemHandlerMap: new Map(),
	itemListRef: {current: []},
	sendMessage: () => {},
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
	const itemHandlerMap: Context['itemHandlerMap'] = useMemo(
		() => new Map(),
		[]
	);
	const itemListElementRef = useRef<HTMLDivElement | null>(null);
	const [sourceItem, setSourceItem] = useState<Item | null>(null);
	const [targetItem, setTargetItem] = useState<Item | null>(null);
	const [text, setText] = useState('');

	const itemListRef = useRef(itemList);
	itemListRef.current = itemList;

	const clearMessageTimeoutRef = useRef<NodeJS.Timeout>();

	const sendMessage = useCallback((message: string) => {
		setText(message);

		if (clearMessageTimeoutRef.current) {
			clearTimeout(clearMessageTimeoutRef.current);

			clearMessageTimeoutRef.current = undefined;
		}

		if (message) {
			clearMessageTimeoutRef.current = setTimeout(() => {
				setText('');
			}, 1000);
		}
	}, []);

	const contextValue: Context = {
		dragOverPosition,
		itemElementMap,
		itemHandlerMap,
		itemListRef,
		sendMessage,
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

	const onKeyDown: KeyboardEventHandler = (event) => {
		if (
			sourceItem ||
			(event.key !== 'ArrowDown' && event.key !== 'ArrowUp')
		) {
			return;
		}

		event.preventDefault();

		const currentItemId =
			document.activeElement instanceof HTMLButtonElement
				? document.activeElement.dataset.itemId
				: null;

		const currentItemIndex = itemList.findIndex(
			(item) => item.id === currentItemId
		);

		if (event.key === 'ArrowDown') {
			const nextItem = itemList[currentItemIndex + 1];

			const nextItemHandler = nextItem
				? itemHandlerMap.get(nextItem.id)
				: itemHandlerMap.get(itemList[0].id);

			if (nextItemHandler) {
				nextItemHandler.focus();
			}
		}
		else if (event.key === 'ArrowUp') {
			const previousItem = itemList[currentItemIndex - 1];

			const previousItemHandler = previousItem
				? itemHandlerMap.get(previousItem.id)
				: itemHandlerMap.get(itemList[itemList.length - 1].id);

			if (previousItemHandler) {
				previousItemHandler.focus();
			}
		}
	};

	return (
		<KeyboardDragAndDropContext.Provider value={contextValue}>
			<span aria-live="assertive" className="sr-only">
				{text}
			</span>

			<div
				aria-orientation="vertical"
				className="c-p-4"
				onKeyDown={onKeyDown}
				ref={itemListElementRef}
				role="list"
				tabIndex={Liferay.FeatureFlags['LPS-196420'] ? 0 : -1}
			>
				{children}
			</div>
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
		itemHandlerMap,
		itemListRef,
		sendMessage,
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

	const updateHandlerRef = useCallback(
		(element: HTMLButtonElement | null) => {
			handlerRef.current = element;
			itemHandlerMap.set(item.id, element);
		},
		[item.id, itemHandlerMap]
	);

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

		const onKeyDown = (event: KeyboardEvent) => {
			if (
				['ArrowDown', 'ArrowUp', 'Enter', 'Escape', ' '].includes(
					event.key
				)
			) {
				event.preventDefault();
			}
		};

		const onKeyUp = (event: KeyboardEvent) => {
			const itemList = itemListRef.current;
			const position = dragOverPositionRef.current;
			const targetItem = targetItemRef.current;

			if (event.key === 'Escape' && targetItem) {
				event.stopImmediatePropagation();

				document.activeElement?.scrollIntoView({
					behavior: 'smooth',
					block: 'center',
				});

				setDragOverPosition(null);
				setSourceItem(null);
				setTargetItem(null);

				return;
			}

			if (!itemList) {
				return;
			}

			if (event.key === 'ArrowUp' && targetItem) {
				event.stopImmediatePropagation();

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
				event.stopImmediatePropagation();

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
				event.stopImmediatePropagation();

				if (sourceItemRef.current === item) {
					const onDropItem = onDropItemRef.current;

					if (!position || !onDropItem || !targetItem) {
						return;
					}

					const targetIndex = itemList.indexOf(targetItem);

					if (targetIndex < 0) {
						return;
					}

					flushSync(() => {
						sendMessage(
							sub(Liferay.Language.get('x-placed-on-x-of-x'), [
								item.name,
								DRAG_OVER_POSITIONS_LABELS[position],
								targetItem.name,
							])
						);
					});

					onDropItem(item.id, targetIndex, position);
					setDragOverPosition(null);
					setSourceItem(null);
					setTargetItem(null);
				}
				else {
					flushSync(() => {
						sendMessage(
							sub(
								Liferay.Language.get(
									'use-up-and-down-arrows-to-move-the-set-and-press-enter-to-place-it-in-desired-position.-currently-targeting-x-of-x'
								),
								[DRAG_OVER_POSITIONS_LABELS.top, item.name]
							)
						);
					});

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
		button.addEventListener('keydown', onKeyDown);
		button.addEventListener('keyup', onKeyUp);

		return () => {
			button.removeEventListener('blur', onBlur);
			button.removeEventListener('keydown', onKeyDown);
			button.removeEventListener('keyup', onKeyUp);
		};
	}, [
		item,
		itemListRef,
		sendMessage,
		setDragOverPosition,
		setSourceItem,
		setTargetItem,
	]);

	useEffect(() => {
		if (dragOverPosition && sourceItem && targetItem) {
			sendMessage(
				sub(Liferay.Language.get('targeting-x-of-x'), [
					DRAG_OVER_POSITIONS_LABELS[dragOverPosition],
					targetItem.name,
				])
			);
		}
	}, [dragOverPosition, sendMessage, sourceItem, targetItem]);

	return {
		dragOverPosition: targetItem === item ? dragOverPosition : null,
		handlerRef: updateHandlerRef,
		isDragging: sourceItem === item,
		targetRef,
	};
}
