/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {DragOverPosition} from '../../config/constants/dragOverPositions';
export interface Item {
	id: string;
	name: string;
}
interface ItemProps {
	index: number;
	item: Item;
	numberOfItems: number;
	onDropItem: (
		itemId: Item['id'],
		index: number,
		dragOverPosition?: DragOverPosition
	) => void;
}
export declare function Item({
	index,
	item,
	numberOfItems,
	onDropItem,
}: ItemProps): JSX.Element;
export declare function ReorderDropdown({
	index,
	item,
	numberOfItems,
	onDropItem,
}: ItemProps): JSX.Element;
export declare function useMouseDropTarget(
	itemId: string,
	itemIndex: number,
	onDropItem: ItemProps['onDropItem']
): {
	dragOverPosition: DragOverPosition | null | undefined;
	targetRef: (targetElement: any) => void;
};
export {};
