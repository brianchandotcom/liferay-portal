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
import {waitTimeout} from '../../util';
import {searchUtil} from '../../util/search';
import {CaseResultStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import {liferayMessageBoardImpl} from './LiferayMessageBoard';
import Rest from './Rest';
import {testrayCaseResultsIssuesImpl} from './TestrayCaseresultsIssues';
import {testrayIssueImpl} from './TestrayIssues';
import {TestrayCaseResult} from './types';

type CaseResultForm = typeof yupSchema.caseResult.__outputType;

class TestrayCaseResultRest extends Rest<CaseResultForm, TestrayCaseResult> {
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				mbMessageId,
				mbThreadId,
				buildId: r_buildToCaseResult_c_buildId,
				caseId: r_caseToCaseResult_c_caseId,
				dueStatus,
				issues,
				runId: r_runToCaseResult_c_runId,
				startDate,
				userId: r_userToCaseResults_userId = Liferay.ThemeDisplay.getUserId(),
			}) => ({
				dueStatus,
				issues,
				mbMessageId,
				mbThreadId,
				r_buildToCaseResult_c_buildId,
				r_caseToCaseResult_c_caseId,
				r_runToCaseResult_c_runId,
				r_userToCaseResults_userId,
				startDate,
			}),
			nestedFields:
				'case,component.team,build.productVersion,build.routine,run,user',
			transformData: (caseResult) => ({
				...caseResult,
				build: caseResult?.r_buildToCaseResult_c_build
					? {
							...caseResult?.r_buildToCaseResult_c_build,
							productVersion:
								caseResult.r_buildToCaseResult_c_build
									?.r_productVersionToBuilds_c_productVersion,
							routine:
								caseResult.r_buildToCaseResult_c_build
									?.r_routineToBuilds_c_routine,
					  }
					: undefined,
				case: caseResult?.r_caseToCaseResult_c_case
					? {
							...caseResult?.r_caseToCaseResult_c_case,
							caseType:
								caseResult?.r_caseToCaseResult_c_case
									?.r_caseTypeToCases_c_caseType,
							component:
								caseResult?.r_caseToCaseResult_c_case
									?.r_componentToCases_c_component,
					  }
					: undefined,
				component: caseResult?.r_componentToCaseResult_c_component
					? {
							...caseResult.r_componentToCaseResult_c_component,
							team:
								caseResult.r_componentToCaseResult_c_component
									.r_teamToComponents_c_team,
					  }
					: undefined,
				run: caseResult?.r_runToCaseResult_c_run
					? {
							...caseResult?.r_runToCaseResult_c_run,
							build: caseResult?.r_runToCaseResult_c_run?.build,
					  }
					: undefined,
				user: caseResult?.r_userToCaseResults_user
					? {
							...caseResult?.r_userToCaseResults_user,
							id: caseResult?.r_userToCaseResults_user?.uuid,
					  }
					: undefined,
			}),
			uri: 'caseresults',
		});
	}

	public assignTo(caseResult: TestrayCaseResult, userId: number) {
		return this.update(caseResult.id, {
			dueStatus: caseResult.dueStatus.key,
			startDate: caseResult.startDate,
			userId,
		});
	}

	public assignToMe(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			dueStatus: CaseResultStatuses.IN_PROGRESS,
			startDate: caseResult.startDate,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});
	}

	public removeAssign(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			startDate: null,
			userId: this.UNASSIGNED_USER_ID,
		});
	}

	public async assignCaseResultIssue(caseResultId: number, issues: string[]) {
		const caseResultIssuesResponse = await testrayCaseResultsIssuesImpl.getAll(
			searchUtil.eq('caseResultId', caseResultId)
		);

		for (const issue of issues) {
			const testrayIssue = await testrayIssueImpl.createIfNotExist(issue);

			await testrayCaseResultsIssuesImpl.createIfNotExist({
				caseResultId,
				issueId: testrayIssue?.id,
				name: `${issue}-${caseResultId}`,
			});
		}

		if (caseResultIssuesResponse?.items) {
			const caseResultIssuesTransform = testrayCaseResultsIssuesImpl.transformDataFromList(
				caseResultIssuesResponse
			);

			const caseResulIssueIdsToRemove = caseResultIssuesTransform.items
				.filter(({issue}) => !issues.includes(issue?.name || ''))
				.map(({id}) => id);

			for (const caseResultIssueId of caseResulIssueIdsToRemove) {
				await testrayCaseResultsIssuesImpl.remove(caseResultIssueId);
			}
		}
	}

	private async addComment(data: Partial<CaseResultForm>) {
		try {
			const message = data.comment as string;
			let mbThreadId = data.mbThreadId;

			if (!mbThreadId) {
				const mbThread = await liferayMessageBoardImpl.createMbThread(
					message
				);

				mbThreadId = mbThread.id;

				await waitTimeout(1500);
			}

			const mbMessage = await liferayMessageBoardImpl.createMbMessage(
				message,
				mbThreadId as number
			);

			return {mbMessage, mbThreadId};
		}
		catch {
			return {};
		}
	}

	public async update(
		id: number,
		data: Partial<
			CaseResultForm & {defaultMessageId?: number; issues: string[]}
		>
	): Promise<TestrayCaseResult> {
		const issues = data.issues || [];

		await this.assignCaseResultIssue(id, issues);

		if (data.comment) {
			const {mbMessage, mbThreadId} = await this.addComment(data);

			data.mbMessageId = mbMessage.id;
			data.mbThreadId = mbThreadId;
		}

		if (!data.comment && data.mbMessageId) {
			data.mbMessageId = data.defaultMessageId ?? 0;
		}

		return super.update(id, data);
	}
}

export const testrayCaseResultImpl = new TestrayCaseResultRest();
