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

const KeyboardDndContext = React.createContext();

const ITEM_CARD_HEIGHT = 87;

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
		(nextDragLayer) => {
			if (!nextDragLayer) {
				setDragLayer(null);

				return;
			}

			const parentElement = document.querySelector(
				`[data-item-id="${nextDragLayer.parentSiteNavigationMenuItemId}"]`
			);

			if (!parentElement) {
				setDragLayer(nextDragLayer);

				return;
			}

			const parentElementRect = parentElement.getBoundingClientRect();

			const currentOffset = {
				x:
					parentElementRect.x +
					(nextDragLayer.parentSiteNavigationMenuItemId === '0'
						? ROOT_ITEM_OFFSET_WIDTH
						: NESTING_MARGIN),
				y:
					parentElementRect.y +
					(nextDragLayer.parentSiteNavigationMenuItemId === '0' &&
					nextDragLayer.order === 0
						? 0
						: (nextDragLayer.order + 1) * ITEM_CARD_HEIGHT),
			};

			setDragLayer({
				...nextDragLayer,
				currentOffset,
			});
		},
		[setDragLayer]
	);
}
