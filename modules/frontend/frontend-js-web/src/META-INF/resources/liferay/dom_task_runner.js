;(function(Liferay) {
	var DOMTaskRunner = {
		_data: [],
		_scheduled: [],

		addTask: function(action, params, condition) {
			var instance = this;

			instance._scheduled.push(
				{
					action: action,
					condition: condition,
					params: params
				}
			);
		},

		addTaskState: function(owner, data) {
			var instance = this;

			instance._data.push(
				{
					data: data,
					owner: owner
				}
			);
		},

		reset: function() {
			var instance = this;

			instance._data = [];
			instance._scheduled = [];
		},

		runTasks: function(node) {
			var instance = this;

			for (var i = 0; i < instance._scheduled.length; i++) {
				var action = instance._scheduled[i];

				for (var j = 0; j < instance._data.length; j++) {
					var data = instance._data[j];

					if (action.condition(data, action.params, node)) {
						action.action(data, action.params, node);
					}
				}
			}
		}
	};

	Liferay.DOMTaskRunner = DOMTaskRunner;
})(Liferay);