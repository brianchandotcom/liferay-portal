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

import {fetchSites} from '../../utils/api';
import {TColumn} from '../table/types';
import {TProperty} from './Properties';
import Tab, {TRawItem} from './Tab';

enum EColumn {
	Name = 'name',
	FriendlyURL = 'friendlyURL',
	ChannelName = 'channelName',
}

const columns: TColumn[] = [
	{
		expanded: true,
		id: EColumn.Name,
		label: Liferay.Language.get('site-name'),
	},
	{
		id: EColumn.FriendlyURL,
		label: Liferay.Language.get('friendly-url'),
	},
	{
		id: EColumn.ChannelName,
		label: Liferay.Language.get('assigned-property'),
		sortable: false,
	},
];

interface ISiteTabProps {
	initialIds: number[];
	onSitesChange: (ids: number[]) => void;
	property: TProperty;
}

const SitesTab: React.FC<ISiteTabProps> = ({
	initialIds,
	onSitesChange,
	property,
}) => (
	<Tab
		columns={columns.map(({id}) => id) as Array<keyof TRawItem>}
		description={Liferay.Language.get('sites-tab-description')}
		emptyState={{
			noResultsTitle: Liferay.Language.get('no-sites-were-found'),
			title: Liferay.Language.get('there-are-no-sites'),
		}}
		header={columns}
		initialIds={initialIds}
		onItemsChange={onSitesChange}
		property={property}
		requestFn={fetchSites}
	/>
);

export default SitesTab;
