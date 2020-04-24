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
import {useFilter} from '../../../shared/hooks/useFilter.es';
import {usePost} from '../../../shared/hooks/usePost.es';
import ProcessStepFilter from '../../filter/ProcessStepFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import {getTimeRangeParams} from '../../filter/util/timeRangeUtil.es';
import {Body, Footer} from './PerformanceByAssigneeCardBody.es';

const Header = ({disableFilters, prefixKey, processId}) => {
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
						disabled={disableFilters}
						options={{
							hideControl: true,
							multiple: false,
							position: 'right',
							withAllSteps: true,
							withSelectionTitle: true,
						}}
						prefixKey={prefixKey}
						processId={processId}
					/>

					<TimeRangeFilter
						className={'pl-3'}
						disabled={disableFilters}
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

	const {
		filterValues: {
			assigneeDateEnd,
			assigneeDateStart,
			assigneeTaskNames: [taskName] = ['allSteps'],
			assigneeTimeRange: [key] = [],
		},
		filtersError,
	} = useFilter({
		filterKeys,
		prefixKeys,
	});

	const taskNames = taskName !== 'allSteps' ? [taskName] : undefined;
	const timeRange = useMemo(
		() => getTimeRangeParams(assigneeDateStart, assigneeDateEnd),
		[assigneeDateEnd, assigneeDateStart]
	);

	const {data, postData} = usePost({
		body: {
			completed: true,
			taskNames,
			...timeRange,
		},
		params: {
			page: 1,
			pageSize: 10,
			sort: 'durationTaskAvg:desc',
		},
		url: `/processes/${processId}/assignees/metrics`,
	});

	const promises = useMemo(() => [postData()], [postData]);

	return (
		<Panel elementClasses="dashboard-card">
			<PromisesResolver promises={promises}>
				<PerformanceByAssigneeCard.Header
					disableFilters={filtersError}
					prefixKey={prefixKey}
					{...routeParams}
				/>

				<PerformanceByAssigneeCard.Body
					{...data}
					filtered={!!taskNames}
				/>

				<PerformanceByAssigneeCard.Footer
					processStep={taskName}
					timeRange={{key, ...timeRange}}
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
