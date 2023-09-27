/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	displayChartAsTable,
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
					<CardList
						data={newData}
						displayChartAsTable={displayChartAsTable}
						fields={newFields}
					/>
				</div>

				<Sidebar />
			</SidebarContextProvider>
		</div>
	);
}
