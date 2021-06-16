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

import CardShortcut from './components/card-shortcut/CardShortcut.es';
import CardList from './components/card/CardList.es';
import Sidebar from './components/sidebar/Sidebar.es';
import {SidebarContextProvider} from './components/sidebar/SidebarContext.es';
import {transformSearchLocationValues} from './utils/searchLocation.es';

export default ({
	data,
	fields,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) => {
	const {data: newData, fields: newFields} = transformSearchLocationValues(
		fields,
		data
	);

	return (
		<SidebarContextProvider
			formReportRecordsFieldValuesURL={formReportRecordsFieldValuesURL}
			portletNamespace={portletNamespace}
		>
			<div className="report-cards-area" key="report-cards">
				<CardList data={newData} fields={newFields} />
			</div>

			<div className="report-cards-shortcut" key="report-cards-shortcut">
				<CardShortcut fields={newFields} />
			</div>

			<Sidebar />
		</SidebarContextProvider>
	);
};
