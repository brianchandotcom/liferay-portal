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

import React from 'react';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import ListView from '../../components/list-view/ListView.es';
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
		value: Liferay.Language.get('create-date'),
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date'),
	},
];

export default () => {
	return (
		<>
			<ControlMenu
				title={Liferay.Language.get(
					'javax.portlet.title.com_liferay_app_builder_web_internal_portlet_ObjectsPortlet'
				)}
			/>
			<ListView
				columns={COLUMNS}
				emptyState={{
					title: Liferay.Language.get(
						'there-are-no-native-objects-yet'
					),
				}}
				endpoint={`/o/data-engine/v2.0/data-definitions/by-content-type/native-object`}
			>
				{item => ({
					...item,
					dateCreated: fromNow(item.dateCreated),
					dateModified: fromNow(item.dateModified),
					name: item.name.en_US,
				})}
			</ListView>
		</>
	);
};
