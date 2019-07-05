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

import {deleteItem} from '../utils/client.es';
import {Link} from 'react-router-dom';
import moment from 'moment';
import React from 'react';
import SearchContainer from './search-container/SearchContainer.es';

export default function CustomObjectsList() {
	const actions = [
		{
			name: Liferay.Language.get('delete'),
			callback: row =>
				deleteItem(`/o/data-engine/v1.0/data-definitions/${row.id}`)
		}
	];

	const columns = [
		{
			name: Liferay.Language.get('name')
		},
		{
			dateCreated: Liferay.Language.get('create-date')
		},
		{
			dateModified: Liferay.Language.get('modified-date')
		}
	];

	const emptyState = {
		message: Liferay.Language.get('there-are-no-custom-objects-yet')
	};

	const endpoint = `/o/data-engine/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/data-definitions`;

	const formatter = items =>
		items.map(item => ({
			dateCreated: moment(item.dateCreated).fromNow(),
			dateModified: moment(item.dateModified).fromNow(),
			id: item.id,
			name: (
				<Link to={`/custom-object/${item.id}`}>{item.name.en_US}</Link>
			)
		}));

	return (
		<SearchContainer
			actions={actions}
			columns={columns}
			emptyState={emptyState}
			endpoint={endpoint}
			formatter={formatter}
		/>
	);
}
