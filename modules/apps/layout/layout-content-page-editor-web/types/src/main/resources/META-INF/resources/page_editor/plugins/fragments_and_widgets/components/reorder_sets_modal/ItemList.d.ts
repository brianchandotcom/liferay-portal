/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TabId} from '../../config/constants/tabsIds';
import {Item} from './Item';
interface ItemListProps {
	items: Item[];
	listId: TabId;
	updateLists: (tabId: TabId, items: Item[]) => void;
}
export declare function ItemList({
	items: initialItems,
	listId,
	updateLists,
}: ItemListProps): JSX.Element;
export {};
