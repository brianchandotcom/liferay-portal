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

import {useMemo} from 'react';

import SearchBuilder from '../../core/SearchBuilder';
import {
	APIResponse,
	TestrayCaseResultIssue,
	TestrayIssue,
} from '../../services/rest';
import {testrayCaseResultsIssuesImpl} from '../../services/rest/TestrayCaseresultsIssues';
import {useFetch} from '../useFetch';

type useIssuesFoundProps = {
	buildId?: number;
	caseId?: number;
};

const useIssuesFound = ({buildId, caseId}: useIssuesFoundProps) => {
	const id = (buildId ?? caseId) as number;

	const {data} = useFetch<APIResponse<TestrayCaseResultIssue>>(
		testrayCaseResultsIssuesImpl.resource,
		{
			params: {
				fields: 'r_issueToCaseResultsIssues_c_issue.name',
				filter: SearchBuilder.eq(
					buildId
						? 'caseResultToCaseResultsIssues/r_buildToCaseResult_c_buildId'
						: 'caseResultToCaseResultsIssues/r_caseToCaseResult_c_caseId',
					id
				),
			},
			swrConfig: {
				shouldFetch: id,
			},
			transformData: (response) =>
				testrayCaseResultsIssuesImpl.transformDataFromList(response),
		}
	);

	const issues = useMemo(
		() => (data?.items ?? []).map(({issue}) => issue as TestrayIssue),
		[data?.items]
	);

	return issues;
};

export default useIssuesFound;
