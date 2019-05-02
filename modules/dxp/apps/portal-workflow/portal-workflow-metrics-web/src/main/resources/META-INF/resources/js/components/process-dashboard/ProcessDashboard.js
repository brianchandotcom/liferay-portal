import {
	CompletedItemsCard,
	PendingItemsCard
} from './process-items/ProcessItemsCard';
import {
	Redirect,
	Route,
	HashRouter as Router,
	Switch
} from 'react-router-dom';
import { AppContext } from '../AppContext';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import { getPathname } from '../../shared/components/tabs/TabItem';
import Icon from '../../shared/components/Icon';
import React from 'react';
import { sub } from '../../shared/util/lang';
import Tabs from '../../shared/components/tabs/Tabs';
import WorkloadByStepCard from './workload-by-step/WorkloadByStepCard';

class ProcessDashboard extends React.Component {
	constructor(props) {
		super(props);
		this.state = { blockedSLACount: 0 };
	}

	componentDidMount() {
		this.loadBlockedSLA();
	}

	loadBlockedSLA() {
		const { processId } = this.props;
		const { client } = this.context;

		client
			.get(`/processes/${processId}/slas?page=1&pageSize=1&status=2`)
			.then(({ data: { totalCount: blockedSLACount } }) => {
				this.setState({
					blockedSLACount
				});
			});
	}

	render() {
		const { blockedSLACount = 0 } = this.state;
		const { processId, query } = this.props;
		const { defaultDelta } = this.context;

		let blockedSLAText = Liferay.Language.get('x-sla-is-blocked');

		if (blockedSLACount !== 1) {
			blockedSLAText = Liferay.Language.get('x-slas-are-blocked');
		}

		const completedTab = {
			key: 'completed',
			name: Liferay.Language.get('completed'),
			params: {
				processId
			},
			path: '/dashboard/:processId/completed',
			query
		};
		const pendingTab = {
			key: 'pending',
			name: Liferay.Language.get('pending'),
			params: {
				page: 1,
				pageSize: defaultDelta,
				processId,
				sort: encodeURIComponent('overdueInstanceCount:asc')
			},
			path: '/dashboard/:processId/pending/:pageSize/:page/:sort',
			query
		};

		const defaultPathname = getPathname(pendingTab.params, pendingTab.path);

		return (
			<div className="workflow-process-dashboard">
				<Tabs tabs={[pendingTab, completedTab]} />

				{!!blockedSLACount && (
					<div className="container-fluid-1280" style={{ paddingTop: '24px' }}>
						<div className="alert alert-danger alert-dismissible" role="alert">
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">{Liferay.Language.get('error')}</strong>

							{`${sub(blockedSLAText, [
								blockedSLACount
							])} ${Liferay.Language.get(
								'fix-the-sla-configuration-to-resume-accurate-reporting'
							)}`}

							<ChildLink to={`/slas/${processId}/${defaultDelta}/1`}>
								<strong>{Liferay.Language.get('set-up-slas')}</strong>
							</ChildLink>

							<button
								aria-label="Close"
								className="close"
								data-dismiss="alert"
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</div>
					</div>
				)}

				<Router>
					<Switch>
						<Redirect
							exact
							from="/dashboard/:processId"
							to={{
								pathname: defaultPathname,
								search: query
							}}
						/>

						<Route
							exact
							path={pendingTab.path}
							render={withParams(PendingItemsCard, WorkloadByStepCard)}
						/>

						<Route
							exact
							path={completedTab.path}
							render={withParams(CompletedItemsCard)}
						/>
					</Switch>
				</Router>
			</div>
		);
	}
}

export const withParams = (...args) => ({ match: { params } }) =>
	args.map((Component, index) => <Component {...params} key={index} />);

ProcessDashboard.contextType = AppContext;
export default ProcessDashboard;