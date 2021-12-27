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
