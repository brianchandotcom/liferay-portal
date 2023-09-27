/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {
	ReactNode,
	memo,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
} from 'react';
import {KeyedMutator} from 'swr';

import ListViewContextProvider, {
	AppActions,
	InitialState as ListViewContextState,
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
} from '../../context/ListViewContext';
import SearchBuilder from '../../core/SearchBuilder';
import {useFetch} from '../../hooks/useFetch';
import i18n from '../../i18n';
import {
	FilterSchema as FilterSchemaType,
	filterSchema as filterSchemas,
} from '../../schema/filter';
import {APIResponse} from '../../services/rest';
import {SortDirection} from '../../types';
import {PAGINATION} from '../../util/constants';
import EmptyState from '../EmptyState';
import Loading from '../Loading';
import ManagementToolbar, {ManagementToolbarProps} from '../ManagementToolbar';
import Table, {TableProps} from '../Table';

type ChildrenOptions = {
	dispatch: React.Dispatch<AppActions>;
	listViewContext: ListViewContextState;
	mutate: KeyedMutator<any>;
};

export type ListViewProps<T = any> = {
	children?: (response: APIResponse, options: ChildrenOptions) => ReactNode;
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

const ListView: React.FC<ListViewProps> = ({
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
	variables,
	pagination = {displayTop: true},
}) => {
	const [listViewContext, dispatch] = useContext(ListViewContext);

	const {
		columns: columnsContext,
		filters,
		selectedRows,
		sort,
	} = listViewContext;

	const filterSchemaName = managementToolbarProps.filterSchema ?? '';

	const filterSchema = (filterSchemas as any)[
		filterSchemaName
	] as FilterSchemaType;

	const onContextChangeRef = useRef<
		((context: ListViewContextState) => void) | undefined
	>(onContextChange);

	const onApplyFilterMemo = useMemo(
		() => filterSchema?.onApply?.bind(filterSchema),
		[filterSchema]
	);

	const filterVariables = useMemo(
		() => ({
			appliedFilter: filters.filter,
			defaultFilter: variables?.filter,
			filterSchema,
		}),
		[filters.filter, variables?.filter, filterSchema]
	);

	const getURLSearchParams = useCallback(
		() => ({
			filter: onApplyFilterMemo
				? onApplyFilterMemo(filterVariables)
				: SearchBuilder.createFilter(filterVariables) || '',
			forceRefetch,
			page: listViewContext.page,
			pageSize: listViewContext.pageSize,
			sort: sort.key ? `${sort.key}:${sort.direction.toLowerCase()}` : '',
		}),
		[
			onApplyFilterMemo,
			filterVariables,
			forceRefetch,
			listViewContext.page,
			listViewContext.pageSize,
			sort.key,
			sort.direction,
		]
	);

	const {data: response, error, loading, mutate} = useFetch(resource, {
		params: getURLSearchParams(),
		transformData,
	});

	const {
		actions = {},
		items = [],
		page = 1,
		pageSize,
		totalCount = 0,
		lastPage = 1,
	} = response || {};

	const itemsMemoized = useMemo(() => items, [items]);

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
		(rowId: number | number[]) => {
			dispatch({
				payload: rowId,
				type: ListViewTypes.SET_CHECKED_ROW,
			});
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
		onSelectRow(itemsMemoized.map(({id}) => id));
	}, [itemsMemoized, onSelectRow]);

	useEffect(() => {
		const shouldCurrentPageBeChanged =
			!loading &&
			!itemsMemoized.length &&
			lastPage > 1 &&
			page === lastPage;

		if (shouldCurrentPageBeChanged) {
			dispatch({payload: page - 1, type: ListViewTypes.SET_PAGE});
		}
	}, [dispatch, itemsMemoized.length, lastPage, loading, page]);

	const listViewContextString = JSON.stringify(listViewContext);

	useEffect(() => {
		if (onContextChangeRef.current) {
			onContextChangeRef.current(JSON.parse(listViewContextString));
		}
	}, [listViewContextString]);

	useEffect(() => {
		if (tableProps.rowSelectable) {
			dispatch({
				payload: itemsMemoized.every(({id}) =>
					selectedRows.includes(id)
				),
				type: ListViewTypes.SET_CHECKED_ALL_ROWS,
			});
		}
	}, [itemsMemoized, tableProps, selectedRows, dispatch]);

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
					totalItems={itemsMemoized.length}
				/>
			)}

			{!itemsMemoized.length && (
				<EmptyState
					description={error?.message}
					type={error ? 'EMPTY_SEARCH' : 'EMPTY_STATE'}
				/>
			)}

			{children &&
				children(response as APIResponse, {
					dispatch,
					listViewContext,
					mutate,
				})}

			{!!items.length && (
				<>
					{pagination?.displayTop && (
						<div className="mt-4">{Pagination}</div>
					)}

					<Table
						{...tableProps}
						allRowsChecked={listViewContext.checkAll}
						columns={columns}
						items={itemsMemoized}
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

const ListViewMemoized = memo(ListView);

const ListViewWithContext: React.FC<
	ListViewProps & {
		initialContext?: ListViewContextProviderProps;
	}
> = ({initialContext, ...otherProps}) => (
	<ListViewContextProvider {...initialContext} id={otherProps.resource}>
		<ListViewMemoized {...otherProps} />
	</ListViewContextProvider>
);

export default ListViewWithContext;
