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
	var DOMTaskRunner = {
		_scheduledTasks: [],

		_taskStates: [],

		addTask(task) {
			var instance = this;

			instance._scheduledTasks.push(task);
		},

		addTaskState(state) {
			var instance = this;

			instance._taskStates.push(state);
		},

		reset() {
			var instance = this;

			instance._taskStates.length = 0;
			instance._scheduledTasks.length = 0;
		},

		runTasks(node) {
			var instance = this;

			var scheduledTasks = instance._scheduledTasks;
			var taskStates = instance._taskStates;

			var tasksLength = scheduledTasks.length;
			var taskStatesLength = taskStates.length;

			for (var i = 0; i < tasksLength; i++) {
				var task = scheduledTasks[i];

				var taskParams = task.params;

				for (var j = 0; j < taskStatesLength; j++) {
					var state = taskStates[j];

					if (task.condition(state, taskParams, node)) {
						task.action(state, taskParams, node);
					}
				}
			}
		},
	};

	Liferay.DOMTaskRunner = DOMTaskRunner;
})();
