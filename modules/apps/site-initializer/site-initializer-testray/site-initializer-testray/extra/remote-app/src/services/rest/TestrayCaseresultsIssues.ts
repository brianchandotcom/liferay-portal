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

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayCaseResultIssue} from './types';

type CaseResultsIssues = typeof yupSchema.caseResultIssues.__outputType;

class TestrayCaseResultsIssuesImpl extends Rest<
	CaseResultsIssues,
	TestrayCaseResultIssue
> {
	constructor() {
		super({
			adapter: ({
				caseResultId: r_caseResultToCaseResultsIssues_c_caseResultId,
				issueId: r_issueToCaseResultsIssues_c_issueId,
				name,
			}) => ({
				name,
				r_caseResultToCaseResultsIssues_c_caseResultId,
				r_issueToCaseResultsIssues_c_issueId,
			}),
			nestedFields: 'caseResults,issue',
			transformData: (caseResultsIssue) => ({
				...caseResultsIssue,
				caseResult:
					caseResultsIssue?.r_caseResultToCaseResultsIssues_c_caseResult,
				issue: caseResultsIssue?.r_issueToCaseResultsIssues_c_issue,
			}),
			uri: 'caseresultsissueses',
		});
	}
}

export const testrayCaseResultsIssuesImpl = new TestrayCaseResultsIssuesImpl();
