(function(_, Liferay) {
	Liferay.lazyLoad = function() {
		var failureCallback;

		var isFunction = function(val) {
			return typeof val === 'function';
		};

		var isString = function(val) {
			return typeof val === 'string';
		};

		var modules;
		var successCallback;

		if (Array.isArray(arguments[0])) {
			modules = arguments[0];

			successCallback = isFunction(arguments[1]) ? arguments[1] : null;
			failureCallback = isFunction(arguments[2]) ? arguments[2] : null;
		}
		else {
			modules = [];

			for (var i = 0; i < arguments.length; ++i) {
				if (isString(arguments[i])) {
					modules[i] = arguments[i];
				}
				else if (isFunction(arguments[i])) {
					successCallback = arguments[i];
					failureCallback = isFunction(arguments[++i]) ? arguments[i] : null;
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
	};
})(AUI._, Liferay);