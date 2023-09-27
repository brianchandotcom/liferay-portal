/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const DOMTaskRunner = {
		_scheduledTasks: [],

		_taskStates: [],

		addTask(task) {
			const instance = this;

			instance._scheduledTasks.push(task);
		},

		addTaskState(state) {
			const instance = this;

			instance._taskStates.push(state);
		},

		reset() {
			const instance = this;

			instance._taskStates.length = 0;
			instance._scheduledTasks.length = 0;
		},

		runTasks(node) {
			const instance = this;

			const scheduledTasks = instance._scheduledTasks;
			const taskStates = instance._taskStates;

			const tasksLength = scheduledTasks.length;
			const taskStatesLength = taskStates.length;

			for (let i = 0; i < tasksLength; i++) {
				const task = scheduledTasks[i];

				const taskParams = task.params;

				for (let j = 0; j < taskStatesLength; j++) {
					const state = taskStates[j];

					if (task.condition(state, taskParams, node)) {
						task.action(state, taskParams, node);
					}
				}
			}
		},
	};

	Liferay.DOMTaskRunner = DOMTaskRunner;
})();
