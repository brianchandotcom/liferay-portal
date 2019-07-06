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

import EmptyState from './EmptyState.es';
import PageSize from './PageSize.es';
import Pagination from './Pagination.es';
import React, {Fragment, useState} from 'react';
import Table from './Table.es';
import {useResource} from '@clayui/data-provider';

export default function SearchContainer(props) {
	const {columns, emptyState, endpoint, formatter} = props;

	const [state, setState] = useState({
		page: 1,
		pageSize: 20
	});

	const {resource, refetch} = useResource({
		link: endpoint,
		variables: {
			p_auth: Liferay.authToken,
			...state
		}
	});

	let items = [];
	let totalCount = 0;
	let totalPages = 1;

	if (resource) {
		({items, totalCount, lastPage: totalPages} = resource);
	}

	const {page, pageSize} = state;

	const actions = props.actions.map(action => ({
		...action,
		callback: row => {
			action.callback(row).then(() => {
				if (page > 1 && items.length === 1) {
					setState(prevState => ({
						...prevState,
						page: prevState.page - 1
					}));

					return;
				}

				refetch();
			});
		}
	}));

	if (!items || items.length === 0) {
		return <EmptyState {...emptyState} />;
	}

	return (
		<Fragment>
			<Table
				actions={actions}
				columns={columns}
				rows={formatter(items)}
			/>

			<PageSize
				itemsCount={items.length}
				onPageSizeChange={pageSize => setState({page: 1, pageSize})}
				page={page}
				pageSize={pageSize}
				totalCount={totalCount}
			/>

			<Pagination
				page={page}
				onPageChange={page =>
					setState(prevState => ({
						page,
						pageSize: prevState.pageSize
					}))
				}
				totalPages={totalPages}
			/>
		</Fragment>
	);
}
