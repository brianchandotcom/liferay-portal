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

import moment from 'moment';
import React from 'react';
import ListView from '../../components/list-view/ListView.es';
import {confirmDelete} from '../../utils/client.es';

const TABLE_VIEWS = {
	ACTIONS: [
		{
			name: Liferay.Language.get('delete'),
			callback: confirmDelete('/o/data-engine/v1.0/data-list-views/')
		}
	],
	COLUMNS: [
		{
			name: Liferay.Language.get('name')
		},
		{
			dateCreated: Liferay.Language.get('create-date')
		},
		{
			dateModified: Liferay.Language.get('modified-date')
		}
	],
	EMPTY_STATE: {
		title: Liferay.Language.get('there-are-no-table-views-yet'),
		description: Liferay.Language.get(
			'create-one-or-more-tables-to-display-the-data-held-in-your-data-object'
		)
	},
	FORMATTER: items =>
		items.map(item => ({
			dateCreated: moment(item.dateCreated).fromNow(),
			dateModified: moment(item.dateModified).fromNow(),
			id: item.id,
			name: item.name.en_US
		}))
};

export default ({
	match: {
		params: {dataDefinitionId}
	}
}) => {
	return (
		<ListView
			actions={TABLE_VIEWS.ACTIONS}
			columns={TABLE_VIEWS.COLUMNS}
			emptyState={TABLE_VIEWS.EMPTY_STATE}
			endpoint={`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-list-views`}
			formatter={TABLE_VIEWS.FORMATTER}
		/>
	);
};
