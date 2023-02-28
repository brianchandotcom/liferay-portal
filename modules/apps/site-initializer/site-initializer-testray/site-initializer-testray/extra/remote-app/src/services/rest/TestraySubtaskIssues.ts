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
