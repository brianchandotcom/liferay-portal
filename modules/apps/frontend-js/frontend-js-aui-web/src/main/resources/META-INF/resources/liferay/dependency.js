/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const A = AUI().use('oop');

	const usedModules = {};

	const Dependency = {
		_getAOP(object, methodName) {
			return object._yuiaop && object._yuiaop[methodName];
		},

		_proxy(object, methodName, methodFn, context, guid, modules, _A) {
			let args;

			const queue = Dependency._proxyLoaders[guid];

			Dependency._replaceMethod(object, methodName, methodFn, context);

			while ((args = queue.next())) {
				methodFn.apply(context, args);
			}

			for (let i = modules.length - 1; i >= 0; i--) {
				usedModules[modules[i]] = true;
			}
		},

		_proxyLoaders: {},

		_replaceMethod(object, methodName, methodFn) {
			const AOP = Dependency._getAOP(object, methodName);

			let proxy = object[methodName];

			if (AOP) {
				proxy = AOP.method;

				AOP.method = methodFn;
			}
			else {
				object[methodName] = methodFn;
			}

			A.mix(methodFn, proxy);
		},

		provide(object, methodName, methodFn, modules, proto) {
			if (!Array.isArray(modules)) {
				modules = [modules];
			}

			let before;

			const guid = A.guid();

			if (A.Lang.isObject(methodFn, true)) {
				const config = methodFn;

				methodFn = config.fn;
				before = config.before;

				if (!A.Lang.isFunction(before)) {
					before = null;
				}
			}

			if (proto && A.Lang.isFunction(object)) {
				object = object.prototype;
			}

			const AOP = Dependency._getAOP(object, methodName);

			if (AOP) {
				delete object._yuiaop[methodName];
			}

			const proxy = function () {
				const args = arguments;

				let context = object;

				if (proto) {
					context = this;
				}

				if (modules.length === 1) {
					if (modules[0] in usedModules) {
						Dependency._replaceMethod(
							object,
							methodName,
							methodFn,
							context
						);

						methodFn.apply(context, args);

						return;
					}
				}

				let firstLoad = false;

				let queue = Dependency._proxyLoaders[guid];

				if (!queue) {
					firstLoad = true;

					Dependency._proxyLoaders[guid] = new A.Queue();

					queue = Dependency._proxyLoaders[guid];
				}

				queue.add(args);

				if (firstLoad) {
					modules.push(
						A.bind(
							Dependency._proxy,
							Liferay,
							object,
							methodName,
							methodFn,
							context,
							guid,
							modules
						)
					);

					A.use.apply(A, modules);
				}
			};

			proxy.toString = function () {
				return methodFn.toString();
			};

			object[methodName] = proxy;
		},
	};

	Liferay.Dependency = Dependency;

	Liferay.provide = Dependency.provide;
})();
