/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import SidebarPanel from '../../../SidebarPanel';
import BaseUser from '../../shared-components/BaseUser';

const User = (props) => {
	const {emailAddress, screenName, userId} = props.restProps;

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('section')}>
			<BaseUser
				{...props}
				emailAddress={emailAddress}
				identifier={props.subSectionIdentifier}
				reassignment
				screenName={screenName}
				sectionsLength={props.subSectionsLength}
				userId={userId}
			/>
		</SidebarPanel>
	);
};

export default User;
