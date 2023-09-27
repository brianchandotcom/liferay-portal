/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useConfig} from 'data-engine-js-components-web';
import React from 'react';

import FieldSetList from '../../../components/field-sets/FieldSetList';
import FieldTypeList from '../../../components/field-types/FieldTypeList.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';

export default function FieldsSidebarBody({
	keywords,
	searchClicked,
	setKeywords,
}) {
	const {allowFieldSets, tabs = []} = useConfig();

	const sidebarTabs = [
		{
			label: Liferay.Language.get('fields'),
			render: () => (
				<FieldTypeList
					keywords={keywords}
					searchClicked={searchClicked}
				/>
			),
		},
	];

	if (allowFieldSets) {
		sidebarTabs.push({
			label: Liferay.Language.get('fieldsets'),
			render: () => <FieldSetList searchTerm={keywords} />,
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
