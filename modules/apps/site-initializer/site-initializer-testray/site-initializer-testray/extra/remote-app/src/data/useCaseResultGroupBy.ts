/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useCallback, useMemo} from 'react';

import {useFetch} from '../hooks/useFetch';
import {APIResponse, FacetAggregation, TestrayBuild} from '../services/rest';
import {chartColors} from '../util/constants';
import {searchUtil} from '../util/search';
import {CaseResultStatuses} from '../util/statuses';

function getStatusesMap(
	facetAggregation: FacetAggregation | undefined
): Map<string, number> {
	const facetValueMap: Map<string, number> = new Map();

	if (!facetAggregation?.facets) {
		return facetValueMap;
	}

	for (const facet of facetAggregation.facets) {
		for (const facetValue of facet.facetValues) {
			facetValueMap.set(facetValue.term, facetValue.numberOfOccurrences);
		}
	}

	return facetValueMap;
}

const useCaseResultGroupBy = (buildId: number = 0) => {
	const {data, loading} = useFetch<
		APIResponse<TestrayBuild> & FacetAggregation
	>(
		`/caseresults?aggregationTerms=dueStatus&filter=${searchUtil.eq(
			'buildId',
			buildId as number
		)}&fields=id`
	);

	const statuses = useMemo(() => getStatusesMap(data), [data]);

	const getStatusValue = useCallback(
		(status: string | number) => statuses.get(String(status)) || 0,
		[statuses]
	);

	const donutColumns = [
		[CaseResultStatuses.PASSED, getStatusValue(CaseResultStatuses.PASSED)],
		[CaseResultStatuses.FAILED, getStatusValue(CaseResultStatuses.FAILED)],
		[
			CaseResultStatuses.BLOCKED,
			getStatusValue(CaseResultStatuses.BLOCKED),
		],
		[
			CaseResultStatuses.TEST_FIX,
			getStatusValue(CaseResultStatuses.TEST_FIX),
		],
		[
			CaseResultStatuses.INCOMPLETE,
			getStatusValue(CaseResultStatuses.UNTESTED) +
				getStatusValue(CaseResultStatuses.IN_PROGRESS),
		],
	];

	return {
		colors: chartColors,
		donut: {
			columns: donutColumns,
			total: donutColumns
				.map(([, totalCase]) => totalCase)
				.reduce(
					(prevValue, currentValue) =>
						Number(prevValue) + Number(currentValue)
				),
		},
		ready: !loading && statuses.size,
		statuses: Object.values(CaseResultStatuses),
	};
};

export default useCaseResultGroupBy;
