/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const LAYOUT_DATA_ITEM_TYPES: {
	readonly collection: 'collection';
	readonly collectionItem: 'collection-item';
	readonly column: 'column';
	readonly container: 'container';
	readonly dropZone: 'drop-zone';
	readonly form: 'form';
	readonly fragment: 'fragment';
	readonly fragmentDropZone: 'fragment-drop-zone';
	readonly root: 'root';
	readonly row: 'row';
};
export declare type LayoutDataItemType = typeof LAYOUT_DATA_ITEM_TYPES[keyof typeof LAYOUT_DATA_ITEM_TYPES];
