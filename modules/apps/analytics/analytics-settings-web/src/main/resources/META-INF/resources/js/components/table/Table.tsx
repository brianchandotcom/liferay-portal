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

import {DEFAULT_PAGINATION, TPagination} from '../../utils/pagination';
import {useLazyRequest, useRequest} from '../../utils/useRequest';
import Content from './Content';
import TableContext, {Events, useData, useDispatch} from './Context';
import ManagementToolbar from './ManagementToolbar';
import PaginationBar from './PaginationBar';
import StateRenderer, {TEmptyState} from './StateRenderer';
import {TColumn, TFormattedItems, TItem, TTableRequestParams} from './types';

interface ITableProps<TRawItem> {
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

interface TData<TRawItem> extends TPagination {
	items: TRawItem[];
}

export function Table<TRawItem>({
	addItemTitle,
	columns,
	disabled = false,
	emptyState,
	mapperItems,
	onAddItem,
	onItemsChange,
	requestFn,
	showCheckbox = true,
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
		filter,
		keywords: '',
		pagination: {
			page: DEFAULT_PAGINATION.page,
			pageSize: pagination.maxCount,
		},
	});

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
					refetch,
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

	useEffect(() => {
		dispatch({
			payload: refetch,
			type: Events.Reload,
		});
	}, [dispatch, refetch]);

	const empty = !data?.items.length;

	return (
		<>
			<ManagementToolbar
				addItemTitle={addItemTitle}
				columns={columns}
				disabled={
					disabled || (empty && !keywords) || lazyResult.loading
				}
				makeRequest={makeRequest}
				onAddItem={onAddItem}
				showCheckbox={showCheckbox}
			/>

			<StateRenderer
				empty={empty}
				emptyState={emptyState}
				error={error || lazyResult.error}
				loading={loading || lazyResult.loading}
				refetch={refetch}
			>
				<Content
					columns={columns}
					disabled={disabled}
					showCheckbox={showCheckbox}
				/>
			</StateRenderer>

			<PaginationBar disabled={empty} />
		</>
	);
}

function ComposedTable<TRawItem>(props: ITableProps<TRawItem>) {
	return (
		<TableContext>
			<Table {...props} />
		</TableContext>
	);
}

export default ComposedTable;
