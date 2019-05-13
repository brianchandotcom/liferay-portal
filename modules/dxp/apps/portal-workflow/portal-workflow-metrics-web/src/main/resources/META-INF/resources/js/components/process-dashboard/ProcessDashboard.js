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
import AlertMessage from './AlertMessage';
import { AppContext } from '../AppContext';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import DropDownHeader from './DropDownHeader';
import { getPathname } from '../../shared/components/tabs/TabItem';
import React from 'react';
import { sub } from '../../shared/util/lang';
import Tabs from '../../shared/components/tabs/Tabs';
import WorkloadByStepCard from './workload-by-step/WorkloadByStepCard';

class ProcessDashboard extends React.Component {
	constructor(props) {
		super(props);
		this.state = { blockedSLACount: 0, slaCount: null };
	}

	componentDidMount() {
		Promise.all([this.loadBlockedSLACount(), this.loadSLACount()]).then(
			([blockedSLACount, slaCount]) =>
				this.setState({ blockedSLACount, slaCount })
		);
	}

	loadBlockedSLACount() {
		return this.context.client
			.get(`/processes/${this.props.processId}/slas?page=1&pageSize=1&status=2`)
			.then(({ data: { totalCount } }) => totalCount);
	}

	loadSLACount() {
		return this.context.client
			.get(`/processes/${this.props.processId}/slas?page=1&pageSize=1`)
			.then(({ data: { totalCount } }) => totalCount);
	}

	render() {
		const { blockedSLACount = 0, slaCount } = this.state;
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
				<DropDownHeader>
					<DropDownHeader.Item>
						<ChildLink
							className="dropdown-item"
							to={`/slas/${processId}/${defaultDelta}/1`}
						>
							{Liferay.Language.get('sla-settings')}
						</ChildLink>
					</DropDownHeader.Item>
				</DropDownHeader>

				<Tabs tabs={[pendingTab, completedTab]} />

				{!!blockedSLACount && (
					<AlertMessage iconName="exclamation-full">
						<React.Fragment>
							{`${sub(blockedSLAText, [
								blockedSLACount
							])} ${Liferay.Language.get(
								'fix-the-sla-configuration-to-resume-accurate-reporting'
							)} `}

							<ChildLink to={`/slas/${processId}/${defaultDelta}/1`}>
								<strong>{Liferay.Language.get('set-up-slas')}</strong>
							</ChildLink>
						</React.Fragment>
					</AlertMessage>
				)}

				{slaCount === 0 && (
					<AlertMessage iconName="warning-full" type="warning">
						<React.Fragment>
							{`${Liferay.Language.get(
								'no-slas-are-defined-for-this-process'
							)} `}

							<ChildLink to={`/sla/new/${processId}`}>
								<strong>{Liferay.Language.get('add-a-new-sla')}</strong>
							</ChildLink>
						</React.Fragment>
					</AlertMessage>
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