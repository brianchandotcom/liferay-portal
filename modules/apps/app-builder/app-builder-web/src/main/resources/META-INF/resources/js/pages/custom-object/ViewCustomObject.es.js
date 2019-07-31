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

import React, {Fragment, useState} from 'react';
import {Route, Switch} from 'react-router-dom';
import CustomObjectNavigationBar from './CustomObjectNavigationBar.es';
import {
	APPS,
	FORM_VIEWS,
	TABLE_VIEWS
} from '../../components/search-container/constants.es';
import {
	ManagementToolbar,
	SearchBar
} from '../../components/management-toolbar/index.es';
import SearchContainer from '../../components/search-container/SearchContainer.es';

export default ({
	match: {
		params: {dataDefinitionId},
		path,
		url
	}
}) => {
	const [state, setState] = useState({
		keywords: '',
		sort: '',
		totalCount: 0
	});

	const onSearch = keywords => {
		setState({...state, keywords});
	};

	const onSort = sort => {
		setState({...state, sort});
	};

	const {keywords, sort, totalCount} = state;

	return (
		<Fragment>
			<CustomObjectNavigationBar url={url} />

			<ManagementToolbar>
				<SearchBar
					keywords={keywords}
					onSearch={onSearch}
					onSort={onSort}
					totalCount={totalCount}
				/>
			</ManagementToolbar>

			<Switch>
				<Route
					path={`${path}/form-views`}
					render={() => (
						<SearchContainer
							actions={FORM_VIEWS.ACTIONS}
							columns={FORM_VIEWS.COLUMNS}
							emptyState={FORM_VIEWS.EMPTY_STATE}
							endpoint={FORM_VIEWS.ENDPOINT(dataDefinitionId)}
							formatter={FORM_VIEWS.FORMATTER}
							key="0"
							keywords={keywords}
							sort={sort}
						/>
					)}
				/>

				<Route
					path={`${path}/table-views`}
					render={() => (
						<SearchContainer
							actions={TABLE_VIEWS.ACTIONS}
							columns={TABLE_VIEWS.COLUMNS}
							emptyState={TABLE_VIEWS.EMPTY_STATE}
							endpoint={TABLE_VIEWS.ENDPOINT(dataDefinitionId)}
							formatter={TABLE_VIEWS.FORMATTER}
							key="1"
							keywords={keywords}
						/>
					)}
				/>

				<Route
					path={`${path}/deployments`}
					render={() => (
						<SearchContainer
							actions={APPS.ACTIONS}
							columns={APPS.COLUMNS}
							emptyState={APPS.EMPTY_STATE}
							endpoint={APPS.ENDPOINT(dataDefinitionId)}
							formatter={APPS.FORMATTER}
							key="2"
							keywords={keywords}
						/>
					)}
				/>
			</Switch>
		</Fragment>
	);
};
