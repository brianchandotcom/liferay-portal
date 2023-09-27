/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TEmptyState} from './StateRenderer';
import {TColumn, TFormattedItems, TItem, TTableRequestParams} from './types';
export interface ITableProps<TRawItem> {
	addItemTitle?: string;
	columns: TColumn[];
	disabled?: boolean;
	emptyState: TEmptyState;
	mapperItems: (items: TRawItem[]) => TItem[];
	onAddItem?: () => void;
	onItemsChange?: (items: TFormattedItems) => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	showCheckbox?: boolean;
}
export declare function Table<TRawItem>({
	addItemTitle,
	columns,
	disabled,
	emptyState,
	mapperItems,
	onAddItem,
	onItemsChange,
	requestFn,
	showCheckbox,
}: ITableProps<TRawItem>): JSX.Element;
declare function ComposedTable<TRawItem>(
	props: ITableProps<TRawItem>
): JSX.Element;
export default ComposedTable;
