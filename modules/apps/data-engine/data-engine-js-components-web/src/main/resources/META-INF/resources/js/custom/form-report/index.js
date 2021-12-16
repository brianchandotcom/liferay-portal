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

import CardShortcut from './components/card-shortcut/CardShortcut';
import CardList from './components/card/CardList';
import EmptyState from './components/empty-state/EmptyState';
import Sidebar from './components/sidebar/Sidebar';
import {SidebarContextProvider} from './components/sidebar/SidebarContext';
import {transformSearchLocationValues} from './utils/searchLocation';

import './index.scss';

export default function FormReport({
	data,
	fields,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) {
	if (!data || data.length === 0) {
		return <EmptyState />;
	}

	const {data: newData, fields: newFields} = transformSearchLocationValues(
		fields,
		JSON.parse(data)
	);

	return (
		<SidebarContextProvider
			formReportRecordsFieldValuesURL={formReportRecordsFieldValuesURL}
			portletNamespace={portletNamespace}
		>
			<div className="lfr-de__form-report">
				<div className="report-cards-area">
					<CardList data={newData} fields={newFields} />
				</div>

				<div className="report-cards-shortcut">
					<CardShortcut fields={newFields} />
				</div>
			</div>

			<Sidebar />
		</SidebarContextProvider>
	);
}
