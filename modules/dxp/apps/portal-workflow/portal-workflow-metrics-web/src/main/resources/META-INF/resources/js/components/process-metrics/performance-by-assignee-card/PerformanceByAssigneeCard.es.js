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
import ProcessStepFilter from '../../filter/ProcessStepFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import {isValidDate} from '../../filter/util/timeRangeUtil.es';
import {Body, Footer} from './PerformanceByAssigneeCardBody.es';

const Header = ({prefixKey, processId}) => {
	return (
		<Panel.HeaderWithOptions
			description={Liferay.Language.get(
				'performance-by-assignee-description'
			)}
			elementClasses="dashboard-panel-header"
			title={Liferay.Language.get('performance-by-assignee')}
		>
			<div className="autofit-col m-0 management-bar management-bar-light navbar">
				<ul className="navbar-nav">
					<ProcessStepFilter
						options={{
							hideControl: true,
							multiple: false,
							position: 'right',
							withAllSteps: true,
							withSelectionTitle: true
						}}
						prefixKey={prefixKey}
						processId={processId}
					/>

					<TimeRangeFilter
						className={'pl-3'}
						options={{position: 'right'}}
						prefixKey={prefixKey}
					/>
				</ul>
			</div>
		</Panel.HeaderWithOptions>
	);
};

const PerformanceByAssigneeCard = ({routeParams}) => {
	const {processId} = routeParams;

	const filterKeys = ['processStep', 'timeRange'];
	const prefixKey = 'assignee';
	const prefixKeys = [prefixKey];
	const {filterState = {}, filterValues} = useFilter({
		filterKeys,
		prefixKeys
	});

	const params = {
		completed: true,
		page: 1,
		pageSize: 10,
		sort: 'durationTaskAvg:desc'
	};

	const processStep = filterValues.assigneeTaskKeys || [];
	if (processStep.length && processStep[0] !== 'allSteps') {
		params.taskKeys = processStep[0];
	}

	const timeRange = filterState.assigneeTimeRange || [];
	const timeRangeValues = timeRange.length ? timeRange[0] : {};
	const {dateEnd, dateStart} = timeRangeValues;

	if (isValidDate(dateEnd) && isValidDate(dateStart)) {
		params.dateEnd = dateEnd.toISOString();
		params.dateStart = dateStart.toISOString();
	}

	const {data, fetchData} = useFetch({
		params,
		url: `/processes/${processId}/assignee-users`
	});

	const promises = useMemo(() => {
		if (params.dateEnd && params.dateStart) {
			return [fetchData()];
		}

		return [new Promise(() => {})];
	}, [fetchData, params.dateEnd, params.dateStart]);

	return (
		<Panel elementClasses="dashboard-card">
			<PromisesResolver promises={promises}>
				<PerformanceByAssigneeCard.Header
					prefixKey={prefixKey}
					{...routeParams}
				/>

				<PerformanceByAssigneeCard.Body
					data={data}
					filtered={params.taskKeys}
				/>

				<PerformanceByAssigneeCard.Footer
					processStep={params.taskKeys}
					timeRange={timeRangeValues}
					totalCount={data.totalCount}
					{...routeParams}
				/>
			</PromisesResolver>
		</Panel>
	);
};

PerformanceByAssigneeCard.Body = Body;
PerformanceByAssigneeCard.Footer = Footer;
PerformanceByAssigneeCard.Header = Header;

export default PerformanceByAssigneeCard;
