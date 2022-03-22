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

import {TypedDocumentNode, useQuery} from '@apollo/client';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useCallback, useContext, useEffect, useMemo} from 'react';

import ListViewContextProvider, {
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
} from '../../context/ListViewContext';
import i18n from '../../i18n';
import {PAGINATION} from '../../util/constants';
import EmptyState from '../EmptyState';
import ManagementToolbar, {ManagementToolbarProps} from '../ManagementToolbar';
import Table, {TableProps} from '../Table';

type LiferayQueryResponse<T = any> = {
	items: T[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

type ListViewProps<T = any> = {
	forceRefetch?: number;
	managementToolbarProps?: {
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		'tableProps' | 'totalItems' | 'onSelectAllRows'
	>;
	query: TypedDocumentNode;
	tableProps: Omit<TableProps, 'items'>;
	transformData: (data: T) => LiferayQueryResponse<T>;
	variables?: any;
};

const ListView: React.FC<ListViewProps> = ({
	forceRefetch,
	managementToolbarProps: {
		visible: managementToolbarVisible = true,
		...managementToolbarProps
	} = {},
	query,
	tableProps,
	transformData,
	variables,
}) => {
	const [{filters, selectedRows}, dispatch] = useContext(ListViewContext);

	const {data, error, loading, refetch} = useQuery(query, {
		variables,
	});

	const {items = [], page, pageSize, totalCount} = transformData(data) || {};

	const deltas = PAGINATION.delta.map((label) => ({label}));

	const columns = useMemo(
		() =>
			tableProps.columns.filter(({key}) => {
				const columns = filters.columns || {};

				if (columns[key] === undefined) {
					return true;
				}

				return columns[key];
			}),
		[filters.columns, tableProps.columns]
	);

	const onRefetch = useCallback(
		(newVariables: any) => {
			refetch({...variables, ...newVariables});
		},
		[refetch, variables]
	);

	const onSelectRow = useCallback(
		(row: any) => {
			dispatch({payload: row?.id, type: ListViewTypes.SET_CHECKED_ROW});
		},
		[dispatch]
	);

	const onSelectAllRows = useCallback(() => {
		items.forEach(onSelectRow);
	}, [items, onSelectRow]);

	useEffect(() => {
		if (forceRefetch) {
			onRefetch({});
		}
	}, [forceRefetch, onRefetch]);

	if (error) {
		return <span>{error.message}</span>;
	}

	if (loading) {
		return <span>{i18n.translate('loading')}...</span>;
	}

	return (
		<>
			{managementToolbarVisible && (
				<ManagementToolbar
					{...managementToolbarProps}
					onSelectAllRows={onSelectAllRows}
					tableProps={tableProps}
					totalItems={items.length}
				/>
			)}

			{!items.length && <EmptyState />}

			{!!items.length && (
				<>
					<Table
						{...tableProps}
						columns={columns}
						items={items}
						onSelectRow={onSelectRow}
						selectedRows={selectedRows}
					/>

					<ClayPaginationBarWithBasicItems
						activeDelta={pageSize}
						activePage={page}
						deltas={deltas}
						ellipsisBuffer={PAGINATION.ellipsisBuffer}
						labels={{
							paginationResults: i18n.translate(
								'showing-x-to-x-of-x'
							),
							perPageItems: i18n.translate('x-items'),
							selectPerPageItems: i18n.translate('x-items'),
						}}
						onDeltaChange={(delta) => onRefetch({pageSize: delta})}
						onPageChange={(page) => onRefetch({page})}
						totalItems={totalCount}
					/>
				</>
			)}
		</>
	);
};

const ListViewWithContext: React.FC<
	ListViewProps & {initialContext?: ListViewContextProviderProps}
> = ({initialContext, ...otherProps}) => {
	return (
		<ListViewContextProvider {...initialContext}>
			<ListView {...otherProps} />
		</ListViewContextProvider>
	);
};

export default ListViewWithContext;
