/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestraySubTaskCaseResult} from './types';

type SubtaskCaseResultForm = typeof yupSchema.subtaskToCaseResult.__outputType;

class TestraySubtaskCaseResultImpl extends Rest<
	SubtaskCaseResultForm,
	TestraySubTaskCaseResult
> {
	constructor() {
		super({
			adapter: ({
				caseResultId: r_caseResultToSubtasksCasesResults_c_caseResultId,
				issues,
				name,
				subtaskId: r_subtaskToSubtasksCasesResults_c_subtaskId,
			}) => ({
				issues,
				name,
				r_caseResultToSubtasksCasesResults_c_caseResultId,
				r_subtaskToSubtasksCasesResults_c_subtaskId,
			}),
			nestedFields:
				'caseResult.case,caseResult.component.team,caseResult.build.routine,caseResult.build.project,caseResult.run,subtask.user',
			transformData: (subtaskCaseResult) => ({
				caseResult: subtaskCaseResult?.r_caseResultToSubtasksCasesResults_c_caseResult
					? {
							...subtaskCaseResult?.r_caseResultToSubtasksCasesResults_c_caseResult,
							build: subtaskCaseResult
								.r_caseResultToSubtasksCasesResults_c_caseResult
								?.r_buildToCaseResult_c_build
								? {
										...subtaskCaseResult
											.r_caseResultToSubtasksCasesResults_c_caseResult
											?.r_buildToCaseResult_c_build,

										project:
											subtaskCaseResult
												.r_caseResultToSubtasksCasesResults_c_caseResult
												?.r_buildToCaseResult_c_build
												?.r_projectToBuilds_c_project,
										routine:
											subtaskCaseResult
												.r_caseResultToSubtasksCasesResults_c_caseResult
												?.r_buildToCaseResult_c_build
												?.r_routineToBuilds_c_routine,
								  }
								: undefined,
							case:
								subtaskCaseResult
									?.r_caseResultToSubtasksCasesResults_c_caseResult
									.r_caseToCaseResult_c_case,
							component: subtaskCaseResult
								.r_caseResultToSubtasksCasesResults_c_caseResult
								?.r_componentToCaseResult_c_component
								? {
										...subtaskCaseResult
											.r_caseResultToSubtasksCasesResults_c_caseResult
											?.r_componentToCaseResult_c_component,
										team:
											subtaskCaseResult
												.r_caseResultToSubtasksCasesResults_c_caseResult
												?.r_componentToCaseResult_c_component
												.r_teamToComponents_c_team,
								  }
								: undefined,
							run:
								subtaskCaseResult
									?.r_caseResultToSubtasksCasesResults_c_caseResult
									.r_runToCaseResult_c_run,
					  }
					: undefined,
				id: subtaskCaseResult.id,
				name: '',
				subTask: subtaskCaseResult?.r_subtaskToSubtasksCasesResults_c_subtask
					? {
							...subtaskCaseResult?.r_subtaskToSubtasksCasesResults_c_subtask,
							task:
								subtaskCaseResult
									.r_subtaskToSubtasksCasesResults_c_subtask
									?.r_taskToSubtasks_c_task,
							user:
								subtaskCaseResult
									.r_subtaskToSubtasksCasesResults_c_subtask
									?.r_userToSubtasks_user,
					  }
					: undefined,
			}),
			uri: 'subtaskscasesresultses',
		});
	}
}

export const testraySubtaskCaseResultImpl = new TestraySubtaskCaseResultImpl();
