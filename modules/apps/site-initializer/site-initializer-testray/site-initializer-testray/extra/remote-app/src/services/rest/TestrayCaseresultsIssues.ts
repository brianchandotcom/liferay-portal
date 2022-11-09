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

import yupSchema from '../../schema/yup';
import Rest from './Rest';
import {TestrayCaseResultIssue} from './types';

type CaseResultsIssues = typeof yupSchema.caseResultIssues.__outputType;

class TestrayCaseResultsIssuesImpl extends Rest<
	CaseResultsIssues,
	TestrayCaseResultIssue
> {
	constructor() {
		super({
			adapter: ({
				caseResult: r_caseResultToCaseResultsIssues_c_caseResultId,
				issue: r_issueToCaseResultsIssues_c_issueId,
				name,
			}) => ({
				name,
				r_caseResultToCaseResultsIssues_c_caseResultId,
				r_issueToCaseResultsIssues_c_issueId,
			}),
			nestedFields: 'caseResults,issue',
			transformData: (caseResultsIssue) => {
				return {
					...caseResultsIssue,
					caseResult: caseResultsIssue?.r_caseResultToCaseResultsIssues_c_caseResultId
						? {
								...caseResultsIssue.r_caseResultToCaseResultsIssues_c_caseResultId,
						  }
						: undefined,
					issue: caseResultsIssue?.r_issueToCaseResultsIssues_c_issueId
						? {
								...caseResultsIssue.r_issueToCaseResultsIssues_c_issueId,
						  }
						: undefined,
				};
			},
			uri: 'caseresultsissueses',
		});
	}
}

export const testrayCaseResultsIssuesImpl = new TestrayCaseResultsIssuesImpl();
