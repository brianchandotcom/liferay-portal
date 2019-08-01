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

const APPS = {
	ACTIONS: [
		{
			name: Liferay.Language.get('delete'),
			callback: confirmDelete('/o/app-builder/v1.0/apps/')
		}
	],
	COLUMNS: [
		{
			name: Liferay.Language.get('name')
		},
		{
			type: Liferay.Language.get('deployed-as')
		},
		{
			dateCreated: Liferay.Language.get('create-date')
		},
		{
			dateModified: Liferay.Language.get('modified-date')
		},
		{
			status: Liferay.Language.get('status')
		}
	],
	EMPTY_STATE: {
		title: Liferay.Language.get('there-are-no-deployments-yet'),
		description: Liferay.Language.get(
			'select-the-form-and-table-view-you-want-and-deploy-your-app-as-a-widget-standalone-or-place-it-in-the-product-menu'
		)
	},
	FORMATTER: items =>
		items.map(item => ({
			dateCreated: moment(item.dateCreated).fromNow(),
			dateModified: moment(item.dateModified).fromNow(),
			id: item.id,
			name: item.name.en_US,
			status: item.settings.status,
			type: item.settings.type
		}))
};

export default ({
	match: {
		params: {dataDefinitionId}
	}
}) => {
	return (
		<ListView
			actions={APPS.ACTIONS}
			columns={APPS.COLUMNS}
			emptyState={APPS.EMPTY_STATE}
			endpoint={`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`}
			formatter={APPS.FORMATTER}
		/>
	);
};
