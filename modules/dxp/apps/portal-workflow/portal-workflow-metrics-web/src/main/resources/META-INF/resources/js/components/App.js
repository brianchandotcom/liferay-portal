import {
	Redirect,
	Route,
	HashRouter as Router,
	Switch
} from 'react-router-dom';
import { AppContext } from './AppContext';
import fetch from '../shared/rest/fetch';
import HeaderController from '../shared/components/header-controller/HeaderController';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';
import SLAForm from './sla/SLAForm';
import SLAListCard from './sla/SLAListCard';

/**
 * @class
 * @classdesc Application starter.
 */
export default class AppComponent extends React.Component {
	constructor(props) {
		super(props);

		this.contextState = {
			client: fetch,
			companyId: props.companyId,
			defaultDelta: props.defaultDelta,
			deltas: props.deltas,
			maxPages: props.maxPages,
			setTitle: this.setTitle.bind(this)
		};

		this.state = {
			title: null
		};
	}

	setTitle(title) {
		this.setState({ title });
	}

	render() {
		const { title } = this.state;
		const withParams = Component => ({ match: { params } }) => (
			<Component {...params} />
		);

		return (
			<Router>
				<AppContext.Provider value={this.contextState}>
					<HeaderController basePath="/processes" title={title} />

					<div className="portal-workflow-metrics-app">
						<Switch>
							<Redirect exact from="/" to="/processes" />

							<Route
								path="/processes/:pageSize?/:page?"
								render={withParams(ProcessListCard)}
							/>

							<Route
								exact
								path="/slas/:processId/:pageSize?/:page?"
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