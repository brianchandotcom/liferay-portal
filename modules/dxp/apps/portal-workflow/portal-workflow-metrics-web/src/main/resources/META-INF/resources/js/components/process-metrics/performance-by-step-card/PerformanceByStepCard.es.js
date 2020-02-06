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
import {isValidDate} from '../../filter/util/timeRangeUtil.es';
import {Body, Footer} from './PerformanceByStepCardBody.es';

const Header = ({prefixKey, totalCount}) => (
	<Panel.HeaderWithOptions
		description={Liferay.Language.get('performance-by-step-description')}
		elementClasses="dashboard-panel-header"
		title={Liferay.Language.get('performance-by-step')}
	>
		<div className="autofit-col m-0 management-bar management-bar-light navbar">
			<ul className="navbar-nav">
				<TimeRangeFilter
					disabled={!totalCount}
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

	const {filterState = {}} = useFilter({filterKeys, prefixKeys});

	const timeRange = filterState.stepTimeRange || [];
	const timeRangeValues = timeRange && timeRange.length ? timeRange[0] : {};
	const {dateEnd, dateStart} = timeRangeValues;

	let timeRangeParams = {};

	if (isValidDate(dateEnd) && isValidDate(dateStart)) {
		timeRangeParams = {
			dateEnd: dateEnd.toISOString(),
			dateStart: dateStart.toISOString()
		};
	}

	const {data, fetchData} = useFetch({
		params: {
			completed: true,
			page: 1,
			pageSize: 10,
			sort: 'durationAvg:desc',
			...timeRangeParams
		},
		url: `/processes/${processId}/tasks`
	});

	const promises = useMemo(() => {
		if (timeRangeParams.dateEnd && timeRangeParams.dateStart) {
			return [fetchData()];
		}

		return [new Promise(() => {})];
	}, [fetchData, timeRangeParams.dateEnd, timeRangeParams.dateStart]);

	return (
		<Panel elementClasses="dashboard-card">
			<PromisesResolver promises={promises}>
				<PerformanceByStepCard.Header
					prefixKey={prefixKey}
					totalCount={data.totalCount}
				/>

				<PerformanceByStepCard.Body data={data} />

				{data.totalCount > 0 && (
					<PerformanceByStepCard.Footer
						processId={processId}
						timeRange={timeRangeValues}
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
