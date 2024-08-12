/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessAdminWorkflowApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-workflow/v1.0/';
	}

	async deleteWorkflowDefinition(workflowDefinitionId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/workflow-definitions/${workflowDefinitionId}`
		);
	}

	async getWorkflowDefinitionByName(name: string) {
		return (await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/workflow-definitions/by-name/${name}`
		)) as WorkflowDefinition;
	}

	async getWorkflowTasksBySubmittingUser(
		creatorId: number,
		pageSize?: number
	) {
		return await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/workflow-tasks/submitting-user?creatorId=${creatorId}` +
				(pageSize ? `&pageSize=${pageSize}` : '')
		);
	}

	async postAssignTaskToUser(workflowTaskId: number, assigneeId: number) {
		return await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/workflow-tasks/${workflowTaskId}/assign-to-user`,
			{
				data: {
					assigneeId,
				},
			}
		);
	}

	async postWorkflowDefinitionSave(
		name: string,
		workflowDefinition: Partial<WorkflowDefinition>
	): Promise<WorkflowDefinition> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/workflow-definitions/save`,
			{
				data: {
					...workflowDefinition,
					name,
					title: name,
					title_i18n: {...workflowDefinition.title_i18n, en_US: name},
				},
			}
		);
	}
}
