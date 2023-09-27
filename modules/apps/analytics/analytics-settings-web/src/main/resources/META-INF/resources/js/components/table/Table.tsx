/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';

import {TPagination} from '../../utils/pagination';
import {useRequest} from '../../utils/useRequest';
import Content from './Content';
import TableContext, {Events, useData, useDispatch} from './Context';
import ManagementToolbar from './ManagementToolbar';
import PaginationBar from './PaginationBar';
import StateRenderer, {TEmptyState} from './StateRenderer';
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
	const {filter, formattedItems, keywords, pagination} = useData();
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
				disabled={disabled || (empty && !keywords)}
				onAddItem={onAddItem}
				showCheckbox={showCheckbox}
			/>

			<StateRenderer
				empty={empty}
				emptyState={emptyState}
				error={error}
				loading={loading}
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
