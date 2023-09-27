/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {usePost} from '../../shared/hooks/usePost.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {getTimeRangeParams} from '../filter/util/timeRangeUtil.es';
import Body from './PerformanceByAssigneePageBody.es';
import Header from './PerformanceByAssigneePageHeader.es';

function PerformanceByAssigneePage({query, routeParams}) {
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

	const promises = useMemo(() => {
		if (timeRange.dateEnd && timeRange.dateStart) {
			return [postData()];
		}

		return [new Promise((_, reject) => reject())];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [routeParams, timeRange.dateEnd, timeRange.dateStart]);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByAssigneePage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data?.totalCount}
			/>

			<PerformanceByAssigneePage.Body
				{...data}
				filtered={search || !!selectedFilters.length}
			/>
		</PromisesResolver>
	);
}

PerformanceByAssigneePage.Body = Body;
PerformanceByAssigneePage.Header = Header;

export default PerformanceByAssigneePage;
