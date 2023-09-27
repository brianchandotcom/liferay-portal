/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		id: EColumn.SiteName,
		label: Liferay.Language.get('related-site'),
		sortable: false,
	},
	{
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
		description={Liferay.Language.get('channels-tab-description')}
		emptyState={{
			noResultsTitle: Liferay.Language.get('no-channels-were-found'),
			title: Liferay.Language.get('there-are-no-channels'),
		}}
		enableCheckboxs={!!property.commerceSyncEnabled}
		header={columns}
		initialIds={initialIds}
		onItemsChange={onChannelsChange}
		property={property}
		requestFn={fetchChannels}
	/>
);

export default ChannelTab;
