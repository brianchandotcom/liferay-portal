/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo} from 'react';

import SearchBuilder from '../../core/SearchBuilder';
import {APIResponse, FacetAggregation, TestrayBuild} from '../../services/rest';
import {chartColors} from '../../util/constants';
import {CaseResultStatuses} from '../../util/statuses';
import {useFetch} from '../useFetch';

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

const getAggregationValue = (value: number | string) =>
	value ? Number(value) : 0;

const useTotalTestCases = (testrayBuild: TestrayBuild) => {
	const donutColumns = useMemo(
		() => [
			[
				CaseResultStatuses.PASSED,
				getAggregationValue(testrayBuild.caseResultPassed),
			],
			[
				CaseResultStatuses.FAILED,
				getAggregationValue(testrayBuild.caseResultFailed),
			],
			[
				CaseResultStatuses.BLOCKED,
				getAggregationValue(testrayBuild.caseResultBlocked),
			],
			[
				CaseResultStatuses.TEST_FIX,
				getAggregationValue(testrayBuild.caseResultTestFix),
			],
			[
				CaseResultStatuses.INCOMPLETE,
				getAggregationValue(testrayBuild.caseResultUntested) +
					getAggregationValue(testrayBuild.caseResultInProgress),
			],
		],
		[
			testrayBuild.caseResultBlocked,
			testrayBuild.caseResultFailed,
			testrayBuild.caseResultInProgress,
			testrayBuild.caseResultPassed,
			testrayBuild.caseResultTestFix,
			testrayBuild.caseResultUntested,
		]
	);

	return useMemo(
		() => ({
			colors: chartColors,
			donut: {
				columns: donutColumns,
				total: donutColumns
					.map(([, totalCase]) => Number(totalCase))
					.reduce(
						(prevValue, currentValue) => prevValue + currentValue
					),
			},
			ready: !!testrayBuild,
			statuses: Object.values(CaseResultStatuses),
		}),
		[donutColumns, testrayBuild]
	);
};

const useCaseResultGroupBy = (buildId: number = 0) => {
	const {data, loading} = useFetch<
		APIResponse<TestrayBuild> & FacetAggregation
	>('/caseresults', {
		params: {
			aggregationTerms: 'dueStatus',
			fields: 'id',
			filter: SearchBuilder.eq('buildId', buildId),
		},
	});

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
		ready: !loading && statuses.size > 0,
		statuses: Object.values(CaseResultStatuses),
	};
};

export {useTotalTestCases};

export default useCaseResultGroupBy;
