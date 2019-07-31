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

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useState} from 'react';
import {NavLink} from 'react-router-dom';
import {
	ManagementToolbar,
	SearchBar
} from '../../components/management-toolbar/index.es';
import {CUSTOM_OBJECTS} from '../../components/search-container/constants.es';
import SearchContainer from '../../components/search-container/SearchContainer.es';

export default () => {
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
		<>
			<ManagementToolbar>
				<SearchBar
					columns={CUSTOM_OBJECTS.COLUMNS}
					keywords={keywords}
					onSearch={onSearch}
					onSort={onSort}
					totalCount={totalCount}
				/>

				<NavLink to={`/custom-object/edit`}>
					<ClayButtonWithIcon
						className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
						symbol="plus"
					/>
				</NavLink>
			</ManagementToolbar>

			<SearchContainer
				actions={CUSTOM_OBJECTS.ACTIONS}
				columns={CUSTOM_OBJECTS.COLUMNS}
				emptyState={CUSTOM_OBJECTS.EMPTY_STATE}
				endpoint={CUSTOM_OBJECTS.ENDPOINT}
				formatter={CUSTOM_OBJECTS.FORMATTER}
				keywords={keywords}
				sort={sort}
			/>
		</>
	);
};
