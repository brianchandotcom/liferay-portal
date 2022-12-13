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

import {fetchChannels} from '../../utils/api';
import {TColumn} from '../table/types';
import {TProperty} from './Properties';
import Tab, {TRawItem} from './Tab';

enum EColumn {
	Name = 'name',
	SiteName = 'siteName',
	ChannelName = 'channelName',
}

const columns: TColumn[] = [
	{
		expanded: true,
		id: EColumn.Name,
		label: Liferay.Language.get('channel-name'),
	},
	{
		expanded: true,
		id: EColumn.SiteName,
		label: Liferay.Language.get('related-site'),
		sortable: false,
	},
	{
		expanded: true,
		id: EColumn.ChannelName,
		label: Liferay.Language.get('assigned-property'),
		sortable: false,
	},
];

interface IChannelTabProps {
	initialIds: number[];
	onChannelsChange: (ids: number[]) => void;
	property: TProperty;
}

const ChannelTab: React.FC<IChannelTabProps> = ({
	initialIds,
	onChannelsChange,
	property,
}) => (
	<Tab
		columns={columns.map(({id}) => id) as Array<keyof TRawItem>}
		description={Liferay.Language.get(
			'analytics-cloud-assign-commerce-channel-help'
		)}
		emptyStateTitle={Liferay.Language.get('there-are-no-channels')}
		enableCheckboxs={!!property.commerceSyncEnabled}
		header={columns}
		initialIds={initialIds}
		noResultsTitle={Liferay.Language.get('no-channels-were-found')}
		onItemsChange={onChannelsChange}
		property={property}
		requestFn={fetchChannels}
	/>
);

export default ChannelTab;
