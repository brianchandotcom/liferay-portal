/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';
import BaseUser from '../../shared-components/BaseUser';

const User = (props) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const updateSelectedItem = (values) => {
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				assignments: {
					assignmentType: ['user'],
					emailAddress: values.map(({emailAddress}) => emailAddress),
					sectionsData: values.map((values) => values),
				},
			},
		}));
	};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('section')}>
			<BaseUser updateSelectedItem={updateSelectedItem} {...props} />
		</SidebarPanel>
	);
};

export default User;
