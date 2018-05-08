;(function(Liferay) {
	var DOMTaskRunner = {
		addTask: function(task) {
			var instance = this;

			instance._scheduledTasks.push(task);
		},

		addTaskState: function(state) {
			var instance = this;

			instance._taskStates.push(state);
		},

		reset: function() {
			var instance = this;

			instance._taskStates.length = 0;
			instance._scheduledTasks.length = 0;
		},

		runTasks: function(node) {
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

		_scheduledTasks: [],
		_taskStates: []
	};

	Liferay.DOMTaskRunner = DOMTaskRunner;
})(Liferay);