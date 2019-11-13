/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import {Redirect, Route, HashRouter as Router, Switch} from 'react-router-dom';

import HeaderController from '../shared/components/header-controller/HeaderController.es';
import {withParams} from '../shared/components/router/routerUtil.es';
import fetch from '../shared/rest/fetch.es';
import {AppContext} from './AppContext.es';
import InstanceListPage from './instance-list-page/InstanceListPage.es';
import PerformanceByAssigneePage from './performance-by-assignee-page/PerformanceByAssigneePage.es';
import PerformanceByStepPage from './performance-by-step-page/PerformanceByStepPage.es';
import ProcessListPage from './process-list-page/ProcessListPage.es';
import ProcessMetrics from './process-metrics/ProcessMetrics.es';
import SLAForm from './sla/SLAForm.es';
import SLAListCard from './sla/SLAListCard.es';
import WorkloadByAssigneePage from './workload-by-assignee-page/WorkloadByAssigneePage.es';

/**
 * @class
 * @classdesc Application starter.
 */
export default class AppComponent extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			client: fetch,
			companyId: props.companyId,
			defaultDelta: props.defaultDelta,
			deltas: props.deltas,
			isAmPm: props.isAmPm,
			maxPages: props.maxPages,
			namespace: props.namespace,
			setStatus: this.setStatus.bind(this),
			setTitle: this.setTitle.bind(this),
			status: null,
			title: Liferay.Language.get('metrics')
		};
	}

	setStatus(status, callback) {
		this.setState({status}, callback);
	}

	setTitle(title) {
		this.setState({title});
	}

	render() {
		const {defaultDelta, namespace, title} = this.state;

		return (
			<Router>
				<AppContext.Provider value={this.state}>
					<HeaderController
						basePath="/processes"
						namespace={namespace}
						title={title}
					/>

					<div className="portal-workflow-metrics-app">
						<Switch>
							<Redirect
								exact
								from="/"
								to={`/processes/${defaultDelta}/1/${encodeURIComponent(
									'overdueInstanceCount:desc'
								)}`}
							/>

							<Route
								path="/processes/:pageSize/:page/:sort/:search?"
								render={withParams(ProcessListPage)}
							/>

							<Route
								path="/metrics/:processId"
								render={withParams(ProcessMetrics)}
							/>

							<Route
								path="/instance/:processId/:pageSize/:page"
								render={withParams(InstanceListPage)}
							/>

							<Route
								exact
								path="/slas/:processId/:pageSize/:page"
								render={withParams(SLAListCard)}
							/>

							<Route
								exact
								path="/sla/new/:processId"
								render={withParams(SLAForm)}
							/>

							<Route
								exact
								path="/sla/edit/:processId/:id"
								render={withParams(SLAForm)}
							/>

							<Route
								exact
								path="/performance/step/:processId/:pageSize/:page/:sort/:search?"
								render={withParams(PerformanceByStepPage)}
							/>

							<Route
								exact
								path="/workload/assignee/:processId/:pageSize/:page/:sort/:search?"
								render={withParams(WorkloadByAssigneePage)}
							/>

							<Route
								exact
								path="/performance/assignee/:processId/:pageSize/:page/:sort/:search?"
								render={withParams(PerformanceByAssigneePage)}
							/>
						</Switch>
					</div>
				</AppContext.Provider>
			</Router>
		);
	}
}
