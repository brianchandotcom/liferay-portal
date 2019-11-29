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

(function() {
	var A = AUI().use('oop');

	var usedModules = {};

	var Dependency = {
		_getAOP(obj, methodName) {
			return obj._yuiaop && obj._yuiaop[methodName];
		},

		_proxy(obj, methodName, methodFn, context, guid, modules, _A) {
			var args;

			var queue = Dependency._proxyLoaders[guid];

			Dependency._replaceMethod(obj, methodName, methodFn, context);

			while ((args = queue.next())) {
				methodFn.apply(context, args);
			}

			for (var i = modules.length - 1; i >= 0; i--) {
				usedModules[modules[i]] = true;
			}
		},

		_proxyLoaders: {},

		_replaceMethod(obj, methodName, methodFn) {
			var AOP = Dependency._getAOP(obj, methodName);

			var proxy = obj[methodName];

			if (AOP) {
				proxy = AOP.method;

				AOP.method = methodFn;
			}
			else {
				obj[methodName] = methodFn;
			}

			A.mix(methodFn, proxy);
		},

		provide(obj, methodName, methodFn, modules, proto) {
			if (!Array.isArray(modules)) {
				modules = [modules];
			}

			var before;

			var guid = A.guid();

			if (A.Lang.isObject(methodFn, true)) {
				var config = methodFn;

				methodFn = config.fn;
				before = config.before;

				if (!A.Lang.isFunction(before)) {
					before = null;
				}
			}

			if (proto && A.Lang.isFunction(obj)) {
				obj = obj.prototype;
			}

			var AOP = Dependency._getAOP(obj, methodName);

			if (AOP) {
				delete obj._yuiaop[methodName];
			}

			var proxy = function() {
				var args = arguments;

				var context = obj;

				if (proto) {
					context = this;
				}

				if (modules.length == 1) {
					if (modules[0] in usedModules) {
						Dependency._replaceMethod(
							obj,
							methodName,
							methodFn,
							context
						);

						methodFn.apply(context, args);

						return;
					}
				}

				var firstLoad = false;

				var queue = Dependency._proxyLoaders[guid];

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
							obj,
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

			proxy.toString = function() {
				return methodFn.toString();
			};

			obj[methodName] = proxy;
		}
	};

	Liferay.Dependency = Dependency;

	Liferay.provide = Dependency.provide;
})();
