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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Fragment, useState} from 'react';
import PaginationBar from './pagination/PaginationBar.es';
import EmptyState from './table/EmptyState.es';
import Table from './table/Table.es';
import {getURL} from '../../utils/client.es';

export default function SearchContainer({
	actions,
	columns,
	emptyState,
	endpoint,
	formatter,
	keywords,
	sort
}) {
	const [isLoading, setLoading] = useState(true);

	const [state, setState] = useState({
		page: 1,
		pageSize: 20
	});

	const {resource, refetch} = useResource({
		fetchDelay: 0,
		link: getURL(endpoint),
		onNetworkStatusChange: status => setLoading(status < 4),
		variables: {
			...state,
			keywords,
			sort
		}
	});

	let items = [];
	let totalCount = 0;
	let totalPages = 1;

	if (resource) {
		({items, totalCount, lastPage: totalPages} = resource);
	}

	let LoadingIndicator;

	if (!items || items.length === 0) {
		LoadingIndicator = <EmptyState {...emptyState} />;
	}

	if (isLoading) {
		LoadingIndicator = <ClayLoadingIndicator />;
	}

	const fetchItems = nextState => {
		setLoading(true);
		setState(prevState => ({
			...prevState,
			...nextState
		}));
	};

	const onPageChange = page => {
		if (state.page === page) {
			return;
		}

		fetchItems({page});
	};

	const onPageSizeChange = pageSize => {
		if (state.pageSize === pageSize) {
			return;
		}

		fetchItems({page: 1, pageSize});
	};

	const refetchOnDelete = actions =>
		actions.map(action => {
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

						if (state.page > 1 && items.length === 1) {
							onPageChange(state.page - 1);
							return;
						}

						refetch();
					});
				}
			};
		});

	const {page, pageSize} = state;
	const {length: itemsCount} = items || [];

	return (
		<Fragment>
			<div className="container-fluid container-fluid-max-xl">
				{LoadingIndicator || (
					<Fragment>
						<Table
							actions={refetchOnDelete(actions)}
							columns={columns}
							items={formatter(items)}
						/>

						<div className="taglib-search-iterator-page-iterator-bottom">
							<PaginationBar
								itemsCount={itemsCount}
								onPageChange={onPageChange}
								onPageSizeChange={onPageSizeChange}
								page={page}
								pageSize={pageSize}
								totalCount={totalCount}
								totalPages={totalPages}
							/>
						</div>
					</Fragment>
				)}
			</div>
		</Fragment>
	);
}
