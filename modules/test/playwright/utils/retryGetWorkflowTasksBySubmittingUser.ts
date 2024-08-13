/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';

type Params = {
	expectedNumberOfTasks: number;
	page: Page;
	retryInterval?: number;
	retryLimit?: number;
	submittingUser: number;
};

/**
 * Workflow tasks are dynamically generated in the back-end whenever an asset assigned to a workflow is
 * created. Due to this, there is a delay between the creation of an asset and the availability of
 * its corresponding task.
 *
 * This function repeatedly retrieves tasks until the expected amount is returned.
 */
export default async function ({
	expectedNumberOfTasks,
	page,
	retryInterval = 500,
	retryLimit = 10,
	submittingUser,
}: Params): Promise<WorkflowTaskDefinitions> {
	const apiHelpers = new ApiHelpers(page);
	let workflowTasks: WorkflowTaskDefinitions;
	let attempt = 0;

	while (attempt < retryLimit) {
		workflowTasks =
			await apiHelpers.headlessAdminWorkflow.getWorkflowTasksBySubmittingUser(
				submittingUser,
				-1
			);

		if (workflowTasks?.items?.length === expectedNumberOfTasks) {
			return workflowTasks;
		}

		attempt++;

		if (retryInterval) {
			await new Promise((resolve) => setTimeout(resolve, retryInterval));
		}
	}

	throw new Error(
		`Failed to retrieve the expected number of tasks after ${retryLimit} attempts.`
	);
}
