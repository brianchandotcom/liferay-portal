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

import Cards from './cards/Cards';
import EmailsList from './emails_list/EmailsList';
import List from './list/List';
import SelectableTable from './selectable_table/SelectableTable';
import Table from './table/Table';
import Timeline from './timeline/Timeline';

const views = [
	{
		component: Table,
		id: 'table',
	},
	{
		component: SelectableTable,
		id: 'selectableTable',
	},
	{
		component: Timeline,
		id: 'timeline',
	},
	{
		component: EmailsList,
		id: 'emailsList',
	},
	{
		component: List,
		id: 'list',
	},
	{
		component: Cards,
		id: 'cards',
	},
];

export function getViewById(requestedContentRendererId) {
	return new Promise((resolve) => {
		views.forEach((view) => {
			if (view.id === requestedContentRendererId) {
				resolve(view.component);
			}
		});
		throw new Error(
			`No content renderer found with the ID: "${requestedContentRendererId}"`
		);
	});
}
