(function(_, Liferay) {
	Liferay.lazyLoad = function() {
		var failureCallback;
		var modules;
		var successCallback;

		if (_.isArray(arguments[0])) {
			modules = arguments[0];

			successCallback = _.isFunction(arguments[1]) ? arguments[1] : null;
			failureCallback = _.isFunction(arguments[2]) ? arguments[2] : null;
		}
		else {
			modules = [];

			for (var i = 0; i < arguments.length; ++i) {
				if (_.isString(arguments[i])) {
					modules[i] = arguments[i];
				}
				else if (_.isFunction(arguments[i])) {
					successCallback = arguments[i];
					failureCallback = _.isFunction(arguments[++i]) ? arguments[i] : null;

					break;
				}
			}
		}

		return function() {
			var args = [];

			for (var i = 0; i < arguments.length; ++i) {
				args.push(arguments[i]);
			}

			Liferay.Loader.require(
				modules,
				function() {
					for (var i = 0; i < arguments.length; ++i) {
						args.splice(i, 0, arguments[i]);
					}

					successCallback.apply(successCallback, args);
				},
				failureCallback
			);
		};
	}
})(AUI._, Liferay);