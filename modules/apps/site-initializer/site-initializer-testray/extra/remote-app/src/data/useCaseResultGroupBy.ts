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
import {useMemo} from 'react';

import {
	FacetAggregation,
	FacetAggregationQuery,
	getCaseResultsAggregation,
} from '../graphql/queries';

enum Statuses {
	PASSED = 'PASSED',
	FAILED = 'FAILED',
	BLOCKED = 'BLOCKED',
	TEST_FIX = 'TEST FIX',
	INCOMPLETE = 'INCOMPLETE',
}

function getStatusesMap(
	facetAggregation: FacetAggregation | undefined
): Map<string, number> {
	const facetValue: Map<string, number> = new Map();

	if (!facetAggregation?.facets) {
		return facetValue;
	}

	for (const facet of facetAggregation.facets.facetValues) {
		facetValue.set(facet.term, facet.numberOfOccurrences);
	}

	return facetValue;
}

const useCaseResultGroupBy = () => {
	const buildId = 42702;

	const {data} = useQuery<FacetAggregationQuery<'caseResultAggregation'>>(
		getCaseResultsAggregation,
		{
			variables: {
				aggregation: 'dueStatus',
				filter: `buildId eq ${buildId}`,
			},
		}
	);

	const statuses = useMemo(
		() => getStatusesMap(data?.caseResultAggregation),
		[data?.caseResultAggregation]
	);

	const getStatusValue = (status: string) => statuses.get(status) || 0;

	const donutColumns = [
		[Statuses.PASSED, getStatusValue('0')],
		[Statuses.FAILED, getStatusValue('1')],
		[Statuses.BLOCKED, getStatusValue('2')],
		[Statuses.TEST_FIX, getStatusValue('3')],
		[Statuses.INCOMPLETE, getStatusValue('4')],
	];

	return {
		donut: {
			columns: donutColumns,
			total: donutColumns
				.map(([, totalCase]) => totalCase)
				.reduce(
					(prevValue, currentValue) =>
						Number(prevValue) + Number(currentValue)
				),
		},
		statuses: Object.values(Statuses),
	};
};

export default useCaseResultGroupBy;
