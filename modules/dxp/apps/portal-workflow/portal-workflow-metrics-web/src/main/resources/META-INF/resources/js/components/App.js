import {Redirect, Route, HashRouter as Router, Switch} from 'react-router-dom';
import {AppContext} from './AppContext';
import fetch from '../shared/rest/fetch';
import HeaderController from '../shared/components/header-controller/HeaderController';
import InstanceListCard from './process-dashboard/instance-list/InstanceListCard';
import ProcessDashboard from './process-dashboard/ProcessDashboard';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';
import SLAForm from './sla/SLAForm';
import SLAListCard from './sla/SLAListCard';
import {withParams} from '../shared/components/router/routerUtil';

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
								render={withParams(ProcessListCard)}
							/>

							<Route
								path="/dashboard/:processId"
								render={withParams(ProcessDashboard)}
							/>

							<Route
								path="/instances/:processId/:pageSize/:page"
								render={withParams(InstanceListCard)}
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
						</Switch>
					</div>
				</AppContext.Provider>
			</Router>
		);
	}
}
