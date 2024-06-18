/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import os from 'os';

export const MAX_CONCURRENT_TASKS = os.cpus().length;

export default async function runConcurrentTasks(
	tasks,
	maxConcurrent = MAX_CONCURRENT_TASKS
) {
	const results = [];
	const runningTasks = new Set();

	async function runTask(task, index) {
		const result = await task();

		results[index] = result;
	}

	for (let i = 0; i < tasks.length; i++) {
		const task = tasks[i];

		const promise = runTask(task, i).finally(() =>
			runningTasks.delete(promise)
		);

		runningTasks.add(promise);

		if (runningTasks.size >= maxConcurrent) {

			// Using race twait for the fastest to complete

			await Promise.race(runningTasks);
		}
	}

	await Promise.all(runningTasks);

	return results;
}
