/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomVerticalBar} from '@liferay/object-js-components-web';
import React, {ReactNode} from 'react';

import './RightSidebarRoot.scss';

interface IRightSidebarRoot {
	children: ReactNode;
}

export function RightSideBarRoot({children}: IRightSidebarRoot) {
	return (
		<CustomVerticalBar
			defaultActive="objectsModelBuilderRightSidebar"
			panelWidth={320}
			position="right"
			resize={false}
			triggerSideBarAnimation={true}
			verticalBarItems={[
				{
					title: 'objectsModelBuilderRightSidebar',
				},
			]}
		>
			{children}
		</CustomVerticalBar>
	);
}
