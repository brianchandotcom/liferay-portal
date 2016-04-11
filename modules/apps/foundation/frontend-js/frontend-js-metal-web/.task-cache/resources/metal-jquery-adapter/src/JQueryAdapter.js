define("frontend-js-metal-web@1.0.6/metal-jquery-adapter/src/JQueryAdapter", ['exports'], function (exports) {
	'use strict';

	/**
  * Acts as a bridge between Metal.js and jQuery, allowing Metal.js components to
  * be used as jQuery plugins.
  * @type {!Object}
  */

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

	/**
  * Calls a method on the plugin instance for the given element.
  * @param {string} name The name of the plugin.
  * @param {!jQuery} element A jQuery collection with a single element.
  * @param {string} methodName The name of the method to be called.
  * @param {Array} args The arguments to call the method with.
  * @return {*} The return value of the called method.
  */
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

	/**
  * Creates an instace of a component for the given element, or updates it if one
  * already exists.
  * @param {string} name The name of the plugin.
  * @param {!Function(Object)} Ctor The constructor of the Metal.js component.
  * @param {!jQuery} element A jQuery collection with a single element.
  * @param {Object} config A config object to be passed to the component instance.
  */
	function createOrUpdateInstance(name, Ctor, element, config) {
		var fullName = getPluginFullName(name);
		var instance = element.data(fullName);
		config = $.extend({}, config, {
			element: element[0]
		});
		if (instance) {
			instance.setState(config);
		} else {
			instance = new Ctor(config);
			instance.on('*', onMetalEvent.bind(null, name, element));
			element.data(fullName, instance);
		}
	}

	/**
  * Gets the full name of the given plugin, by appending a prefix to it.
  * @param {string} name The name of the plugin.
  * @return {string}
  */
	function getPluginFullName(name) {
		return 'metal-' + name;
	}

	/**
  * Handles calls to a registered plugin.
  * @param {string} name The name of the plugin.
  * @param {!Function(Object)} Ctor The constructor of the Metal.js component.
  * @param {!jQuery} collection A jQuery collection of elements to handle the plugin for.
  * @param {?(string|Object)} configOrMethodName If this is a string, a method with
  * that name will be called on the appropriate component instance. Otherwise, an
  * the instance (which will be created if it doesn't yet exist) will receive this
  * as its config object.
  * @param {Array} args All other arguments that were passed to the plugin call.
  */
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

	/**
  * Checks if the given method is valid. A method is valid if it exists and isn't
  * private.
  * @param {!Object} instance The instance to check for the method.
  * @param {string} methodName The name of the method to check.
  * @return {boolean}
  */
	function isValidMethod(instance, methodName) {
		return typeof instance[methodName] === 'function' && methodName[0] !== '_' && methodName[methodName.length - 1] !== '_';
	}

	/**
  * Called when an event is triggered on a Metal component that has been registered
  * as a jQuery plugin. Triggers a similar event on the jQuery element tied to the
  * plugin.
  * @param {string} name The name of the plugin.
  * @param {!jQuery} element A jQuery collection with a single element.
  * @param {string} eventType The name of the Metal.js event type.
  * @param {*} eventData Event data that was passed to the listener of the Metal.js
  *   event.
  */
	function onMetalEvent(name, element, eventType, eventData) {
		var fullName = getPluginFullName(name);
		element.trigger(fullName + ':' + eventType, eventData);
	}

	exports.default = JQueryAdapter;
});
//# sourceMappingURL=JQueryAdapter.js.map