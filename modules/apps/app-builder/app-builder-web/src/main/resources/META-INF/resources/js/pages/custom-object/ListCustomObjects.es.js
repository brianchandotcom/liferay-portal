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

const CUSTOM_OBJECTS = {
	ACTIONS: [
		{
			name: Liferay.Language.get('form-views'),
			link: item => `#/custom-object/${item.id}/form-views`
		},
		{
			name: Liferay.Language.get('table-views'),
			link: item => `#/custom-object/${item.id}/table-views`
		},
		{
			name: Liferay.Language.get('deployments'),
			link: item => `#/custom-object/${item.id}/deployments`
		},
		{
			name: 'divider'
		},
		{
			name: Liferay.Language.get('delete'),
			callback: confirmDelete('/o/data-engine/v1.0/data-definitions/')
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
			dateModified: Liferay.Language.get('modified-date'),
			asc: false
		}
	],
	EMPTY_STATE: {
		title: Liferay.Language.get('there-are-no-custom-objects-yet'),
		description: Liferay.Language.get(
			'custom-objects-define-the-types-of-data-your-business-application-needs'
		)
	},
	FORMATTER: items =>
		items.map(item => ({
			dateCreated: moment(item.dateCreated).fromNow(),
			dateModified: moment(item.dateModified).fromNow(),
			id: item.id,
			name: {
				name: item.name.en_US,
				link: item => `/custom-object/${item.id}/form-views`
			}
		}))
};

export default () => {
	return (
		<ListView
			actions={CUSTOM_OBJECTS.ACTIONS}
			addURL="/custom-object/edit"
			columns={CUSTOM_OBJECTS.COLUMNS}
			emptyState={CUSTOM_OBJECTS.EMPTY_STATE}
			endpoint={`/o/data-engine/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/data-definitions`}
			formatter={CUSTOM_OBJECTS.FORMATTER}
		/>
	);
};
