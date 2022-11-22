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
import {TProperty} from './Properties';
import Tab, {TRawItem} from './Tab';

const columns = [
	{
		expanded: true,
		label: Liferay.Language.get('site-name'),
		value: 'name',
	},
	{
		expanded: true,
		label: Liferay.Language.get('friendly-url'),
		value: 'friendlyURL',
	},
	{
		expanded: true,
		label: Liferay.Language.get('assigned-property'),
		sortable: false,
		value: 'channelName',
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
		columns={columns.map(({value}) => value) as Array<keyof TRawItem>}
		emptyStateTitle={Liferay.Language.get('there-are-no-sites')}
		header={columns}
		initialIds={initialIds}
		noResultsTitle={Liferay.Language.get('no-sites-were-found')}
		onItemsChange={onSitesChange}
		property={property}
		requestFn={fetchSites}
	/>
);

export default SitesTab;
