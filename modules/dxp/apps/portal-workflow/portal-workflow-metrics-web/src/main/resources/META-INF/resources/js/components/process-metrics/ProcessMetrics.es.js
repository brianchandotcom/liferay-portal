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

import {withParams} from '../../shared/components/router/routerUtil.es';
import {ChildLink} from '../../shared/components/router/routerWrapper.es';
import {getPathname} from '../../shared/components/tabs/TabItem.es';
import Tabs from '../../shared/components/tabs/Tabs.es';
import {sub} from '../../shared/util/lang.es';
import {openErrorToast} from '../../shared/util/toast.es';
import {AppContext} from '../AppContext.es';
import AlertMessage from './AlertMessage.es';
import DropDownHeader from './DropDownHeader.es';
import CompletionVelocityCard from './completion-velocity/CompletionVelocityCard.es';
import PerformanceByAssigneeCard from './performance-by-assignee-card/PerformanceByAssigneeCard.es';
import PerformanceByStepCard from './performance-by-step-card/PerformanceByStepCard.es';
import CompletedItemsCard from './process-items/CompletedItemsCard.es';
import PendingItemsCard from './process-items/PendingItemsCard.es';
import WorkloadByAssigneeCard from './workload-by-assignee-card/WorkloadByAssigneeCard.es';
import WorkloadByStepCard from './workload-by-step-card/WorkloadByStepCard.es';

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
		const performanceTab = {
			key: 'performance',
			name: Liferay.Language.get('performance'),
			params: {
				processId
			},
			path: '/metrics/:processId/performance',
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
					<AlertMessage className="mb-0" iconName="exclamation-full">
						<>
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
						</>
					</AlertMessage>
				)}

				{slaCount === 0 && (
					<AlertMessage
						className="mb-0"
						iconName="warning-full"
						type="warning"
					>
						<>
							{`${Liferay.Language.get(
								'no-slas-are-defined-for-this-process'
							)} `}

							<ChildLink to={`/sla/new/${processId}`}>
								<strong>
									{Liferay.Language.get('add-a-new-sla')}
								</strong>
							</ChildLink>
						</>
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
							render={withParams(DashboardTab)}
						/>

						<Route
							exact
							path={performanceTab.path}
							render={withParams(
								CompletedItemsCard,
								CompletionVelocityCard,
								PerformanceByStepCard,
								PerformanceByAssigneeCard
							)}
						/>
					</Switch>
				</Router>
			</div>
		);
	}
}

const DashboardTab = props => {
	return (
		<div className="container-fluid-1280">
			<div className="row">
				<div className="col-md-9 p-0">
					<PendingItemsCard {...props} />

					<WorkloadByStepCard {...props} />
				</div>

				<div className="col-md-3 p-0">
					<WorkloadByAssigneeCard {...props} />
				</div>
			</div>
		</div>
	);
};

ProcessMetrics.contextType = AppContext;
export default ProcessMetrics;
