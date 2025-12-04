/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import usePointerDragAndDrop from './usePointerDragAndDrop';

import './useDragAndDrop.scss';

export type DropPosition = 'bottom' | 'top' | null;

interface Props<T> {
	dragHandlerRef: React.RefObject<HTMLElement>;
	dropItemRef: React.RefObject<HTMLElement>;
	item: T;
	items: T[];
}

export default function useDragAndDrop<T extends {id: string; name: string}>({
	dragHandlerRef,
	dropItemRef,
	item,
	items,
}: Props<T>) {
	const {
		isPointerDragging,
		isPointerDropBottomPosition,
		isPointerDropTopPosition,
	} = usePointerDragAndDrop<T>({
		dragHandlerRef,
		dropItemRef,
		items,
		targetItem: item,
	});

	return {
		isDragging: isPointerDragging,
		isDropBottomPosition: isPointerDropBottomPosition,
		isDropTopPosition: isPointerDropTopPosition,
	};
}
