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
import React, {Fragment, useState} from 'react';
import ListView from '../../components/list-view/ListView.es';
import {confirmDelete} from '../../utils/client.es';
import {AddButton} from '../../components/management-toolbar/index.es';
import Popover from '../../components/popover/Popover.es';

const CUSTOM_OBJECTS = {
	ACTIONS: [
		{
			link: item => `#/custom-object/${item.id}/form-views`,
			name: Liferay.Language.get('form-views')
		},
		{
			link: item => `#/custom-object/${item.id}/table-views`,
			name: Liferay.Language.get('table-views')
		},
		{
			link: item => `#/custom-object/${item.id}/deployments`,
			name: Liferay.Language.get('deployments')
		},
		{
			name: 'divider'
		},
		{
			callback: confirmDelete('/o/data-engine/v1.0/data-definitions/'),
			name: Liferay.Language.get('delete')
		}
	],
	COLUMNS: [
		{
			key: 'name',
			link: item => `/custom-object/${item.id}/form-views`,
			value: Liferay.Language.get('name')
		},
		{
			key: 'dateCreated',
			value: Liferay.Language.get('create-date')
		},
		{
			asc: false,
			key: 'dateModified',
			value: Liferay.Language.get('modified-date')
		}
	],
	EMPTY_STATE: {
		empty: {
			description: Liferay.Language.get(
				'custom-objects-define-the-types-of-data-your-business-application-needs'
			),
			title: Liferay.Language.get('there-are-no-custom-objects-yet')
		}
	},
	FORMATTER: items =>
		items.map(item => ({
			dateCreated: moment(item.dateCreated).fromNow(),
			dateModified: moment(item.dateModified).fromNow(),
			id: item.id,
			name: item.name.en_US
		}))
};

export default () => {
	const [popoverVisible, setPopoverVisible] = useState(false);

	const onClickAddButton = () => setPopoverVisible(!popoverVisible);

	return (
		<ListView
			actions={CUSTOM_OBJECTS.ACTIONS}
			addButton={() => (
				<Fragment>
					<AddButton
						onClick={onClickAddButton}
						tooltip={Liferay.Language.get('new-custom-object')}
					/>

					<Popover
						alignElement={this._titleRef.current}
						title={'Test'}
						visible={popoverVisible}
					/>
				</Fragment>
			)}
			columns={CUSTOM_OBJECTS.COLUMNS}
			emptyState={CUSTOM_OBJECTS.EMPTY_STATE}
			endpoint={`/o/data-engine/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/data-definitions`}
			formatter={CUSTOM_OBJECTS.FORMATTER}
		/>
	);
};
