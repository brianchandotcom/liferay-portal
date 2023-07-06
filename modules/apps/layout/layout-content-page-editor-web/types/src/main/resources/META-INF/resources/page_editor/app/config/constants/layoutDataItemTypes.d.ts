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
