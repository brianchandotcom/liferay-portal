/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect} from 'react';

import useQuery from '../../hooks/useQuery';
import useResource from '../../hooks/useResource';
import {errorToast} from '../../utils/toast';
import ManagementToolbar from '../management-toolbar/ManagementToolbar';
import ManagementToolbarResultsBar, {
	getSelectedFilters,
} from '../management-toolbar/ManagementToolbarResultsBar';
import SearchContext, {reducer} from '../management-toolbar/SearchContext';
import TableWithPagination from '../table/TableWithPagination';

const ListView = ({
	actions,
	addButton,
	children,
	customFetch,
	columns,
	defaultDelta = 20,
	defaultSort = 'asc',
	emptyState,
	endpoint,
	filters = [],
	history,
	noActionsMessage,
	queryParams,
	scope,
}) => {
	const [query, setQuery] = useQuery(
		history,
		{
			defaultSort,
			filters: {},
			keywords: '',
			page: 1,
			pageSize: defaultDelta,
			sort: '',
			...queryParams,
		},
		scope
	);

	const dispatch = useCallback(
		(action) => {
			setQuery(reducer(query, action));
		},
		[query, setQuery]
	);

	const params = {...query, ...query.filters};

	delete params.filters;

	const {error, isLoading, refetch, response} = useResource({
		customFetch,
		endpoint,
		params,
	});

	let items = [];
	let totalCount = 0;
	let totalPages;

	if (response) {
		({items = [], totalCount, lastPage: totalPages} = response);
	}

	useEffect(() => {
		if (totalPages && Number(query.page) > totalPages) {
			dispatch({page: totalPages, type: 'CHANGE_PAGE'});
		}
	}, [dispatch, query.page, totalPages]);

	useEffect(() => {
		if (error) {
			errorToast();
		}
	}, [error]);

	let refetchOnActions;

	if (actions && !!actions.length) {
		refetchOnActions = actions.map((action) => {
			if (!action.action) {
				return action;
			}

			return {
				...action,
				action: (item) => {
					action.action(item, refetch).then((isRefetch) => {
						if (!isRefetch) {
							return;
						}

						refetch();
					});
				},
			};
		});
	}

	const selectedFilters = getSelectedFilters(filters, query.filters);
	const isEmpty = totalCount === 0 || !items.length;
	const isFiltered = !!selectedFilters.length;

	return (
		<div className="list-view">
			<SearchContext.Provider value={[query, dispatch]}>
				<ManagementToolbar
					addButton={addButton}
					columns={columns}
					disabled={!isFiltered && !query.keywords && isEmpty}
					filters={filters}
				/>

				<ManagementToolbarResultsBar
					filters={filters}
					isLoading={isLoading}
					totalCount={totalCount}
				/>

				<TableWithPagination
					actions={refetchOnActions}
					columns={columns}
					emptyState={emptyState}
					isEmpty={isEmpty}
					isFiltered={isFiltered}
					isLoading={isLoading}
					items={items.map((item, index) => children(item, index))}
					keywords={query.keywords}
					noActionsMessage={noActionsMessage}
					totalCount={totalCount}
				/>
			</SearchContext.Provider>
		</div>
	);
};

export default ListView;
