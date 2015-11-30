'use strict';

define("frontend-js-metal-web@1.0.0/metal-jquery-adapter/src/JQueryAdapter", ['exports', 'metal/src/core'], function (exports, _core) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	var JQueryAdapter = {
		register: function register(name, Ctor) {
			if (!$) {
				throw new Error('jQuery needs to be included in the page for JQueryAdapter to work.');
			}

			if (!_core2.default.isString(name)) {
				throw new Error('The name string is required for registering a plugin');
			}

			if (!_core2.default.isFunction(Ctor)) {
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
		if (_core2.default.isString(configOrMethodName)) {
			return callMethod(name, $(collection[0]), configOrMethodName, args);
		} else {
			collection.each(function () {
				createOrUpdateInstance(name, Ctor, $(this), configOrMethodName);
			});
		}

		return collection;
	}

	function isValidMethod(instance, methodName) {
		return _core2.default.isFunction(instance[methodName]) && methodName[0] !== '_' && methodName[methodName.length - 1] !== '_';
	}

	function onMetalEvent(name, element, eventType, eventData) {
		var fullName = getPluginFullName(name);
		element.trigger(fullName + ':' + eventType, eventData);
	}

	exports.default = JQueryAdapter;
});
//# sourceMappingURL=JQueryAdapter.js.map