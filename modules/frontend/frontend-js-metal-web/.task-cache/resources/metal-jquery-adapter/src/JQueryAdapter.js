define("frontend-js-metal-web@1.0.0/metal-jquery-adapter/src/JQueryAdapter", ['exports'], function (exports) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	var JQueryAdapter = {
		register: function register(name, Ctor) {
			if (!$) {
				throw new Error('jQuery needs to be included in the page for JQueryAdapter to work.');
			}

			if (typeof name !== 'string') {
				throw new Error('The name string is required for registering a plugin');
			}

			if (typeof Ctor !== 'function') {
				throw new Error('The constructor function is required for registering a plugin');
			}

			$.fn[name] = function (configOrMethodName) {
				var args = Array.prototype.slice.call(arguments, 1);
				return handlePluginCall(name, Ctor, this, configOrMethodName, args);
			};
		}
	};

	function callMethod(name, element, methodName, args) {
		var fullName = getPluginFullName(name);
		var instance = element.data(fullName);

		if (!instance) {
			throw new Error('Tried to call method ' + methodName + ' on ' + name + ' plugin' + 'without initialing it first.');
		}

		if (!isValidMethod(instance, methodName)) {
			throw new Error('Plugin ' + name + ' has no method called ' + methodName);
		}

		return instance[methodName].apply(instance, args);
	}

	function createOrUpdateInstance(name, Ctor, element, config) {
		var fullName = getPluginFullName(name);
		var instance = element.data(fullName);
		config = $.extend({}, config, {
			element: element[0]
		});

		if (instance) {
			instance.setAttrs(config);
		} else {
			instance = new Ctor(config).render();
			instance.on('*', onMetalEvent.bind(null, name, element));
			element.data(fullName, instance);
		}
	}

	function getPluginFullName(name) {
		return 'metal-' + name;
	}

	function handlePluginCall(name, Ctor, collection, configOrMethodName, args) {
		if (typeof configOrMethodName === 'string') {
			return callMethod(name, $(collection[0]), configOrMethodName, args);
		} else {
			collection.each(function () {
				createOrUpdateInstance(name, Ctor, $(this), configOrMethodName);
			});
		}

		return collection;
	}

	function isValidMethod(instance, methodName) {
		return typeof instance[methodName] === 'function' && methodName[0] !== '_' && methodName[methodName.length - 1] !== '_';
	}

	function onMetalEvent(name, element, eventType, eventData) {
		var fullName = getPluginFullName(name);
		element.trigger(fullName + ':' + eventType, eventData);
	}

	exports.default = JQueryAdapter;
});
//# sourceMappingURL=JQueryAdapter.js.map