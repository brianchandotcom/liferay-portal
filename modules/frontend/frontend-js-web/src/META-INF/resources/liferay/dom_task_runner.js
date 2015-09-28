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

			for (var i = 0; i < instance._scheduledTasks.length; i++) {
				var task = instance._scheduledTasks[i];

				for (var j = 0; j < instance._taskStates.length; j++) {
					var state = instance._taskStates[j];

					if (task.condition(state, task.params, node)) {
						task.action(state, task.params, node);
					}
				}
			}
		},

		_scheduledTasks: [],
		_taskStates: []
	};

	Liferay.DOMTaskRunner = DOMTaskRunner;
})(Liferay);