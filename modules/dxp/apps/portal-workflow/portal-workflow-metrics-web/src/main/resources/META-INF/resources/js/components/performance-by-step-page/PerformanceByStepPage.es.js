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
import {getTimeRangeParams} from '../filter/util/timeRangeUtil.es';
import Body from './PerformanceByStepPageBody.es';
import Header from './PerformanceByStepPageHeader.es';

function PerformanceByStepPage({query, routeParams}) {
	useTimeRangeFetch();

	const {processId, ...paginationParams} = routeParams;
	const {search = null} = parse(query);
	const filterKeys = ['processVersion'];
	const hideFilters = ['processVersion'];

	useProcessTitle(processId, Liferay.Language.get('performance-by-step'));

	const {
		filterValues: {dateEnd, dateStart, processVersion},
		prefixedKeys,
		selectedFilters,
	} = useFilter({filterKeys});

	const {data, fetchData} = useFetch({
		params: {
			completed: true,
			key: search,
			processVersion:
				processVersion?.indexOf('allVersions') == -1
					? processVersion
					: undefined,
			...paginationParams,
			...getTimeRangeParams(dateStart, dateEnd),
		},
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByStepPage.Header
				filterKeys={prefixedKeys}
				hideFilters={hideFilters}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data.totalCount}
			/>

			<PerformanceByStepPage.Body
				{...data}
				filtered={search || selectedFilters.length > 0}
			/>
		</PromisesResolver>
	);
}

PerformanceByStepPage.Body = Body;
PerformanceByStepPage.Header = Header;

export default PerformanceByStepPage;
