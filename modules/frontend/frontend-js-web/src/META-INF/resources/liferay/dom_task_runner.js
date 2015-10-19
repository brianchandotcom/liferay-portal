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

			var tasksLength = instance._scheduledTasks.length;
			var taskStatesLength = instance._taskStates.length;

			for (var i = 0; i < tasksLength; i++) {
				var task = instance._scheduledTasks[i];
				var taskParams = task.params;

				for (var j = 0; j < taskStatesLength; j++) {
					var state = instance._taskStates[j];

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