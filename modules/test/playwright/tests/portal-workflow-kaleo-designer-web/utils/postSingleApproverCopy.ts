/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';

export default async function postSingleApproverCopy(apiHelpers: ApiHelpers) {
	const singleApproverWorkflowDefinition =
		await apiHelpers.headlessAdminWorkflow.getWorkflowDefinitionByName(
			'Single Approver'
		);

	const workflowDefinition =
		await apiHelpers.headlessAdminWorkflow.postWorkflowDefinitionSave(
			'Copy of Single Approver' + getRandomInt(),
			{
				...singleApproverWorkflowDefinition,
				externalReferenceCode: getRandomString(),
			}
		);

	return workflowDefinition;
}
