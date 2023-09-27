/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {SidebarContext} from '../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/sidebar/SidebarContext';

export default function SidebarContextProviderWrapper({
	children,
	...otherProps
}) {
	const field = {
		icon: 'text',
		label: 'Text',
		name: 'Text',
		options: {Option: 'Option'},
		title: 'Text',
		type: 'text',
	};
	const sidebarState = {
		field,
		isOpen: true,
		totalEntries: 6,
	};
	const context = {
		...sidebarState,
		formReportRecordsFieldValuesURL: 'http://localhost:8080/',
		portletNamespace: '_portlet_namespace_',
		toggleSidebar: jest.fn(() => !sidebarState.isOpen),
		...otherProps,
	};

	return (
		<SidebarContext.Provider value={context}>
			{children}
		</SidebarContext.Provider>
	);
}
