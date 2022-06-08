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
import {
	ReactNode,
	memo,
	useCallback,
	useContext,
	useEffect,
	useMemo,
} from 'react';

import ListViewContextProvider, {
	InitialState as ListViewContextState,
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
} from '../../context/ListViewContext';
import i18n from '../../i18n';
import {PAGINATION} from '../../util/constants';
import {SearchBuilder} from '../../util/search';
import EmptyState from '../EmptyState';
import Loading from '../Loading';
import ManagementToolbar, {ManagementToolbarProps} from '../ManagementToolbar';
import Table, {TableProps} from '../Table';

type LiferayQueryResponse<T = any> = {
	items: T[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type ListViewProps<T = any> = {
	children?: ({items}: {items: any[]}) => ReactNode;
	forceRefetch?: number;
	managementToolbarProps?: {
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		'tableProps' | 'totalItems' | 'onSelectAllRows' | 'rowSelectable'
	>;
	onContextChange?: (context: ListViewContextState) => void;
	pagination?: {
		displayTop?: boolean;
	};
	query: TypedDocumentNode;
	tableProps: Omit<TableProps, 'items' | 'onSelectAllRows'>;
	transformData: (data: T) => LiferayQueryResponse<T>;
	variables?: any;
};

const ListView: React.FC<ListViewProps> = ({
	children,
	forceRefetch,
	managementToolbarProps: {
		visible: managementToolbarVisible = true,
		...managementToolbarProps
	} = {},
	onContextChange,
	query,
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

	const variables = useMemo(() => {
		return {
			..._variables,
			filter:
				SearchBuilder.createFilter(
					filters.filter,
					_variables?.filter
				) || '',
			sort,
		};
	}, [_variables, sort, filters]);

	const {data, error, loading, refetch} = useQuery(query, {
		variables,
	});

	const {items = [], page, pageSize, totalCount} = transformData(data) || {};

	const deltas = PAGINATION.delta.map((label) => ({label}));

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
		if (onContextChange) {
			onContextChange(listViewContext);
		}
	}, [listViewContext, onContextChange]);

	useEffect(() => {
		if (forceRefetch) {
			onRefetch({});
		}
	}, [forceRefetch, onRefetch]);

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
			deltas={deltas}
			disableEllipsis={totalCount > 100}
			ellipsisBuffer={PAGINATION.ellipsisBuffer}
			labels={{
				paginationResults: i18n.translate('showing-x-to-x-of-x'),
				perPageItems: i18n.translate('x-items'),
				selectPerPageItems: i18n.translate('x-items'),
			}}
			onDeltaChange={(delta) => onRefetch({pageSize: delta})}
			onPageChange={(page) => onRefetch({page})}
			totalItems={totalCount}
		/>
	);

	return (
		<>
			{managementToolbarVisible && (
				<ManagementToolbar
					{...managementToolbarProps}
					tableProps={tableProps}
					totalItems={items.length}
				/>
			)}

			{!items.length && <EmptyState />}

			{!!items.length && (
				<>
					{children && children({items})}

					{pagination?.displayTop && (
						<div className="mt-4">{Pagination}</div>
					)}

					<Table
						{...tableProps}
						columns={columns}
						items={items}
						onSelectAllRows={onSelectAllRows}
						onSelectRow={onSelectRow}
						selectedRows={selectedRows}
					/>

					{Pagination}
				</>
			)}
		</>
	);
};

const ListViewMemoized = memo(ListView);

const ListViewWithContext: React.FC<
	ListViewProps & {
		initialContext?: ListViewContextProviderProps;
		viewPermission?: boolean;
	}
> = ({initialContext, viewPermission = true, ...otherProps}) => {
	if (viewPermission) {
		return (
			<ListViewContextProvider {...initialContext}>
				<ListViewMemoized {...otherProps} />
			</ListViewContextProvider>
		);
	}

	return (
		<EmptyState
			description={i18n.translate(
				'you-do-not-have-permissions-to-access-this-app-contact-the-app-administrator-to-request-the-access'
			)}
			title={i18n.translate('no-permissions')}
			type="NO_ACCESS"
		/>
	);
};

export default ListViewWithContext;
