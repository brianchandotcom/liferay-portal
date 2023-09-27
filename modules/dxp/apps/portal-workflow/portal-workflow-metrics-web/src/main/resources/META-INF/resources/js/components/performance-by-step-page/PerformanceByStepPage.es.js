/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				processVersion?.indexOf('allVersions') === -1
					? processVersion
					: undefined,
			...paginationParams,
			...getTimeRangeParams(dateStart, dateEnd),
		},
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routeParams]
	);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByStepPage.Header
				filterKeys={prefixedKeys}
				hideFilters={hideFilters}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data?.totalCount}
			/>

			<PerformanceByStepPage.Body
				{...data}
				filtered={search || !!selectedFilters.length}
			/>
		</PromisesResolver>
	);
}

PerformanceByStepPage.Body = Body;
PerformanceByStepPage.Header = Header;

export default PerformanceByStepPage;
