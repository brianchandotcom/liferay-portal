/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import HeaderController from '../shared/components/header/HeaderController.es';
import {withParams} from '../shared/components/router/routerUtil.es';
import {AppContextProvider} from './AppContext.es';
import InstanceListPage from './instance-list-page/InstanceListPage.es';
import PerformanceByAssigneePage from './performance-by-assignee-page/PerformanceByAssigneePage.es';
import PerformanceByStepPage from './performance-by-step-page/PerformanceByStepPage.es';
import ProcessListPage from './process-list-page/ProcessListPage.es';
import ProcessMetricsContainer from './process-metrics/ProcessMetricsContainer.es';
import SettingsContainer from './settings/SettingsContainer.es';
import SLAContainer from './sla/SLAContainer.es';
import WorkloadByAssigneePage from './workload-by-assignee-page/WorkloadByAssigneePage.es';

const App = (props) => {
	return (
		<Router>
			<AppContextProvider {...props}>
				<HeaderController basePath="/processes" />

				<div className="portal-workflow-metrics-app">
					<Switch>
						<Route
							exact
							path="/"
							render={withParams(ProcessListPage)}
						/>

						<Route
							path="/processes/:pageSize/:page/:sort"
							render={withParams(ProcessListPage)}
						/>

						<Route
							path="/metrics/:processId"
							render={withParams(ProcessMetricsContainer)}
						/>

						<Route
							path="/instance/:processId/:pageSize/:page/:sort"
							render={withParams(InstanceListPage)}
						/>

						<Route
							component={SLAContainer}
							path="/sla/:processId"
						/>

						<Route
							exact
							path="/performance/step/:processId/:pageSize/:page/:sort"
							render={withParams(PerformanceByStepPage)}
						/>

						<Route
							exact
							path="/workload/assignee/:processId/:pageSize/:page/:sort"
							render={withParams(WorkloadByAssigneePage)}
						/>

						<Route
							exact
							path="/performance/assignee/:processId/:pageSize/:page/:sort"
							render={withParams(PerformanceByAssigneePage)}
						/>

						<Route component={SettingsContainer} path="/settings" />
					</Switch>
				</div>
			</AppContextProvider>
		</Router>
	);
};

export default App;
