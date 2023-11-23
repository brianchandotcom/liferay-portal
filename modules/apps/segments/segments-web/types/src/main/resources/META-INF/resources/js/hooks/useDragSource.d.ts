/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type Item = {
	type: string;
	[key: string]: any;
};
export default function useDragSource({
	item,
}: {
	item: Item;
}): {
	handlerRef: import('react-dnd').ConnectDragSource;
	isDragging: boolean;
};
export {};
