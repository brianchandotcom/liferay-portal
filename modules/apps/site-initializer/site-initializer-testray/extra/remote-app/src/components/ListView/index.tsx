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

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {
	ReactNode,
	memo,
	useCallback,
	useContext,
	useEffect,
	useMemo,
} from 'react';
import {KeyedMutator} from 'swr';

import ListViewContextProvider, {
	InitialState as ListViewContextState,
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
} from '../../context/ListViewContext';
import {APIResponse} from '../../graphql/queries';
import {useFetch} from '../../hooks/useFetch';
import i18n from '../../i18n';
import {SortDirection} from '../../types';
import {PAGINATION} from '../../util/constants';
import {SearchBuilder} from '../../util/search';
import EmptyState from '../EmptyState';
import Loading from '../Loading';
import ManagementToolbar, {ManagementToolbarProps} from '../ManagementToolbar';
import Table, {TableProps} from '../Table';

export type ListViewProps<T = any> = {
	children?: (response: APIResponse, mutate: KeyedMutator<any>) => ReactNode;
	forceRefetch?: number;
	managementToolbarProps?: {
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		| 'actions'
		| 'tableProps'
		| 'totalItems'
		| 'onSelectAllRows'
		| 'rowSelectable'
	>;
	onContextChange?: (context: ListViewContextState) => void;
	pagination?: {
		displayTop?: boolean;
	};
	resource: string;
	tableProps: Omit<
		TableProps,
		'items' | 'mutate' | 'onSelectAllRows' | 'onSort'
	>;
	transformData?: (data: T) => APIResponse<T>;
	variables?: any;
};

const ListViewRest: React.FC<ListViewProps> = ({
	children,
	forceRefetch,
	managementToolbarProps: {
		visible: managementToolbarVisible = true,
		...managementToolbarProps
	} = {},
	onContextChange,
	resource,
	tableProps,
	transformData,
	variables: _variables,
	pagination = {displayTop: true},
}) => {
	const [listViewContext, dispatch] = useContext(ListViewContext);

	const {
		columns: columnsContext,
		filters,
		selectedRows,
		sort,
	} = listViewContext;

	const getURLSearchParams = useCallback(
		(url: string) => {
			const urlSearchParams = new URLSearchParams();

			urlSearchParams.set(
				'sort',
				sort.key ? `${sort.key}:${sort.direction.toLowerCase()}` : ''
			);
			urlSearchParams.set('page', listViewContext.page.toString());
			urlSearchParams.set(
				'pageSize',
				listViewContext.pageSize.toString()
			);
			urlSearchParams.set(
				'filter',
				SearchBuilder.createFilter(
					filters.filter,
					_variables?.filter
				) || ''
			);

			if (forceRefetch) {
				urlSearchParams.set('ts', forceRefetch.toString());
			}

			const operator = url.includes('?') ? '&' : '?';

			return `${url}${operator}${urlSearchParams.toString()}`;
		},
		[
			forceRefetch,
			_variables?.filter,
			filters.filter,
			listViewContext.page,
			listViewContext.pageSize,
			sort.direction,
			sort.key,
		]
	);

	const {data, error, loading, mutate} = useFetch(
		getURLSearchParams(resource)
	);

	const response = transformData ? transformData(data) : data || {};

	const {actions = {}, items = [], page, pageSize, totalCount} =
		response || {};

	const columns = useMemo(
		() =>
			tableProps.columns.filter(({key}) => {
				const columns = columnsContext || {};

				if (columns[key] === undefined) {
					return true;
				}

				return columns[key];
			}),
		[columnsContext, tableProps.columns]
	);

	const onSelectRow = useCallback(
		(row: any) => {
			dispatch({payload: row?.id, type: ListViewTypes.SET_CHECKED_ROW});
		},
		[dispatch]
	);

	const onSort = useCallback(
		(key: string, direction: SortDirection) => {
			dispatch({
				payload: {direction, key},
				type: ListViewTypes.SET_SORT,
			});
		},
		[dispatch]
	);

	const onSelectAllRows = useCallback(() => {
		items.forEach(onSelectRow);
	}, [items, onSelectRow]);

	useEffect(() => {
		if (onContextChange) {
			onContextChange(listViewContext);
		}
	}, [listViewContext, onContextChange]);

	if (error) {
		return <span>{error.message}</span>;
	}

	if (loading) {
		return <Loading />;
	}

	const Pagination = (
		<ClayPaginationBarWithBasicItems
			activeDelta={pageSize}
			activePage={page}
			deltas={PAGINATION.delta.map((label) => ({label}))}
			disableEllipsis={totalCount > 100}
			ellipsisBuffer={PAGINATION.ellipsisBuffer}
			labels={{
				paginationResults: i18n.translate('showing-x-to-x-of-x'),
				perPageItems: i18n.translate('x-items'),
				selectPerPageItems: i18n.translate('x-items'),
			}}
			onDeltaChange={(delta) =>
				dispatch({payload: delta, type: ListViewTypes.SET_PAGE_SIZE})
			}
			onPageChange={(page) =>
				dispatch({payload: page, type: ListViewTypes.SET_PAGE})
			}
			totalItems={totalCount}
		/>
	);

	return (
		<>
			{managementToolbarVisible && (
				<ManagementToolbar
					{...managementToolbarProps}
					actions={actions}
					tableProps={tableProps}
					totalItems={items.length}
				/>
			)}

			{!items.length && <EmptyState />}

			{!!items.length && (
				<>
					{children && children(response, mutate)}

					{pagination?.displayTop && (
						<div className="mt-4">{Pagination}</div>
					)}

					<Table
						{...tableProps}
						columns={columns}
						items={items}
						mutate={mutate}
						onSelectAllRows={onSelectAllRows}
						onSelectRow={onSelectRow}
						onSort={onSort}
						selectedRows={selectedRows}
						sort={sort}
					/>

					{Pagination}
				</>
			)}
		</>
	);
};

const ListViewMemoized = memo(ListViewRest);

const ListViewWithContext: React.FC<
	ListViewProps & {
		initialContext?: ListViewContextProviderProps;
	}
> = ({initialContext, ...otherProps}) => (
	<ListViewContextProvider {...initialContext}>
		<ListViewMemoized {...otherProps} />
	</ListViewContextProvider>
);

export default ListViewWithContext;
