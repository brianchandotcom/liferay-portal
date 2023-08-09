/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {waitTimeout} from '../../util';
import {CaseResultStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import {liferayMessageBoardImpl} from './LiferayMessageBoard';
import {testrayCaseResultsIssuesImpl} from './TestrayCaseresultsIssues';
import {testrayIssueImpl} from './TestrayIssues';
import {CaseResultAggregation, TestrayCaseResult} from './types';

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
				comment,
				dueStatus,
				issues,
				runId: r_runToCaseResult_c_runId,
				startDate,
				userId: r_userToCaseResults_userId = Liferay.ThemeDisplay.getUserId(),
			}) => ({
				comment,
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
				'case.caseType,component.team.name,team,build.productVersion,build.routine,run,user,caseResultToCaseResultsIssues',
			transformData: (caseResult) => ({
				...caseResult,
				...this.normalizeCaseResultAggregation(caseResult),
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
							component: caseResult?.r_caseToCaseResult_c_case
								?.r_componentToCases_c_component
								? {
										...caseResult?.r_caseToCaseResult_c_case
											?.r_componentToCases_c_component,
										team:
											caseResult
												?.r_caseToCaseResult_c_case
												.r_componentToCases_c_component
												.r_teamToComponents_c_team,
								  }
								: undefined,
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
				issues: caseResult.caseResultToCaseResultsIssues ?? [],
				run: caseResult?.r_runToCaseResult_c_run
					? {
							...caseResult?.r_runToCaseResult_c_run,
							build: caseResult?.r_runToCaseResult_c_run?.build,
					  }
					: undefined,
				runId: caseResult?.r_runToCaseResult_c_runId,
				user: caseResult?.r_userToCaseResults_user,
			}),
			uri: 'caseresults',
		});
	}

	public normalizeCaseResultAggregation(
		caseResultAggregation: CaseResultAggregation
	): CaseResultAggregation {
		return {
			caseResultBlocked: Number(
				caseResultAggregation.caseResultBlocked ?? 0
			),
			caseResultFailed: Number(
				caseResultAggregation.caseResultFailed ?? 0
			),
			caseResultInProgress: Number(
				caseResultAggregation.caseResultInProgress ?? 0
			),
			caseResultIncomplete: Number(
				caseResultAggregation.caseResultIncomplete ?? 0
			),
			caseResultPassed: Number(
				caseResultAggregation.caseResultPassed ?? 0
			),
			caseResultTestFix: Number(
				caseResultAggregation.caseResultTestFix ?? 0
			),
			caseResultUntested: Number(
				caseResultAggregation.caseResultUntested ?? 0
			),
		};
	}

	public assignTo(caseResult: TestrayCaseResult, userId: number) {
		const dueStatus =
			userId === Number(Liferay.ThemeDisplay.getUserId())
				? CaseResultStatuses.IN_PROGRESS
				: caseResult.dueStatus.key;

		return this.update(caseResult.id, {
			dueStatus,
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
			dueStatus: CaseResultStatuses.UNTESTED,
			startDate: null,
			userId: this.UNASSIGNED_USER_ID,
		});
	}

	public async assignCaseResultIssue(caseResultId: number, issues: string[]) {
		const caseResultIssuesResponse = await testrayCaseResultsIssuesImpl.getAll(
			{filter: SearchBuilder.eq('caseResultId', caseResultId)}
		);

		for (const issue of issues) {
			const testrayIssue = await testrayIssueImpl.createIfNotExist({
				name: issue,
			});

			await testrayCaseResultsIssuesImpl.createIfNotExist({
				caseResultId,
				issueId: testrayIssue?.id,
				name: `${issue}${testrayIssueImpl.DELIMITER}${caseResultId}`,
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
			CaseResultForm & {
				defaultMessageId?: number;
				issues: string[];
			}
		>
	): Promise<TestrayCaseResult> {
		const issues = data.issues || [];

		if (data.issues) {
			await this.assignCaseResultIssue(id, issues);
		}

		if (data.comment) {
			const {mbMessage, mbThreadId} = await this.addComment(data);

			data.mbMessageId = mbMessage.id;
			data.mbThreadId = mbThreadId;
		}

		if (!data.comment && data.mbMessageId) {
			data.mbMessageId = data.defaultMessageId ?? 0;
		}

		return super.update(id, {...data, issues: issues.join(', ')});
	}
}

export const testrayCaseResultImpl = new TestrayCaseResultRest();
