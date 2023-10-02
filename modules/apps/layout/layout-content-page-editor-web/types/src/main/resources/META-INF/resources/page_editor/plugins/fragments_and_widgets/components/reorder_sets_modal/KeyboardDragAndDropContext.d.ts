/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropsWithChildren} from 'react';
import {DragOverPosition} from '../../config/constants/dragOverPositions';
import {Item} from './Item';
export declare function KeyboardDragAndDropContextProvider({
	children,
	itemList,
}: PropsWithChildren<{
	itemList: Item[];
}>): JSX.Element;
export declare function useKeyboardDragItem(
	item: Item,
	onDropItem: (
		itemId: string,
		index: number,
		dragOverPosition: DragOverPosition
	) => void
): {
	dragOverPosition: DragOverPosition | null;
	handlerRef: (element: HTMLButtonElement | null) => void;
	isDragging: boolean;
	targetRef: (element: HTMLDivElement | null) => void;
};
