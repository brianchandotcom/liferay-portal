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

/// <reference types="react" />

import {TColumn, TFormattedItems, TItem, TTableRequestParams} from './types';
interface ITableProps<TRawItem> {
	addItemTitle?: string;
	columns: TColumn[];
	disabled?: boolean;
	emptyStateTitle: string;
	mapperItems: (items: TRawItem[]) => TItem[];
	noResultsTitle: string;
	onAddItem?: () => void;
	onItemsChange?: (items: TFormattedItems) => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	showCheckbox?: boolean;
}
export declare function Table<TRawItem>({
	addItemTitle,
	columns,
	disabled,
	emptyStateTitle,
	mapperItems,
	noResultsTitle,
	onAddItem,
	onItemsChange,
	requestFn,
	showCheckbox,
}: ITableProps<TRawItem>): JSX.Element;
declare function ComposedTable<TRawItem>(
	props: ITableProps<TRawItem>
): JSX.Element;
export default ComposedTable;
