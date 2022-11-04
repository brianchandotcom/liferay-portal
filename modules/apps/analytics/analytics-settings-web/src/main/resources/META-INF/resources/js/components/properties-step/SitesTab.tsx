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

import {TProperty} from '../../pages/wizard/PropertyStep';
import {fetchSites} from '../../utils/api';
import {TItem} from '../table/Table';
import Tab from './Tab';

interface ISiteTabProps {
	onSitesChange: (items: TItem[]) => void;
	property: TProperty;
}

const SitesTab: React.FC<ISiteTabProps> = ({onSitesChange, property}) => {
	return (
		<Tab
			columns={['name', 'friendlyURL', 'channelName']}
			emptyStateTitle={Liferay.Language.get('there-are-no-sites')}
			fetchFn={fetchSites}
			header={[
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
			]}
			noResultsTitle={Liferay.Language.get('no-sites-were-found')}
			onItemsChange={onSitesChange}
			property={property}
		/>
	);
};

export default SitesTab;
