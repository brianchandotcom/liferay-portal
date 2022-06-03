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

import {useQuery} from '@apollo/client';
import {useCallback, useMemo} from 'react';

import {
	FacetAggregation,
	FacetAggregationQuery,
	getCaseResultsAggregation,
} from '../graphql/queries';
import {Statuses, TEST_STATUS, chartColors} from '../util/constants';
import {searchUtil} from '../util/search';

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

const useCaseResultGroupBy = (buildId: number) => {
	const {data, loading} = useQuery<
		FacetAggregationQuery<'caseResultAggregation'>
	>(getCaseResultsAggregation, {
		variables: {
			aggregation: 'dueStatus',
			filter: searchUtil.eq('buildId', buildId),
		},
	});

	const statuses = useMemo(
		() => getStatusesMap(data?.caseResultAggregation),
		[data?.caseResultAggregation]
	);

	const getStatusValue = useCallback(
		(status: string | number) => statuses.get(String(status)) || 0,
		[statuses]
	);

	const status = {
		blocked: getStatusValue(TEST_STATUS.Blocked),
		failed: getStatusValue(TEST_STATUS.Failed),
		incomplete:
			getStatusValue(TEST_STATUS.Untested) +
			getStatusValue(TEST_STATUS['In Progress']),
		passed: getStatusValue(TEST_STATUS.Passed),
		test_fix: getStatusValue(TEST_STATUS['Test Fix']),
	};

	const donutColumns = [
		[Statuses.PASSED, status.passed],
		[Statuses.FAILED, status.failed],
		[Statuses.BLOCKED, status.blocked],
		[Statuses.TEST_FIX, status.test_fix],
		[Statuses.INCOMPLETE, status.incomplete],
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
		status,
		statuses: Object.values(Statuses),
	};
};

export default useCaseResultGroupBy;
