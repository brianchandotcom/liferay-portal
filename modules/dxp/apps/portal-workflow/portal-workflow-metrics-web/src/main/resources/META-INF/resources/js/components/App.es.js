/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {createRoutesFromElements, HashRouter, Navigate, Routes, Route} from 'react-router';

import {FilterContextProvider} from '../shared/components/filter/FilterContext.es';
import HeaderController from '../shared/components/header/HeaderController.es';
import {AppContextProvider} from './AppContext.es';
import InstanceListPage from './instance-list-page/InstanceListPage.es';
import PerformanceByAssigneePage from './performance-by-assignee-page/PerformanceByAssigneePage.es';
import PerformanceByStepPage from './performance-by-step-page/PerformanceByStepPage.es';
import ProcessListPage from './process-list-page/ProcessListPage.es';
import ProcessMetricsContainer, {
	DashboardTab,
	PerformanceTab,
} from './process-metrics/ProcessMetricsContainer.es';
import SettingsContainer from './settings/SettingsContainer.es';
import IndexesPage from './settings/indexes-page/IndexesPage.es';
import SLAContainer from './sla/SLAContainer.es';
import SLAFormPage from './sla/form-page/SLAFormPage.es';
import SLAListPage from './sla/list-page/SLAListPage.es';
import WorkloadByAssigneePage from './workload-by-assignee-page/WorkloadByAssigneePage.es';

const appRoutes = (
	<>
		<Route path="/" element={<Navigate replace to='/processes/20/1/overdueInstanceCount:desc' />} />

		<Route path="/processes/:pageSize/:page/:sort" element={<ProcessListPage />} />

		<Route path="/metrics/:processId" element={<ProcessMetricsContainer />}>
			<Route path="dashboard/:pageSize/:page/:sort" element={<DashboardTab />} />
			<Route path="performance" element={<PerformanceTab />} />
		</Route>

		<Route path="/instance/:processId/:pageSize/:page/:sort" element={<InstanceListPage />} />

		<Route path="/sla/:processId" element={<SLAContainer />}>
			<Route path="list/:pageSize/:page" element={<SLAListPage />} />
			<Route path="new" element={<SLAFormPage />} />
			<Route path="edit/:id" element={<SLAFormPage />} />
		</Route>

		<Route path="/performance/step/:processId/:pageSize/:page/:sort" element={<PerformanceByStepPage />} />

		<Route path="/workload/assignee/:processId/:pageSize/:page/:sort" element={<WorkloadByAssigneePage />} />

		<Route path="/performance/assignee/:processId/:pageSize/:page/:sort" element={<PerformanceByAssigneePage />} />

		<Route path="/settings" element={<SettingsContainer />}>
			<Route path="indexes" element={<IndexesPage />} />
		</Route>
	</>
);

export const appDataRoutes = createRoutesFromElements(appRoutes);

const App = (props) => {
	return (
		<AppContextProvider {...props}>
			<FilterContextProvider>
					<HashRouter>
						<HeaderController basePath="/processes" />

						<div className="portal-workflow-metrics-app">
							<Routes>{appRoutes}</Routes>
						</div>
					</HashRouter>
			</FilterContextProvider>
		</AppContextProvider>
	);
};

export default App;
