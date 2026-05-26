/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useEffect, useState} from 'react';

import {
	getWorkflowDefinition,
	getWorkflowDefinitions,
} from '../services/WorkflowDefinitionService';
import {WorkflowDefinition} from '../types/WorkflowDefinition';

interface UseWorkflowDefinitionsProps {
	readOnly: boolean;
	workflowDefinitionName: string;
}

export function useWorkflowDefinitions({
	readOnly,
	workflowDefinitionName,
}: UseWorkflowDefinitionsProps): WorkflowDefinition[] {
	const [workflowDefinitions, setWorkflowDefinitions] = useState<
		WorkflowDefinition[]
	>([]);

	useEffect(() => {
		async function fetchWorkflowDefinitions() {
			try {
				if (readOnly) {
					if (!workflowDefinitionName) {
						return;
					}

					const response = await getWorkflowDefinition(
						workflowDefinitionName
					);

					setWorkflowDefinitions([response]);

					return;
				}

				const response = await getWorkflowDefinitions();

				setWorkflowDefinitions(response.items || []);
			}
			catch (error) {
				console.error(error);

				openToast({
					message: Liferay.Language.get(
						'failed-to-load-workflow-definitions'
					),
					type: 'danger',
				});
			}
		}

		fetchWorkflowDefinitions();
	}, [readOnly, workflowDefinitionName]);

	return workflowDefinitions;
}
