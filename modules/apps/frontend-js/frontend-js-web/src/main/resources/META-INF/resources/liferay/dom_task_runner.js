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
