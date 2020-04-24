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

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {usePost} from '../../shared/hooks/usePost.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {getTimeRangeParams} from '../filter/util/timeRangeUtil.es';
import {Body} from './PerformanceByAssigneePageBody.es';
import {Header} from './PerformanceByAssigneePageHeader.es';

const PerformanceByAssigneePage = ({query, routeParams}) => {
	useTimeRangeFetch();

	const {processId, ...paginationParams} = routeParams;
	const {search = null} = parse(query);
	const filterKeys = ['processStep', 'roles'];

	useProcessTitle(processId, Liferay.Language.get('performance-by-assignee'));

	const {
		filterValues: {dateEnd, dateStart, roleIds, taskNames},
		prefixedKeys,
		selectedFilters,
	} = useFilter({filterKeys});

	const timeRange = useMemo(() => getTimeRangeParams(dateStart, dateEnd), [
		dateEnd,
		dateStart,
	]);

	const {data, postData} = usePost({
		body: {
			completed: true,
			keywords: search,
			roleIds,
			taskNames,
			...timeRange,
		},
		params: paginationParams,
		url: `/processes/${processId}/assignees/metrics`,
	});

	const promises = useMemo(() => [postData()], [postData]);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByAssigneePage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data.totalCount}
			/>

			<PerformanceByAssigneePage.Body
				{...data}
				filtered={search || selectedFilters.length > 0}
			/>
		</PromisesResolver>
	);
};

PerformanceByAssigneePage.Body = Body;
PerformanceByAssigneePage.Header = Header;

export default PerformanceByAssigneePage;
