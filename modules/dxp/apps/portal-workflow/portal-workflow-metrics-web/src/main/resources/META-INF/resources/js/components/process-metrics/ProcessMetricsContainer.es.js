/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo} from 'react';
import {Navigate, Outlet} from 'react-router';

import HeaderKebab from '../../shared/components/header/HeaderKebab.es';
import MetricsCalculatedInfo from '../../shared/components/last-updated-info/MetricsCalculatedInfo.es';
import NavbarTabs from '../../shared/components/navbar-tabs/NavbarTabs.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse, stringify} from '../../shared/components/router/queryString.es';
import {getPathname} from '../../shared/components/router/routerUtil.es';
import {useDateModified} from '../../shared/hooks/useDateModified.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useRouter} from '../../shared/hooks/useRouter.es';
import {headers, metricsBaseURL} from '../../shared/rest/fetch.es';
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

export function DashboardTab() {
	const {routeParams} = useRouter();
	const {processId} = routeParams;
	const {fetchDateModified} = useContext(AppContext);

	const {dateModified, fetchData} = useDateModified({
		processId,
	});

	const previousFetchData = usePrevious(fetchData);

	const promises = useMemo(() => {
		if (previousFetchData !== fetchData && fetchDateModified) {
			return [fetchData()];
		}

		return [];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchDateModified, routeParams]);

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

export function PerformanceTab() {
	const {location, navigate, routeParams} = useRouter();
	const {processId} = routeParams;
	const {fetchDateModified} = useContext(AppContext);

	const {dateModified, fetchData} = useDateModified({
		processId,
	});

	const previousFetchData = usePrevious(fetchData);

	const promises = useMemo(() => {
		if (previousFetchData !== fetchData && fetchDateModified) {
			return [fetchData()];
		}

		return [];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchDateModified, routeParams]);

	useTimeRangeFetch();

	const addDefaultFiltersToQuery = (defaultTimeRange, query) => {
		const prefixes = ['completion', 'step', 'assignee', 'completed'];

		for (const prefix of prefixes) {
			query.filters = {
				...query.filters,
				[prefix + 'DateEnd']: defaultTimeRange['dateEnd'],
				[prefix + 'DateStart']: defaultTimeRange['dateStart'],
				[prefix + 'TimeRange']: [defaultTimeRange['id']],
			};
		}

		query.filters['completionVelocityUnit[0]'] = 'Days';
		query.filters['stepProcessVersion[0]'] = 'allVersions';
		query.filters['assigneeTaskNames[0]'] = 'allSteps';

		return stringify(query);
	};

	const fetchTimeRanges = async () => {
		let fetchURL = `${metricsBaseURL}${'/time-ranges'}`;

		fetchURL = new URL(fetchURL, Liferay.ThemeDisplay.getPortalURL());

		const response = await fetch(fetchURL, {
			headers,
			method: 'GET',
		});

		return await response.json();
	};

	useEffect(() => {
		const replaceHistoryWithDefaultFilters = async () => {
			const fetchedTimeRanges = await fetchTimeRanges();

			const query = parse(location.search);

			if (
				fetchedTimeRanges?.items?.length &&
				!query?.filters?.assigneeDateEnd
			) {
				const {items: timeRanges} = fetchedTimeRanges;

				const defaultTimeRange = timeRanges.find(
					(timeRange) => timeRange.defaultTimeRange
				);

				const queryWithDefaultFilters = addDefaultFiltersToQuery(
					defaultTimeRange,
					query
				);

				navigate({search: queryWithDefaultFilters}, {replace: true});
			}
		};

		replaceHistoryWithDefaultFilters();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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

export default function ProcessMetricsContainer() {
	const {defaultDelta} = useContext(AppContext);
	const {location, routeParams} = useRouter();
	const {processId} = routeParams;

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

	if (location.pathname === `/metrics/${processId}`) {
		const pathname = getPathname(
			tabs.dashboard.params,
			tabs.dashboard.path
		);

		const search = stringify({
			...parse(location.search),
			filters: {taskNames: ['allSteps']},
		});

		return <Navigate replace to={{pathname, search}} />
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

			<Outlet />
		</div>
	);
}
