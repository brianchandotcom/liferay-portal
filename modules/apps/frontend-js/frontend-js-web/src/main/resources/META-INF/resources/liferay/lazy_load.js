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

(function(Liferay) {
	Liferay.lazyLoad = function() {
		var failureCallback;

		var isFunction = function(val) {
			return typeof val === 'function';
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
				if (typeof arguments[i] === 'string') {
					modules[i] = arguments[i];
				}
				else if (isFunction(arguments[i])) {
					successCallback = arguments[i];
					failureCallback = isFunction(arguments[++i])
						? arguments[i]
						: null;
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
})(Liferay);
