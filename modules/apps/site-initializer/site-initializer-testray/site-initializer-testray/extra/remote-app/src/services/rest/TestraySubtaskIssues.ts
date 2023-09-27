/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestraySubTaskIssue} from './types';

type SubtaskIssues = typeof yupSchema.subtaskIssues.__outputType;

class TestraySubtaskIssuesImpl extends Rest<
	SubtaskIssues,
	TestraySubTaskIssue
> {
	constructor() {
		super({
			adapter: ({
				issueId: r_issueToSubtasksIssues_c_issueId,
				name,
				subTaskId: r_subtaskToSubtasksIssues_c_subtaskId,
			}) => ({
				name,
				r_issueToSubtasksIssues_c_issueId,
				r_subtaskToSubtasksIssues_c_subtaskId,
			}),
			nestedFields: 'subtask,issue',
			transformData: (subtaskIssue) => ({
				...subtaskIssue,
				issue: subtaskIssue?.r_issueToSubtasksIssues_c_issue
					? {
							...subtaskIssue.r_issueToSubtasksIssues_c_issue,
					  }
					: undefined,
				subTask: subtaskIssue?.r_subtaskToSubtasksIssues_c_subtask
					? {
							...subtaskIssue.r_subtaskToSubtasksIssues_c_subtask,
					  }
					: undefined,
			}),
			uri: 'subtaskissues',
		});
	}
}

export const testraySubtaskIssuesImpl = new TestraySubtaskIssuesImpl();
