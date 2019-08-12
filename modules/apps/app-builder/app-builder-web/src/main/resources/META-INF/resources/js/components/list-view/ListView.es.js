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
import React, {useReducer, useState} from 'react';
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
	const [isLoading, setLoading] = useState(true);

	const [state, dispatch] = useReducer(reducer, {
		keywords: '',
		page: 1,
		pageSize: 20,
		sort: ''
	});

	const {refetch, resource} = useResource({
		fetchDelay: 0,
		link: getURL(endpoint),
		onNetworkStatusChange: status => setLoading(status < 4),
		variables: {
			...state
		}
	});

	let items = [];
	let totalCount = 0;
	let totalPages = 1;

	if (resource) {
		({items = [], totalCount, lastPage: totalPages} = resource);
	}

	if (state.page > totalPages) {
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
		<SearchContext.Provider value={{dispatch, isLoading, state}}>
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
				isLoading={isLoading}
				items={formattedItems}
				keywords={state.keywords}
				totalCount={totalCount}
				totalPages={totalPages}
			/>
		</SearchContext.Provider>
	);
};
