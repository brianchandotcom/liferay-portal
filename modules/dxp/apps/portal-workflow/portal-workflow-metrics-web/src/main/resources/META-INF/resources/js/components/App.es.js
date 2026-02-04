/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Navigate, Outlet, RouterProvider, createHashRouter} from 'react-router';

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
import SLAContainer from './sla/SLAContainer.es';
import SLAFormPage from './sla/form-page/SLAFormPage.es';
import SLAListPage from './sla/list-page/SLAListPage.es';
import WorkloadByAssigneePage from './workload-by-assignee-page/WorkloadByAssigneePage.es';

const Layout = () => (
	<>
		<HeaderController basePath="/processes" />

		<div className="portal-workflow-metrics-app">
			<Outlet />
		</div>
	</>
);

const router = createHashRouter([
	{
		children: [
			{
				element: <Navigate replace to='/processes/20/1/overdueInstanceCount:desc' />,
				index: true,
			},
			{
				element: <ProcessListPage />,
				handle: {
					path: '/processes/:pageSize/:page/:sort',
				},
				path: '/processes/:pageSize/:page/:sort',
			},
			{
				children: [
					{
						element: <DashboardTab />,
						handle: {
							path: '/metrics/:processId/dashboard/:pageSize/:page/:sort',
						},
						path: 'dashboard/:pageSize/:page/:sort',
					},
					{
						element: <PerformanceTab />,
						handle: {
							path: '/metrics/:processId/performance',
						},
						path: 'performance',
					},
				],
				element: <ProcessMetricsContainer />,
				path: '/metrics/:processId',
			},
			{
				element: <InstanceListPage />,
				handle: {
					path: '/instance/:processId/:pageSize/:page/:sort',
				},
				path: '/instance/:processId/:pageSize/:page/:sort',
			},
			{
				children: [
					{
						element: <SLAListPage />,
						handle: {
							path: '/sla/:processId/list/:pageSize/:page',
						},
						path: 'list/:pageSize/:page',
					},
					{
						element: <SLAFormPage />,
						handle: {
							path: '/sla/:processId/new',
						},
						path: 'new',
					},
					{
						element: <SLAFormPage />,
						handle: {
							path: '/sla/:processId/edit/:id',
						},
						path: 'edit/:id',
					},
				],
				element: <SLAContainer />,
				path: '/sla/:processId',
			},
			{
				element: <PerformanceByStepPage />,
				handle: {
					path: '/performance/step/:processId/:pageSize/:page/:sort',
				},
				path: '/performance/step/:processId/:pageSize/:page/:sort',
			},
			{
				element: <WorkloadByAssigneePage />,
				handle: {
					path: '/workload/assignee/:processId/:pageSize/:page/:sort',
				},
				path: '/workload/assignee/:processId/:pageSize/:page/:sort',
			},
			{
				element: <PerformanceByAssigneePage />,
				handle: {
					path: '/performance/assignee/:processId/:pageSize/:page/:sort',
				},
				path: '/performance/assignee/:processId/:pageSize/:page/:sort',
			},
			{
				element: <SettingsContainer />,
				path: '/settings/*',
			},
		],
		element: <Layout />,
		path: '/',
	},
]);

const App = (props) => {
	return (
		<AppContextProvider {...props}>
			<FilterContextProvider>
				<RouterProvider router={router} />
			</FilterContextProvider>
		</AppContextProvider>
	);
};

export default App;
