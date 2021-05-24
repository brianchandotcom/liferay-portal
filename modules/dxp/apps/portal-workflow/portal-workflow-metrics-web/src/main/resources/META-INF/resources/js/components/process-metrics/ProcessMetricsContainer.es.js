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

import ClayLayout from '@clayui/layout';
import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useContext, useMemo} from 'react';
import {Route, Switch} from 'react-router-dom';

import HeaderKebab from '../../shared/components/header/HeaderKebab.es';
import MetricsCalculatedInfo from '../../shared/components/last-updated-info/MetricsCalculatedInfo.es';
import NavbarTabs from '../../shared/components/navbar-tabs/NavbarTabs.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse, stringify} from '../../shared/components/router/queryString.es';
import {
	getPathname,
	withParams,
} from '../../shared/components/router/routerUtil.es';
import {useDateModified} from '../../shared/hooks/useDateModified.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {AppContext} from '../AppContext.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import CompletedItemsCard from '../process-metrics/process-items/CompletedItemsCard.es';
import SLAInfo from './SLAInfo.es';
import CompletionVelocityCard from './completion-velocity/CompletionVelocityCard.es';
import PerformanceByAssigneeCard from './performance-by-assignee-card/PerformanceByAssigneeCard.es';
import PerformanceByStepCard from './performance-by-step-card/PerformanceByStepCard.es';
import PendingItemsCard from './process-items/PendingItemsCard.es';
import WorkloadByAssigneeCard from './workload-by-assignee-card/WorkloadByAssigneeCard.es';
import WorkloadByStepCard from './workload-by-step-card/WorkloadByStepCard.es';

const DashboardTab = ({processId, routeParams}) => {
	const {fetchDateModified} = useContext(AppContext);

	const {dateModified, fetchData} = useDateModified({
		fetchDateModified,
		processId,
	});

	const previousFetchData = usePrevious(fetchData);
	const promises = useMemo(() => {
		if (previousFetchData !== fetchData) {
			return [fetchData()];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<MetricsCalculatedInfo dateModified={dateModified} />

			<ClayLayout.ContainerFluid>
				<ClayLayout.Row>
					<ClayLayout.Col className="p-0" md="9">
						<ClayLayout.ContainerFluid>
							<PendingItemsCard processId={processId} />

							<WorkloadByStepCard
								processId={processId}
								routeParams={routeParams}
							/>
						</ClayLayout.ContainerFluid>
					</ClayLayout.Col>

					<ClayLayout.Col className="p-0" md="3">
						<ClayLayout.ContainerFluid>
							<WorkloadByAssigneeCard routeParams={routeParams} />
						</ClayLayout.ContainerFluid>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</PromisesResolver>
	);
};

function PerformanceTab({processId, routeParams}) {
	const {fetchDateModified} = useContext(AppContext);

	const {dateModified, fetchData} = useDateModified({
		fetchDateModified,
		processId,
	});

	const previousFetchData = usePrevious(fetchData);
	const promises = useMemo(() => {
		if (previousFetchData !== fetchData) {
			return [fetchData()];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData]);

	useTimeRangeFetch();

	return (
		<PromisesResolver promises={promises}>
			<MetricsCalculatedInfo dateModified={dateModified} />

			<ClayLayout.ContainerFluid>
				<CompletedItemsCard routeParams={routeParams} />

				<CompletionVelocityCard routeParams={routeParams} />

				<PerformanceByStepCard routeParams={routeParams} />

				<PerformanceByAssigneeCard routeParams={routeParams} />
			</ClayLayout.ContainerFluid>
		</PromisesResolver>
	);
}

export default function ProcessMetricsContainer({history, processId, query}) {
	const {defaultDelta} = useContext(AppContext);

	useProcessTitle(processId);

	const tabs = {
		dashboard: {
			key: 'dashboard',
			name: Liferay.Language.get('dashboard'),
			params: {
				page: 1,
				pageSize: defaultDelta,
				processId,
				sort: 'overdueInstanceCount:asc',
			},
			path: '/metrics/:processId/dashboard/:pageSize/:page/:sort',
		},
		performance: {
			key: 'performance',
			name: Liferay.Language.get('performance'),
			params: {processId},
			path: '/metrics/:processId/performance',
		},
	};

	if (history.location.pathname === `/metrics/${processId}`) {
		const pathname = getPathname(
			tabs.dashboard.params,
			tabs.dashboard.path
		);

		const search = stringify({
			...parse(query),
			filters: {taskNames: ['allSteps']},
		});

		history.replace({pathname, search});
	}

	return (
		<div className="workflow-process-tabs">
			<HeaderKebab
				kebabItems={[
					{
						label: Liferay.Language.get('sla-settings'),
						link: `/sla/${processId}/list/${defaultDelta}/1`,
					},
				]}
			/>

			<NavbarTabs tabs={Object.values(tabs)} />

			<SLAInfo processId={processId} />

			<Switch>
				<Route
					exact
					path={tabs.dashboard.path}
					render={withParams(DashboardTab)}
				/>

				<Route
					exact
					path={tabs.performance.path}
					render={withParams(PerformanceTab)}
				/>
			</Switch>
		</div>
	);
}
