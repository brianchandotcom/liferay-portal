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

import React, {useEffect} from 'react';

import {DEFAULT_FILTER} from '../../utils/filter';
import {DEFAULT_PAGINATION, TPagination} from '../../utils/pagination';
import {useLazyRequest, useRequest} from '../../utils/useRequest';
import Content from './Content';
import TableContext, {Events, useData, useDispatch} from './Context';
import ManagementToolbar from './ManagementToolbar';
import PaginationBar from './PaginationBar';
import StateRenderer from './StateRenderer';
import {TColumn, TFormattedItems, TItem, TTableRequestParams} from './types';

interface ITableProps<TRawItem> {
	columns: TColumn[];
	disabled?: boolean;
	emptyStateTitle: string;
	mapperItems: (items: TRawItem[]) => TItem[];
	noResultsTitle: string;
	onItemsChange?: (items: TFormattedItems) => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
}

interface TData<TRawItem> extends TPagination {
	items: TRawItem[];
}

function Table<TRawItem>({
	columns,
	disabled = false,
	emptyStateTitle,
	mapperItems,
	noResultsTitle,
	onItemsChange,
	requestFn,
}: ITableProps<TRawItem>) {
	const {
		filter,
		formattedItems,
		globalChecked,
		keywords,
		pagination,
	} = useData();
	const dispatch = useDispatch();

	const {data, error, loading, refetch} = useRequest<
		TData<TRawItem>,
		TTableRequestParams
	>(requestFn, {
		filter,
		keywords,
		pagination: {
			page: pagination.page,
			pageSize: pagination.pageSize,
		},
	});

	const [makeRequest, lazyResult] = useLazyRequest<
		TData<TRawItem>,
		TTableRequestParams
	>(requestFn, {
		filter: DEFAULT_FILTER,
		keywords: '',
		pagination: {
			page: DEFAULT_PAGINATION.page,
			pageSize: pagination.maxCount,
		},
	});

	const empty = !data?.items.length;

	useEffect(() => {
		if (lazyResult.data) {
			dispatch({
				payload: {
					globalChecked: !globalChecked,
					items: mapperItems(lazyResult.data.items),
				},
				type: Events.ToggleGlobalCheckbox,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [lazyResult.data]);

	useEffect(() => {
		if (data) {
			const {items, page, pageSize, totalCount} = data;

			dispatch({
				payload: {
					items: mapperItems(items),
					page,
					pageSize,
					totalCount,
				},
				type: Events.FormatData,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	useEffect(() => {
		onItemsChange && onItemsChange(formattedItems);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [formattedItems]);

	return (
		<>
			<ManagementToolbar
				columns={columns}
				disabled={
					disabled || (empty && !keywords) || lazyResult.loading
				}
				makeRequest={makeRequest}
			/>

			<StateRenderer
				empty={empty}
				emptyStateTitle={emptyStateTitle}
				error={error || lazyResult.error}
				loading={loading || lazyResult.loading}
				noResultsTitle={noResultsTitle}
				refetch={refetch}
			>
				<Content columns={columns} disabled={disabled} />
			</StateRenderer>

			<PaginationBar disabled={empty} />
		</>
	);
}

function TableWrapper<TRawItem>(props: ITableProps<TRawItem>) {
	return (
		<TableContext>
			<Table {...props} />
		</TableContext>
	);
}

export default TableWrapper;
