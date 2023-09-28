/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollectionItemLayoutDataItem} from './layout_data/CollectionItemLayoutDataItem';
import {CollectionLayoutDataItem} from './layout_data/CollectionLayoutDataItem';
import {ColumnLayoutDataItem} from './layout_data/ColumnLayoutDataItem';
import {ContainerLayoutDataItem} from './layout_data/ContainerLayoutDataItem';
import {DropZoneLayoutDataItem} from './layout_data/DropZoneLayoutDataItem';
import {FormLayoutDataItem} from './layout_data/FormLayoutDataItem';
import {FragmentDropZoneLayoutDataItem} from './layout_data/FragmentDropZoneLayoutDataItem';
import {FragmentLayoutDataItem} from './layout_data/FragmentLayoutDataItem';
import {RootLayoutDataItem} from './layout_data/RootLayoutDataItem';
import {RowLayoutDataItem} from './layout_data/RowLayoutDataItem';

export interface DeletedLayoutDataItem {
	childrenItemIds: string[];
	itemId: string;
	portletIds: string[];
	position: number;
}

export type LayoutDataItem =
	| CollectionItemLayoutDataItem
	| CollectionLayoutDataItem
	| ColumnLayoutDataItem
	| ContainerLayoutDataItem
	| DropZoneLayoutDataItem
	| FormLayoutDataItem
	| FragmentDropZoneLayoutDataItem
	| FragmentLayoutDataItem
	| RootLayoutDataItem
	| RowLayoutDataItem;

export interface LayoutData {
	deletedItems: DeletedLayoutDataItem[];
	items: Record<string, LayoutDataItem>;
	rootItems: {dropZone: string; main: string};
	version: string;
}
