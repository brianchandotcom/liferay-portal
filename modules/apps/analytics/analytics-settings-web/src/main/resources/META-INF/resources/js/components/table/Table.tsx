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

import React, {useEffect, useRef, useState} from 'react';

import {TPagination} from '../../utils/pagination';
import {TQueries} from '../../utils/request';
import {useLazyRequest, useRequest} from '../../utils/useRequest';
import Content from './Content';
import TableContext, {Events, useData, useDispatch} from './Context';
import ManagementToolbar from './ManagementToolbar';
import PaginationBar from './PaginationBar';
import StateRenderer from './StateRenderer';

export type TColumn = {
	expanded: boolean;
	label: string;
	show?: boolean;
	sortable?: boolean;
	value: string;
};

export type TItem = {
	checked: boolean;
	columns: {label: string; show?: boolean}[];
	disabled: boolean;
	id: string;
};

export type TFormattedItems = {[key: string]: TItem};

interface ITableProps<TRawItem> {
	columns: TColumn[];
	disabled?: boolean;
	emptyStateTitle: string;
	mapperItems: (items: TRawItem[]) => TItem[];
	noResultsTitle: string;
	onItemsChange?: (items: TFormattedItems) => void;
	requestFn: (params: TQueries) => Promise<any>;
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
	const _contentRef = useRef<HTMLDivElement>(null);

	const {
		filter,
		formattedItems,
		globalChecked,
		keywords,
		pagination,
	} = useData();
	const dispatch = useDispatch();

	const {data, error, loading, refetch} = useRequest<TData<TRawItem>>(
		requestFn,
		{
			filter,
			keywords,
			pagination,
		}
	);

	const [lazyRequest, lazyResult] = useLazyRequest<TData<TRawItem>>(
		requestFn,
		{
			filter,
			keywords,
			pagination: {
				...pagination,
				pageSize: pagination.totalCount,
			},
		}
	);

	const height = useHeightHack(loading || lazyResult.loading, _contentRef);

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
			const mappedItems = mapperItems(items);

			dispatch({
				payload: {
					items: mappedItems,
					pagination: {
						page,
						pageSize,
						totalCount,
					},
					rows: mappedItems.map(({id}: TItem) => id),
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
				lazyRequest={lazyRequest}
			/>

			<div ref={_contentRef} style={{height, position: 'relative'}}>
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
			</div>

			<PaginationBar disabled={empty} />
		</>
	);
}

function useHeightHack(
	loading: boolean,
	containerRef: React.RefObject<HTMLDivElement>
) {
	const [height, setHeight] = useState<number | undefined>(undefined);

	useEffect(() => {
		if (loading) {
			return;
		}

		const tableNode = containerRef.current?.querySelector(
			'.table-responsive'
		);

		if (
			tableNode &&
			tableNode.getBoundingClientRect().height &&
			tableNode.getBoundingClientRect().height !== height
		) {
			setHeight(tableNode.getBoundingClientRect().height + 24);
		}
	}, [containerRef, height, loading]);

	return height;
}

function TableWrapper<TRawItem>(props: ITableProps<TRawItem>) {
	return (
		<TableContext>
			<Table {...props} />
		</TableContext>
	);
}

export default TableWrapper;
