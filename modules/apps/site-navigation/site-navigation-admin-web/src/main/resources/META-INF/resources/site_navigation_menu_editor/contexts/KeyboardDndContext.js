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

import React, {useCallback, useContext, useState} from 'react';

import {NESTING_MARGIN} from '../constants/nestingMargin';
import getFlatItems from '../utils/getFlatItems';

const KeyboardDndContext = React.createContext();

const ROOT_ITEM_OFFSET_WIDTH = NESTING_MARGIN / 2;

export function KeyboardDndProvider({children}) {
	const [dragLayer, setDragLayer] = useState(null);

	return (
		<KeyboardDndContext.Provider value={{dragLayer, setDragLayer}}>
			{children}
		</KeyboardDndContext.Provider>
	);
}

export function useDragLayer() {
	return useContext(KeyboardDndContext).dragLayer;
}

export function useSetDragLayer() {
	const {setDragLayer} = useContext(KeyboardDndContext);

	return useCallback(
		(items, nextDragLayer) => {
			if (!nextDragLayer) {
				setDragLayer(null);

				return;
			}

			// Try to put the placeholder next to the corresponding sibling

			const siblingsItems = getFlatItems(items).filter(
				(item) =>
					item.parentSiteNavigationMenuItemId ===
					nextDragLayer.parentSiteNavigationMenuItemId
			);

			const siblingItem = siblingsItems[nextDragLayer.order];

			if (siblingItem) {
				const siblingElement = document.querySelector(
					`[data-item-id="${siblingItem.siteNavigationMenuItemId}"]`
				);

				const siblingElementRect = siblingElement.getBoundingClientRect();

				setDragLayer({
					...nextDragLayer,
					currentOffset: {
						x: siblingElementRect.x,
						y: siblingElementRect.y,
					},
				});

				return;
			}

			// Otherwise place it at the very end of it's parent

			const parentElement = document.querySelector(
				`[data-item-id="${nextDragLayer.parentSiteNavigationMenuItemId}"]`
			);

			if (!parentElement) {
				setDragLayer(nextDragLayer);

				return;
			}

			const parentElementRect = parentElement.getBoundingClientRect();

			const parentElementAriaLevel = parseInt(
				parentElement.getAttribute('aria-level'),
				10
			);

			// Traverse the DOM until we find and element with same or less
			// aria-level than our parent.

			let nextChildNode = parentElement.nextElementSibling;

			while (nextChildNode) {
				const childNodeAriaLevel = parseInt(
					nextChildNode.getAttribute('aria-level'),
					10
				);

				if (childNodeAriaLevel <= parentElementAriaLevel) {
					break;
				}

				nextChildNode = nextChildNode.nextElementSibling;
			}

			setDragLayer({
				...nextDragLayer,
				currentOffset: {
					x:
						nextDragLayer.parentSiteNavigationMenuItemId === '0'
							? parentElementRect.x + ROOT_ITEM_OFFSET_WIDTH
							: parentElementRect.x + NESTING_MARGIN,
					y: nextChildNode
						? nextChildNode.getBoundingClientRect().y
						: parentElementRect.bottom,
				},
			});
		},
		[setDragLayer]
	);
}
