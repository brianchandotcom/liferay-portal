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

import {useConfig} from 'data-engine-js-components-web';
import React from 'react';

import Sidebar from '../../../../js/components/sidebar/Sidebar.es';
import FieldSetList from '../../../components/field-sets/FieldSetList';
import FieldTypeList from '../../../components/field-types/FieldTypeList.es';

export default function FieldsSidebarBody({keywords, setKeywords}) {
	const {allowFieldSets, tabs = []} = useConfig();

	const sidebarTabs = [
		{
			label: Liferay.Language.get('fields'),
			render: () => <FieldTypeList keywords={keywords} />,
		},
	];

	if (allowFieldSets) {
		sidebarTabs.push({
			label: Liferay.Language.get('fieldsets'),
			render: () => <FieldSetList serachTerm={keywords} />,
		});
	}

	sidebarTabs.push(...tabs);

	return (
		<Sidebar.Tabs
			searchTerm={keywords}
			setKeywords={setKeywords}
			tabs={sidebarTabs}
		/>
	);
}
