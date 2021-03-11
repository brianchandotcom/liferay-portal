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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ListView from 'data-engine-js-components-web/js/components/list-view/ListView.es';
import {confirmDelete} from 'data-engine-js-components-web/js/utils/client.es';
import {getLocalizedValue} from 'data-engine-js-components-web/js/utils/lang.es';
import React from 'react';
import {Link} from 'react-router-dom';

import {fromNow} from '../../utils/time.es';

const COLUMNS = [
	{
		key: 'name',
		sortable: true,
		value: Liferay.Language.get('name'),
	},
	{
		key: 'dateCreated',
		sortable: true,
		value: Liferay.Language.get('created-date'),
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date'),
	},
];

const queryFields = ['dateCreated', 'dateModified', 'id', 'name'].join(',');

export default ({
	defaultLanguageId,
	history,
	match: {
		params: {dataDefinitionId},
		url,
	},
}) => (
	<ListView
		actions={[
			{
				action: (item) =>
					Promise.resolve(history.push(`${url}/${item.id}`)),
				name: Liferay.Language.get('edit'),
			},
			{
				action: confirmDelete('/o/data-engine/v2.0/data-list-views/'),
				name: Liferay.Language.get('delete'),
			},
		]}
		addButton={() => (
			<Link to={`${url}/add`}>
				<ClayButtonWithIcon
					className="nav-btn nav-btn-monospaced"
					symbol="plus"
					title={Liferay.Language.get('new-table-view')}
				/>
			</Link>
		)}
		columns={COLUMNS}
		emptyState={{
			button: () => (
				<Link to={`${url}/add`}>
					<ClayButton displayType="secondary">
						{Liferay.Language.get('new-table-view')}
					</ClayButton>
				</Link>
			),
			description: Liferay.Language.get(
				'create-one-or-more-tables-to-display-the-data-held-in-your-data-object'
			),
			title: Liferay.Language.get('there-are-no-table-views-yet'),
		}}
		endpoint={`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-list-views?fields=${queryFields}`}
		history={history}
	>
		{(item) => {
			const {dateCreated, dateModified, id, name} = item;

			return {
				...item,
				dateCreated: fromNow(dateCreated),
				dateModified: fromNow(dateModified),
				id,
				name: (
					<Link to={`${url}/${id}`}>
						{getLocalizedValue(defaultLanguageId, name)}
					</Link>
				),
			};
		}}
	</ListView>
);
