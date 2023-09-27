/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
