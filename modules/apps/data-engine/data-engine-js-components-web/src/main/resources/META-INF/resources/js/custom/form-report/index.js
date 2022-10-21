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

import CardMenu from './components/card-menu/CardMenu';
import CardShortcut from './components/card-shortcut/CardShortcut';
import CardList from './components/card/CardList';
import EmptyState from './components/empty-state/EmptyState';
import Sidebar from './components/sidebar/Sidebar';
import {SidebarContextProvider} from './components/sidebar/SidebarContext';
import {transformSearchLocationValues} from './utils/searchLocation';

import './index.scss';

export default function FormReport({
	data,
	dataEngineModule,
	fields,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) {
	if (!data || !data.length) {
		return <EmptyState />;
	}

	const {data: newData, fields: newFields} = transformSearchLocationValues(
		fields,
		JSON.parse(data)
	);

	return (
		<div className="lfr-de__form-report">
			<SidebarContextProvider
				dataEngineModule={dataEngineModule}
				formReportRecordsFieldValuesURL={
					formReportRecordsFieldValuesURL
				}
				portletNamespace={portletNamespace}
			>
				<div className="lfr-de__form-report--vertical-nav">
					<CardMenu fields={newFields} />
				</div>

				<div className="lfr-de__form-report--cards-shortcut">
					<CardShortcut fields={newFields} />
				</div>

				<div className="container-fluid container-fluid-max-xl lfr-de__form-report--cards-area">
					<CardList data={newData} fields={newFields} />
				</div>

				<Sidebar />
			</SidebarContextProvider>
		</div>
	);
}
