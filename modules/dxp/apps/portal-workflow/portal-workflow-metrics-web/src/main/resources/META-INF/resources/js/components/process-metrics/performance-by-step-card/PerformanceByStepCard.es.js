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

import React, {useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import {getTimeRangeParams} from '../../filter/util/timeRangeUtil.es';
import {Body, Footer} from './PerformanceByStepCardBody.es';

const Header = ({disableFilters, prefixKey, totalCount}) => (
	<Panel.HeaderWithOptions
		description={Liferay.Language.get('performance-by-step-description')}
		elementClasses="dashboard-panel-header"
		title={Liferay.Language.get('performance-by-step')}
	>
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter
					disabled={!totalCount || disableFilters}
					options={{position: 'right'}}
					prefixKey={prefixKey}
				/>
			</ul>
		</div>
	</Panel.HeaderWithOptions>
);

const PerformanceByStepCard = ({routeParams}) => {
	const {processId} = routeParams;
	const filterKeys = ['timeRange'];
	const prefixKey = 'step';
	const prefixKeys = [prefixKey];

	const {
		filterValues: {stepDateEnd, stepDateStart, stepTimeRange: [key] = []},
		filtersError,
	} = useFilter({
		filterKeys,
		prefixKeys,
	});

	const timeRange = useMemo(
		() => getTimeRangeParams(stepDateStart, stepDateEnd),
		[stepDateEnd, stepDateStart]
	);

	const {data, fetchData} = useFetch({
		params: {
			completed: true,
			page: 1,
			pageSize: 10,
			sort: 'durationAvg:desc',
			...timeRange,
		},
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<Panel elementClasses="dashboard-card">
			<PromisesResolver promises={promises}>
				<PerformanceByStepCard.Header
					disableFilters={filtersError}
					prefixKey={prefixKey}
					totalCount={data.totalCount}
				/>

				<PerformanceByStepCard.Body {...data} />

				{data.totalCount > 0 && (
					<PerformanceByStepCard.Footer
						processId={processId}
						timeRange={{key, ...timeRange}}
						totalCount={data.totalCount}
					/>
				)}
			</PromisesResolver>
		</Panel>
	);
};

PerformanceByStepCard.Body = Body;
PerformanceByStepCard.Footer = Footer;
PerformanceByStepCard.Header = Header;

export default PerformanceByStepCard;
