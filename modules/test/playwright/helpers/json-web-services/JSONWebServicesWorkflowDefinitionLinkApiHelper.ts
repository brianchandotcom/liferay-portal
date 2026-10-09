/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesWorkflowDefinitionLinkApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/workflowdefinitionlink';
	}

	async addWorkflowDefinitionLink({
		className,
		classPK,
		companyId,
		groupId,
		typePK,
		userId,
		workflowDefinitionName,
		workflowDefinitionVersion,
	}: {
		className: string;
		classPK: number | string;
		companyId: number | string;
		groupId: number | string;
		typePK: number | string;
		userId: number | string;
		workflowDefinitionName: string;
		workflowDefinitionVersion: number | string;
	}) {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', getRandomString());
		urlSearchParams.append('userId', String(userId));
		urlSearchParams.append('companyId', String(companyId));
		urlSearchParams.append('groupId', String(groupId));
		urlSearchParams.append('className', className);
		urlSearchParams.append('classPK', String(classPK));
		urlSearchParams.append('typePK', String(typePK));
		urlSearchParams.append(
			'workflowDefinitionName',
			workflowDefinitionName
		);
		urlSearchParams.append(
			'workflowDefinitionVersion',
			String(workflowDefinitionVersion)
		);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-workflow-definition-link`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
