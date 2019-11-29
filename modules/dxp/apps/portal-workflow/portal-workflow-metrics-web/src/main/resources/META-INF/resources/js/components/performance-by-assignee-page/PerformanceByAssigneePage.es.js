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

import React, {useContext} from 'react';

import {
	filterKeys,
	filterTitles
} from '../../shared/components/filter/util/filterConstants.es';
import {
	getFilterResults,
	getSelectedItems,
	getFiltersParam,
	getFilterValues
} from '../../shared/components/filter/util/filterUtil.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import Request from '../../shared/components/request/Request.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFilterItemKeys} from '../../shared/hooks/useFilterItemKeys.es';
import {useFiltersReducer} from '../../shared/hooks/useFiltersReducer.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useResource} from '../../shared/hooks/useResource.es';
import {
	TimeRangeContext,
	TimeRangeProvider
} from '../process-metrics/filter/store/TimeRangeStore.es';
import {Body} from './PerformanceByAssigneePageBody.es';
import {Header} from './PerformanceByAssigneePageHeader.es';

const Container = ({filtersParam, query, routeParams, timeRangeKeys}) => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {processId} = routeParams;

	useProcessTitle(processId, Liferay.Language.get('performance-by-assignee'));

	const {processStep, roles} = filterKeys;

	const [filterValues, dispatch] = useFiltersReducer({processStep, roles});

	const {roleIds, taskKeys} = useFilterItemKeys(
		{processStep, roles},
		filterValues
	);
	const filterResults = getFilterResults(
		{processStep, roles},
		{
			processStep: filterTitles.processStep,
			roles: filterTitles.roles
		},
		filterValues
	);

	const selectedFilters = getSelectedItems(filterResults);
	const {search = null} = parse(query);
	const timeRange = getSelectedTimeRange(
		timeRangeKeys,
		filtersParam.dateEnd,
		filtersParam.dateStart
	);

	const isValidDate = date => date && !isNaN(date);
	const timeRangeParams = {};

	if (
		timeRange &&
		isValidDate(timeRange.dateEnd) &&
		isValidDate(timeRange.dateStart)
	) {
		const {dateEnd, dateStart} = timeRange;

		timeRangeParams.dateEnd = dateEnd.toISOString();
		timeRangeParams.dateStart = dateStart.toISOString();
	}

	const {data, promises} = useResource(
		`/processes/${processId}/assignee-users?completed=true`,
		{
			completed: true,
			keywords: search,
			roleIds,
			taskKeys,
			...routeParams,
			...timeRangeParams
		}
	);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByAssigneePage.Header
				dispatch={dispatch}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data.totalCount}
			/>

			<PerformanceByAssigneePage.Body
				data={data}
				filtered={search || selectedFilters.length > 0}
			/>
		</PromisesResolver>
	);
};

const PerformanceByAssigneePage = props => {
	const filtersParam = getFiltersParam(props.query);
	const timeRangeKeys = getFilterValues(filterKeys.timeRange, filtersParam);

	return (
		<Request>
			<TimeRangeProvider timeRangeKeys={timeRangeKeys}>
				<PerformanceByAssigneePage.Container
					filtersParam={filtersParam}
					timeRangeKeys={timeRangeKeys}
					{...props}
				/>
			</TimeRangeProvider>
		</Request>
	);
};

PerformanceByAssigneePage.Body = Body;
PerformanceByAssigneePage.Container = Container;
PerformanceByAssigneePage.Header = Header;

export default PerformanceByAssigneePage;
