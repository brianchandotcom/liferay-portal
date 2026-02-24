/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const WORKFLOW_DEFINITION_BASE_URI =
	'/o/headless-admin-workflow/v1.0/workflow-definitions';

async function getWorkflowDefinitions() {
	const response = await fetch(`${WORKFLOW_DEFINITION_BASE_URI}?scope=ai`, {
		method: 'GET',
	});

	return response.json();
}

export {getWorkflowDefinitions};
