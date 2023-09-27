/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	Liferay.lazyLoad = function () {
		let failureCallback;

		const isFunction = function (val) {
			return typeof val === 'function';
		};

		let modules;
		let successCallback;

		if (Array.isArray(arguments[0])) {
			modules = arguments[0];

			successCallback = isFunction(arguments[1]) ? arguments[1] : null;
			failureCallback = isFunction(arguments[2]) ? arguments[2] : null;
		}
		else {
			modules = [];

			for (let i = 0; i < arguments.length; ++i) {
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

		return function () {
			const args = [];

			for (let i = 0; i < arguments.length; ++i) {
				args.push(arguments[i]);
			}

			Liferay.Loader.require(
				modules,
				function () {
					for (let i = 0; i < arguments.length; ++i) {
						args.splice(i, 0, arguments[i]);
					}

					successCallback.apply(successCallback, args);
				},
				failureCallback
			);
		};
	};
})();
