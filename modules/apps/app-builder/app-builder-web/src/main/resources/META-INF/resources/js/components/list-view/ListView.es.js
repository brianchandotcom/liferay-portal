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

import {useResource} from '@clayui/data-provider';
import React, {useReducer} from 'react';
import {ManagementToolbar, SearchBar} from '../management-toolbar/index.es';
import SearchSubnavigationBar from '../management-toolbar/search/SearchSubnavigationBar.es';
import SearchContainer from '../search-container/SearchContainer.es';
import {reducer, SearchContext} from '../search-container/SearchContext.es';
import {getURL} from '../../utils/client.es';

export default ({
	actions,
	addButton,
	columns,
	endpoint,
	emptyState,
	formatter
}) => {
	const [state, dispatch] = useReducer(reducer, {
		isLoading: true,
		query: {
			keywords: '',
			page: 1,
			pageSize: 20,
			sort: ''
		}
	});

	const {refetch, resource} = useResource({
		fetchDelay: 0,
		link: getURL(endpoint),
		onNetworkStatusChange: status =>
			dispatch({isLoading: status < 4, type: 'LOADING'}),
		variables: {
			...state.query
		}
	});

	let items = [];
	let totalCount = 0;
	let totalPages = 1;

	if (resource) {
		({items = [], totalCount, lastPage: totalPages} = resource);
	}

	if (state.query.page > totalPages) {
		dispatch({page: totalPages, type: 'CHANGE_PAGE'});
	}

	const formattedItems = formatter(items);

	const refetchOnActions = actions.map(action => {
		if (!action.callback) {
			return action;
		}

		return {
			...action,
			callback: item => {
				action.callback(item).then(confirmed => {
					if (!confirmed) {
						return;
					}

					refetch();
				});
			}
		};
	});

	return (
		<SearchContext.Provider value={{dispatch, state}}>
			<ManagementToolbar>
				<SearchBar columns={columns} totalCount={totalCount} />

				{addButton && addButton()}
			</ManagementToolbar>

			<SearchSubnavigationBar totalCount={totalCount} />

			<SearchContainer
				actions={refetchOnActions}
				columns={columns}
				emptyState={emptyState}
				isEmpty={totalCount === 0}
				isLoading={state.isLoading}
				items={formattedItems}
				keywords={state.query.keywords}
				totalCount={totalCount}
				totalPages={totalPages}
			/>
		</SearchContext.Provider>
	);
};
