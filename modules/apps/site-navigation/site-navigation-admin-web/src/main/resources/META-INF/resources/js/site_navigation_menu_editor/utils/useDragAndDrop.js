/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import {useItems} from '../contexts/ItemsContext';
import getFlatItems from './getFlatItems';
import getItemPath from './getItemPath';

const DragDropContext = React.createContext({});

export function DragDropProvider({children}) {
	const [nestingLevel, setNestingLevel] = useState(0);
	const [order, setOrder] = useState(null);
	const [targetItemId, setTargetItemId] = useState(null);

	const dragDropValues = {
		nestingLevel,
		order,
		setNestingLevel,
		setOrder,
		setTargetItemId,
		targetItemId,
	};

	return (
		<DragDropContext.Provider value={dragDropValues}>
			{children}
		</DragDropContext.Provider>
	);
}

export function useDragItem(item, onDragEnd) {
	const {siteNavigationMenuItemId} = item;

	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);

	const {portletNamespace: namespace} = useConstants();

	const {setTargetItemId, targetItemId} = useContext(DragDropContext);

	const [{isDragging}, handlerRef, previewRef] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end(_, monitor) {
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
	const {siteNavigationMenuItemId} = item;

	const cardWidthRef = useRef();
	const nextItemNestingRef = useRef(null);
	const items = useItems();
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const {languageId} = useConstants();
	const {
		nestingLevel,
		setNestingLevel,
		setTargetItemId,
		targetItemId,
	} = useContext(DragDropContext);
	const previousItemPathRef = useRef(null);
	const targetRef = useRef();
	const targetRectRef = useRef(null);

	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const isFirstItem =
		itemPath.length === 1 &&
		items[0]?.siteNavigationMenuItemId === siteNavigationMenuItemId;

	const [, dndTargetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(_, monitor) {
			return monitor.isOver();
		},
		drop() {
			if (targetItemId === '0') {
				return {
					order: 0,
					parentId: '0',
				};
			}

			const targetItemPath = getItemPath(targetItemId, items);

			if (targetItemPath.length < nestingLevel) {
				return {
					order: 0,
					parentId: targetItemId,
				};
			}

			const childPath = targetItemPath.slice(0, nestingLevel);

			const childId = childPath[childPath.length - 1];
			const parentId = targetItemPath[childPath.length - 2] || '0';

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
				if (!targetRef.current) {
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
				const targetRect = targetRectRef.current;

				if (
					isFirstItem &&
					itemPosition.y < targetRect.top + targetRect.height * 0.25
				) {
					setTargetItemId('0');

					return;
				}

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

				if (source.id === siteNavigationMenuItemId) {
					previousItemPathRef.current =
						previousItemPathRef.current ||
						(() => {
							const flatItems = getFlatItems(items);

							const itemIndex = flatItems.findIndex(
								(otherItem) =>
									otherItem.siteNavigationMenuItemId ===
									siteNavigationMenuItemId
							);

							const previousItemId =
								flatItems[itemIndex - 1]
									?.siteNavigationMenuItemId || null;

							if (previousItemId) {
								return getItemPath(previousItemId, items);
							}

							return null;
						})();

					if (previousItemPathRef.current) {
						setNestingLevel(
							Math.max(
								nextItemNestingRef.current,
								Math.min(
									previousItemPathRef.current.length + 1,
									nesting
								)
							)
						);

						setTargetItemId(
							previousItemPathRef.current[
								previousItemPathRef.current.length - 1
							]
						);
					}
				}
				else if (itemPath.includes(source.id)) {
					setNestingLevel(0);
					setTargetItemId(null);
				}
				else {
					setNestingLevel(
						Math.max(
							nextItemNestingRef.current,
							Math.min(itemPath.length + 1, nesting)
						)
					);

					setTargetItemId(siteNavigationMenuItemId);
				}
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

	useEffect(() => {
		cardWidthRef.current = null;
		nextItemNestingRef.current = null;
		previousItemPathRef.current = null;
		targetRectRef.current = null;
	}, [targetItemId]);

	return {
		isOver: targetItemId === siteNavigationMenuItemId,
		isOverFirstItem: isFirstItem && targetItemId === '0',
		nestingLevel:
			targetItemId === siteNavigationMenuItemId ? nestingLevel : 0,
		targetRef: updateTargetRef,
	};
}
