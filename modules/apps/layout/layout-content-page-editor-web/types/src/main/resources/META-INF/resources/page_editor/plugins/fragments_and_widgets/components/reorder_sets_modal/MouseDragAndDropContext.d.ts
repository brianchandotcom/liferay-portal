/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DragOverPosition} from '../../config/constants/dragOverPositions';
import {Item} from './Item';
export declare function useMouseDragItem(
	item: Item
): {
	handlerRef: import('react-dnd').ConnectDragSource;
	isDragging: boolean;
};
export declare function useMouseDropTarget(
	itemId: string,
	itemIndex: number,
	onDropItem: (
		itemId: string,
		index: number,
		dragOverPosition?: DragOverPosition
	) => void
): {
	dragOverPosition: DragOverPosition | null | undefined;
	targetRef: (targetElement: any) => void;
};
