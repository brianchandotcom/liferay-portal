/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
