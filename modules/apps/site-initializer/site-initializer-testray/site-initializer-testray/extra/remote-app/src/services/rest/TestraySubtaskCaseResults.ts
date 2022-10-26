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
				name,
				subtaskId: r_subtaskToSubtasksCasesResults_c_subtaskId,
			}) => ({
				name,
				r_caseResultToSubtasksCasesResults_c_caseResultId,
				r_subtaskToSubtasksCasesResults_c_subtaskId,
			}),
			nestedFields:
				'caseResult.case,caseResult.component.team,caseResult.build.routine,caseResult.build.project,caseResult.run,subtask',
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
				subTask:
					subtaskCaseResult?.r_subtaskToSubtasksCasesResults_c_subtask,
			}),
			uri: 'subtaskscasesresultses',
		});
	}
}

export const testraySubtaskCaseResultImpl = new TestraySubtaskCaseResultImpl();
