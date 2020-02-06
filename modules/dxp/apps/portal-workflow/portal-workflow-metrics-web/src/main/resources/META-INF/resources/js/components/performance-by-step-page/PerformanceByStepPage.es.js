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
import {useFetch} from '../../shared/hooks/useFetch.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {isValidDate} from '../filter/util/timeRangeUtil.es';
import {Body} from './PerformanceByStepPageBody.es';
import {Header} from './PerformanceByStepPageHeader.es';

const PerformanceByStepPage = ({query, routeParams}) => {
	useTimeRangeFetch();

	const {processId} = routeParams;

	useProcessTitle(processId, Liferay.Language.get('performance-by-step'));

	const {search = null} = parse(query);

	const {
		filterState: {timeRange},
		prefixedKeys
	} = useFilter({});

	const {dateEnd, dateStart} =
		timeRange && timeRange.length ? timeRange[0] : {};

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
			key: search,
			...routeParams,
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
		<PromisesResolver promises={promises}>
			<PerformanceByStepPage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				totalCount={data.totalCount}
			/>

			<PerformanceByStepPage.Body data={data} filtered={search} />
		</PromisesResolver>
	);
};

PerformanceByStepPage.Body = Body;
PerformanceByStepPage.Header = Header;

export default PerformanceByStepPage;
