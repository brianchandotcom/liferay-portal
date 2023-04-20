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
	useRef,
	useState,
} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {ACCEPTING_ITEM_TYPE} from '../constants/acceptingItemType';
import {NESTING_MARGIN} from '../constants/nestingMargin';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import updateItemParent from '../utils/updateItemParent';
import getDescendantsCount from './getDescendantsCount';
import getFlatItems from './getFlatItems';
import getItemPath from './getItemPath';
import moveItem from './moveItem';

const DIRECTIONS = {
	down: 'down',
	inside: 'inside',
	outside: 'outside',
	up: 'up',
};

const DragDropContext = React.createContext({});

export function DragDropProvider({children}) {
	const [parentId, setParentId] = useState(null);
	const [horizontalOffset, setHorizontalOffset] = useState(0);
	const [order, setOrder] = useState(null);
	const [targetItemId, setTargetItemId] = useState(null);
	const [verticalOffset, setVerticalOffset] = useState(0);

	const dragDropValues = {
		horizontalOffset,
		order,
		parentId,
		setHorizontalOffset,
		setOrder,
		setParentId,
		setTargetItemId,
		setVerticalOffset,
		targetItemId,
		verticalOffset,
	};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
}

export function useDragItem(item, onDragEnd) {
	const {parentSiteNavigationMenuItemId, siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const {portletNamespace: namespace} = useConstants();

	const {
		parentId,
		setHorizontalOffset,
		setParentId,
		setTargetItemId,
		setVerticalOffset,
		targetItemId,
	} = useContext(DragDropContext);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {
			if (!Liferay.FeatureFlags['LPS-134527']) {
				setParentId(parentSiteNavigationMenuItemId);
			}
		},
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end(_, monitor) {
			if (Liferay.FeatureFlags['LPS-134527']) {
				if (!targetItemId) {
					return;
				}

				const dropResult = monitor.getDropResult();

				setTargetItemId(null);

				if (!dropResult) {
					return;
				}

				onDragEnd(
					item.siteNavigationMenuItemId,
					dropResult.parentId,
					dropResult.order
				);
			}
			else {
				onDragEnd(item.siteNavigationMenuItemId, parentId, null);

				setHorizontalOffset(0);
				setParentId(null);
				setVerticalOffset(null);
			}
		},
		isDragging(monitor) {
			return itemPath.includes(monitor.getItem().id);
		},
		item: {
			id: siteNavigationMenuItemId,
			namespace,
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

export function useDropTarget(item) {
	if (Liferay.FeatureFlags['LPS-134527']) {
		// eslint-disable-next-line react-hooks/rules-of-hooks
		return useNewDropTarget(item);
	}
	else {
		// eslint-disable-next-line react-hooks/rules-of-hooks
		return useOldDropTarget(item);
	}
}

function useNewDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const cardWidthRef = useRef();
	const [nestingLevel, setNestingLevel] = useState(0);
	const nextItemNestingRef = useRef(null);
	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const targetRef = useRef();
	const targetRectRef = useRef(null);

	const {languageId} = useConstants();
	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const {setTargetItemId, targetItemId} = useContext(DragDropContext);

	const [, dndTargetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(_, monitor) {
			return monitor.isOver();
		},
		drop() {
			const lastNestingLevel = nestingLevel;

			cardWidthRef.current = null;
			nextItemNestingRef.current = null;
			targetRectRef.current = null;

			if (itemPath.length < lastNestingLevel) {
				return {
					order: 0,
					parentId: itemPath[itemPath.length - 1],
				};
			}

			const childPath = itemPath.slice(0, nestingLevel);

			const childId = childPath[childPath.length - 1];
			const parentId = itemPath[childPath.length - 2] || '0';

			const children = items.filter(
				(item) => item.parentSiteNavigationMenuItemId === parentId
			);

			const order = children.findIndex(
				(item) => item.siteNavigationMenuItemId === childId
			);

			return {
				order: order === -1 ? children.length : order + 1,
				parentId,
			};
		},
		hover(source, monitor) {
			if (monitor.canDrop(source, monitor)) {
				if (!targetRef.current || itemPath.includes(source.id)) {
					setTargetItemId(null);

					return;
				}

				cardWidthRef.current =
					cardWidthRef.current ||
					targetRef.current
						.querySelector('.card')
						.getBoundingClientRect().width;

				targetRectRef.current =
					targetRectRef.current ||
					targetRef.current.getBoundingClientRect();

				nextItemNestingRef.current =
					nextItemNestingRef.current ||
					(() => {
						const flatItems = getFlatItems(items);

						const itemIndex = flatItems.findIndex(
							(otherItem) =>
								otherItem.siteNavigationMenuItemId ===
								siteNavigationMenuItemId
						);

						for (let i = itemIndex + 1; i < flatItems.length; i++) {
							const nextItem = flatItems[i];

							const nextItemPath = getItemPath(
								nextItem.siteNavigationMenuItemId,
								items
							);

							if (!nextItemPath.includes(source.id)) {
								return nextItemPath.length;
							}
						}

						return 1;
					})();

				const itemPosition = monitor.getSourceClientOffset();
				const nextItemNesting = nextItemNestingRef.current;
				const targetRect = targetRectRef.current;

				setTargetItemId(siteNavigationMenuItemId);

				let nesting = 1;

				if (rtl) {
					nesting =
						Math.round(
							(targetRect.right -
								(itemPosition.x + cardWidthRef.current)) /
								NESTING_MARGIN
						) + 1;
				}
				else {
					nesting =
						Math.round(
							(itemPosition.x - targetRect.left) / NESTING_MARGIN
						) + 1;
				}

				setNestingLevel(
					Math.max(
						nextItemNesting,
						Math.min(itemPath.length + 1, nesting)
					)
				);
			}
		},
	});

	const updateTargetRef = useCallback(
		(nextTargetElement) => {
			dndTargetRef(nextTargetElement);
			targetRef.current = nextTargetElement;
		},
		[dndTargetRef]
	);

	return {
		isOver: targetItemId === siteNavigationMenuItemId,
		nestingLevel:
			targetItemId === siteNavigationMenuItemId ? nestingLevel : 0,
		targetRef: updateTargetRef,
	};
}

function useOldDropTarget(item) {
	const {siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const setItems = useSetItems();

	const {languageId} = useConstants();
	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const {
		horizontalOffset,
		setHorizontalOffset,
		setParentId,
		setVerticalOffset,
		verticalOffset,
	} = useContext(DragDropContext);

	const [, targetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(source, monitor) {
			return monitor.isOver();
		},
		hover(source, monitor) {
			if (monitor.canDrop(source, monitor)) {
				if (itemPath.includes(source.id)) {
					const data = computeHoverItself({
						initialOffset: horizontalOffset,
						items,
						monitor,
						rtl,
						source,
					});

					if (data) {
						const {currentOffset, newParentId} = data;

						setParentId(newParentId);
						setHorizontalOffset(currentOffset);

						const newItems = updateItemParent(
							items,
							source.id,
							newParentId
						);

						setItems(newItems);
					}
				}
				else {
					const {
						currentOffset,
						direction,
						newIndex,
						newParentId,
					} = computeHoverAnotherItem({
						initialOffset: verticalOffset,
						items,
						monitor,
						source,
						targetId: siteNavigationMenuItemId,
					});

					if (newParentId) {
						setParentId(newParentId);
						setHorizontalOffset(0);
						setVerticalOffset(currentOffset);

						const newItems = moveItem(
							items,
							source.id,
							newParentId,
							newIndex,
							direction
						);

						setItems(newItems);
					}
				}
			}
		},
	});

	return {
		targetRef,
	};
}

function getHorizontalMovementDirection(initialOffset, currentOffset, rtl) {
	if (rtl) {
		return initialOffset < currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
	else {
		return initialOffset > currentOffset
			? DIRECTIONS.outside
			: DIRECTIONS.inside;
	}
}

function computeHoverItself({initialOffset, items, monitor, rtl, source}) {
	const currentOffset = monitor.getDifferenceFromInitialOffset().x;

	if (Math.abs(initialOffset - currentOffset) < NESTING_MARGIN) {
		return;
	}

	const direction = getHorizontalMovementDirection(
		initialOffset,
		currentOffset,
		rtl
	);

	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const sourceItemIndex = items.indexOf(sourceItem);

	let newParentId;

	if (direction === DIRECTIONS.inside) {
		const previousSibling = items
			.filter(
				(item, index) =>
					item.parentSiteNavigationMenuItemId ===
						sourceItem.parentSiteNavigationMenuItemId &&
					item.siteNavigationMenuItemId !== source.id &&
					index < sourceItemIndex
			)
			.pop();

		newParentId = previousSibling?.siteNavigationMenuItemId;
	}
	else {
		const nextSiblings = items.filter(
			(item, index) =>
				item.parentSiteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId &&
				item.siteNavigationMenuItemId !== source.id &&
				index > sourceItemIndex
		);

		if (!nextSiblings.length) {
			const parent = items.find(
				(item) =>
					item.siteNavigationMenuItemId ===
					sourceItem.parentSiteNavigationMenuItemId
			);

			newParentId = parent?.parentSiteNavigationMenuItemId;
		}
	}

	if (
		!newParentId ||
		newParentId === sourceItem.siteNavigationMenuItemId ||
		itemIsDynamic(newParentId, items)
	) {
		return;
	}

	return {currentOffset, newParentId};
}

function computeHoverAnotherItem({
	initialOffset,
	items,
	monitor,
	source,
	targetId,
}) {
	const sourceItem = items.find(
		(item) => item.siteNavigationMenuItemId === source.id
	);
	const targetItem = items.find(
		(item) => item.siteNavigationMenuItemId === targetId
	);

	const currentOffset = monitor.getDifferenceFromInitialOffset().y;

	if (initialOffset === currentOffset) {
		return;
	}

	const direction =
		initialOffset > currentOffset ? DIRECTIONS.up : DIRECTIONS.down;

	const newIndex = items.indexOf(targetItem);

	if (newIndex === items.indexOf(sourceItem)) {
		return;
	}

	let newParentId;

	if (direction === DIRECTIONS.up) {
		newParentId = targetItem.parentSiteNavigationMenuItemId;
	}

	if (direction === DIRECTIONS.down) {
		const targetItemDescendantsCount = getDescendantsCount(items, targetId);

		newParentId = targetItemDescendantsCount
			? targetItem.siteNavigationMenuItemId
			: targetItem.parentSiteNavigationMenuItemId;
	}

	if (itemIsDynamic(newParentId, items)) {
		return;
	}

	return {
		currentOffset,
		direction,
		newIndex,
		newParentId,
	};
}

function itemIsDynamic(siteNavigationMenuItemId, items) {
	const item = items.find(
		(item) => item.siteNavigationMenuItemId === siteNavigationMenuItemId
	);

	return item?.dynamic;
}
