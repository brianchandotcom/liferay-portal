/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutDataItemType} from '../app/config/constants/layoutDataItemTypes';

export interface DeletedLayoutDataItem {
	childrenItemIds: string[];
	itemId: string;
	portletIds: string[];
	position: number;
}

export interface LayoutDataItem {
	children: string[];
	config: Record<string, unknown>;
	itemId: string;
	itemType: LayoutDataItemType;
	parentId: string;
}

export interface LayoutData {
	deletedItems: DeletedLayoutDataItem[];
	items: Record<string, LayoutDataItem>;
	rootItems: {dropZone: string; main: string};
	version: string;
}
