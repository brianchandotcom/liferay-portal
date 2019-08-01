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

import React, {Fragment, useCallback, useState} from 'react';
import {
	ManagementToolbar,
	SearchBar,
	AddButton
} from '../../components/management-toolbar/index.es';
import SearchContainer from '../../components/search-container/SearchContainer.es';

export default ({
	actions,
	addURL,
	columns,
	emptyState,
	endpoint,
	formatter
}) => {
	const [isLoading, setLoading] = useState(false);

	const onLoadingChange = useCallback(isLoading => {
		setLoading(isLoading);
	}, []);

	const [totalCount, setTotalCount] = useState(0);

	const onTotalCountChange = useCallback(totalCount => {
		setTotalCount(totalCount);
	}, []);

	const [state, setState] = useState({
		keywords: '',
		sort: ''
	});

	const onSearch = keywords => {
		setState({...state, keywords});
	};

	const onSort = sort => {
		setState({...state, sort});
	};

	const {keywords, sort} = state;

	return (
		<Fragment>
			<ManagementToolbar>
				<SearchBar
					columns={columns}
					isLoading={isLoading}
					keywords={keywords}
					onSearch={onSearch}
					onSort={onSort}
					totalCount={totalCount}
				/>

				{!!addURL && <AddButton href={addURL} />}
			</ManagementToolbar>

			<SearchContainer
				actions={actions}
				columns={columns}
				emptyState={emptyState}
				endpoint={endpoint}
				formatter={formatter}
				keywords={keywords}
				onLoadingChange={onLoadingChange}
				onTotalCountChange={onTotalCountChange}
				sort={sort}
			/>
		</Fragment>
	);
};
