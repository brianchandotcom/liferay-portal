(function() {
	var A = AUI().use('oop');

	var usedModules = {};
	var emptyFn = function() {};

	var Dependency = {
		provide: function(obj, methodName, methodFn, modules, proto) {
			if (!A.Lang.isArray(modules)) {
				modules = [modules];
			}

			var guid = A.guid();
			var before;

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
						Dependency._replaceMethod(obj, methodName, methodFn, context);

						methodFn.apply(context, args);

						return;
					}
				}

				var queue = Dependency._proxyLoaders[guid];
				var firstLoad = false;

				if (!queue) {
					firstLoad = true;
					Dependency._proxyLoaders[guid] = new A.Queue();

					queue = Dependency._proxyLoaders[guid];
				}

				queue.add(args);

				if (firstLoad) {
					modules.push(A.bind(Dependency._proxy, Liferay, obj, methodName, methodFn, context, guid, modules));

					A.use.apply(A, modules);
				}
			};

			proxy.toString = function() {
				return methodFn.toString();
			};

			obj[methodName] = proxy;
		},

		_getAOP: function(obj, methodName) {
			var instance = this;

			return obj._yuiaop && obj._yuiaop[methodName];
		},

		_proxy: function(obj, methodName, methodFn, context, guid, modules, A) {
			var queue = Dependency._proxyLoaders[guid];
			var args;

			Dependency._replaceMethod(obj, methodName, methodFn, context);

			while ((args = queue.next())) {
				methodFn.apply(context, args);
			}

			for (var i = modules.length - 1; i >= 0; i--) {
				usedModules[modules[i]] = true;
			}
		},

		_replaceMethod: function(obj, methodName, methodFn, context) {
			var instance = this;

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

		_proxyLoaders: {}
	};

	Liferay.Dependency = Dependency;

	Liferay.provide = Dependency.provide;
})();