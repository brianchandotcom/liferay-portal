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

import {useMemo, useState} from 'react';
import {APIParametersOptions} from '~/core/Rest';
import SearchBuilder from '~/core/SearchBuilder';
import i18n from '~/i18n';
import {
	APIResponse,
	testrayComponentImpl,
	testrayRunImpl,
} from '~/services/rest';
import {chartColors} from '~/util/constants';
import {getRandom} from '~/util/mock';

import {useFetch} from './useFetch';

type TestrayChartResources = {
	components: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
	runs: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
	teams: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
};

const statususes = {
	BLOCKED: 'caseResultBlocked',
	FAILED: 'caseResultFailed',
	INCOMPLETE: 'caseResultIncomplete',
	PASSED: 'caseResultPassed',
	TEST_FIX: 'caseResultTestFix',
};

const fields =
	'caseResultBlocked,caseResultFailed,caseResultIncomplete,caseResultPassed,caseResultTestFix';

const chartSelectData = [
	{label: i18n.translate('runs'), value: 'runs'},
	{label: i18n.translate('teams'), value: 'teams'},
	{label: i18n.translate('components'), value: 'components'},
];

const useCaseResultsChart = ({buildId}: {buildId: number}) => {
	const [entity, setEntity] = useState(chartSelectData[0].value);

	const resources: TestrayChartResources = {
		components: {
			fetchParameters: {
				fields,
				filter: SearchBuilder.eq(
					'componentToCaseResult/r_buildToCaseResult_c_buildId',
					buildId
				),
			},
			url: testrayComponentImpl.resource,
		},
		runs: {
			fetchParameters: {
				fields,
				filter: SearchBuilder.eq('r_buildToRuns_c_buildId', buildId),
			},
			url: testrayRunImpl.resource,
		},
		teams: {
			fetchParameters: {
				fields,
				filter: SearchBuilder.eq(
					'componentToCaseResult/r_buildToCaseResult_c_buildId',
					buildId
				),
			},
			url: testrayComponentImpl.resource,
		},
	};

	const {data} = useFetch<APIResponse<any>>(
		resources[entity as keyof TestrayChartResources].url,
		{
			params: {
				...resources[entity as keyof TestrayChartResources]
					.fetchParameters,
			},
		}
	);

	const responseItems = useMemo(() => data?.items || [], [data?.items]);

	const chartData = Object.entries(statususes).map(([key, value]) => [
		key,
		...responseItems.map(
			(caseResult) => caseResult[value] ?? getRandom(1000)
		),
	]);

	return {
		chart: {
			colors: chartColors,
			columns: chartData,
			statuses: Object.keys(statususes),
		},
		chartSelectData,
		setEntity,
	};
};

export {useCaseResultsChart};
