import {Redirect, Route, HashRouter as Router, Switch} from 'react-router-dom';
import AlertMessage from './AlertMessage';
import {AppContext} from '../AppContext';
import {ChildLink} from '../../shared/components/router/routerWrapper';
import CompletedItemsCard from './process-items/CompletedItemsCard';
import DropDownHeader from './DropDownHeader';
import {getPathname} from '../../shared/components/tabs/TabItem';
import {openErrorToast} from '../../shared/util/toast';
import PendingItemsCard from './process-items/PendingItemsCard';
import React from 'react';
import {sub} from '../../shared/util/lang';
import Tabs from '../../shared/components/tabs/Tabs';
import {withParams} from '../../shared/components/router/routerUtil';
import WorkloadByStepCard from './workload-by-step/WorkloadByStepCard';

class ProcessMetrics extends React.Component {
	constructor(props) {
		super(props);
		this.state = {blockedSLACount: 0, slaCount: null};
	}

	componentDidMount() {
		Promise.all([this.loadBlockedSLACount(), this.loadSLACount()])
			.then(([blockedSLACount, slaCount]) =>
				this.setState({blockedSLACount, slaCount})
			)
			.catch(this.showLoadingError);
	}

	loadBlockedSLACount() {
		return this.context.client
			.get(
				`/processes/${this.props.processId}/slas?page=1&pageSize=1&status=2`
			)
			.then(({data: {totalCount}}) => totalCount)
			.catch(this.showLoadingError);
	}

	loadSLACount() {
		return this.context.client
			.get(`/processes/${this.props.processId}/slas?page=1&pageSize=1`)
			.then(({data: {totalCount}}) => totalCount)
			.catch(this.showLoadingError);
	}

	showLoadingError() {
		openErrorToast({
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)
		});
	}

	render() {
		const {blockedSLACount = 0, slaCount} = this.state;
		const {processId, query} = this.props;
		const {defaultDelta} = this.context;

		let blockedSLAText = Liferay.Language.get('x-sla-is-blocked');

		if (blockedSLACount !== 1) {
			blockedSLAText = Liferay.Language.get('x-slas-are-blocked');
		}

		const performanceTab = {
			key: 'performance',
			name: Liferay.Language.get('performance'),
			params: {
				processId
			},
			path: '/metrics/:processId/performance',
			query
		};
		const dashboardTab = {
				key: 'dashboard',
				name: Liferay.Language.get('dashboard'),
				params: {
					page: 1,
					pageSize: defaultDelta,
					processId,
					sort: encodeURIComponent('overdueInstanceCount:asc')
				},
				path: '/metrics/:processId/dashboard/:pageSize/:page/:sort',
				query
		};

		const defaultPathname = getPathname(
			dashboardTab.params,
			dashboardTab.path
		);

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

				<Tabs tabs={[dashboardTab, performanceTab]} />

				{!!blockedSLACount && (
					<AlertMessage iconName="exclamation-full">
						<React.Fragment>
							{`${sub(blockedSLAText, [
								blockedSLACount
							])} ${Liferay.Language.get(
								'fix-the-sla-configuration-to-resume-accurate-reporting'
							)} `}

							<ChildLink
								to={`/slas/${processId}/${defaultDelta}/1`}
							>
								<strong>
									{Liferay.Language.get('set-up-slas')}
								</strong>
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
								<strong>
									{Liferay.Language.get('add-a-new-sla')}
								</strong>
							</ChildLink>
						</React.Fragment>
					</AlertMessage>
				)}

				<Router>
					<Switch>
						<Redirect
							exact
							from="/metrics/:processId"
							to={{
								pathname: defaultPathname,
								search: query
							}}
						/>

						<Route
							exact
							path={dashboardTab.path}
							render={withParams(
								PendingItemsCard,
								WorkloadByStepCard
							)}
						/>

						<Route
							exact
							path={performanceTab.path}
							render={withParams(CompletedItemsCard)}
						/>
					</Switch>
				</Router>
			</div>
		);
	}
}

ProcessMetrics.contextType = AppContext;
export default ProcessMetrics;
