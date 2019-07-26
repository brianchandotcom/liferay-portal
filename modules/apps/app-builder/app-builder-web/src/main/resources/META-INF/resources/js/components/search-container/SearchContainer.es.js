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
import EmptyState from './EmptyState.es';
import PageSize from './PageSize.es';
import Pagination from './Pagination.es';
import Table from './Table.es';
import {getURL} from '../../utils/client.es';

export default function SearchContainer({
	actions,
	columns,
	emptyState,
	endpoint,
	formatter
}) {
	const [loading, setLoading] = useState(true);

	const [state, setState] = useState({
		page: 1,
		pageSize: 20
	});

	const {resource, refetch} = useResource({
		link: getURL(endpoint),
		onNetworkStatusChange: status => setLoading(status < 4),
		variables: {
			...state
		}
	});

	if (loading) {
		return <ClayLoadingIndicator />;
	}

	let items = [];
	let totalCount = 0;
	let totalPages = 1;

	if (resource) {
		({items, totalCount, lastPage: totalPages} = resource);
	}

	const {page, pageSize} = state;

	const goBackPage = () => {
		setLoading(true);
		setState(prevState => ({
			...prevState,
			page: prevState.page - 1
		}));
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

						if (page > 1 && items.length === 1) {
							goBackPage();
							return;
						}

						refetch();
					});
				}
			};
		});

	if (!items || items.length === 0) {
		return <EmptyState {...emptyState} />;
	}

	return (
		<Fragment>
			<Table
				actions={refetchOnDelete(actions)}
				columns={columns}
				items={formatter(items)}
			/>

			<div className="taglib-search-iterator-page-iterator-bottom">
				<div className="pagination-bar">
					<PageSize
						itemsCount={items.length}
						onPageSizeChange={pageSize => {
							if (state.pageSize === pageSize) {
								return;
							}

							setLoading(true);
							setState(prevState => ({
								...prevState,
								page: 1,
								pageSize
							}));
						}}
						page={page}
						pageSize={pageSize}
						totalCount={totalCount}
					/>

					<Pagination
						onPageChange={page => {
							if (state.page === page) {
								return;
							}

							setLoading(true);
							setState(prevState => ({
								...prevState,
								page
							}));
						}}
						page={page}
						totalPages={totalPages}
					/>
				</div>
			</div>
		</Fragment>
	);
}
