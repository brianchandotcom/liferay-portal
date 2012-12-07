Liferay = window.Liferay || {};

(function(A, Liferay) {
	var Lang = A.Lang;

	var owns = A.Object.owns;

	var isNode = function(node) {
		return node && (node._node || node.nodeType);
	};

	var REGEX_METHOD_GET = /^get$/i;

	Liferay.namespace = A.namespace;

	A.mix(
		AUI.defaults.io,
		{
			method: 'POST',
			uriFormatter: function(value) {
				return Liferay.Util.getURLWithSessionId(value);
			}
		},
		true
	);

	/**
	 * OPTIONS
	 *
	 * Required
	 * service {string|object}: Either the service name, or an object with the keys as the service to call, and the value as the service configuration object.
	 *
	 * Optional
	 * data {object|node|string}: The data to send to the service. If the object passed is the ID of a form or a form element, the form fields will be serialized and used as the data.
	 * successCallback {function}: A function to execute when the server returns a response. It receives a JSON object as it's first parameter.
	 * exceptionCallback {function}: A function to execute when the response from the server contains a service exception. It receives a the exception message as it's first parameter.
	 */

	var Service = function() {
		var instance = this;

		var args = Service.parseInvokeArgs(arguments);

		Service.invoke.apply(Service, args);
	};

	A.mix(
		Service,
		{
			URL_INVOKE: themeDisplay.getPathContext() + '/api/jsonws/invoke',

			bind: function() {
				var instance = this;

				var args = A.Array(arguments, 0, true);

				args.unshift(Liferay.Service, Liferay);

				return A.bind.apply(A, args);
			},

			invoke: function(payload, ioConfig) {
				var instance = this;

				return A.io.request(
					instance.URL_INVOKE,
					A.merge(
						{
							data: {
								cmd: A.JSON.stringify(payload),
								p_auth: Liferay.authToken
							},
							dataType: 'json'
						},
						ioConfig
					)
				);
			},

			parseInvokeArgs: function(args) {
				var instance = this;

				var payload = args[0];

				var ioConfig = instance.parseIOConfig(args);

				if (Lang.isString(payload)) {
					payload = instance.parseStringPayload(args);

					instance.parseIOFormConfig(ioConfig, args);

					var lastArg = args[args.length - 1];

					if (Lang.isObject(lastArg) && lastArg.method) {
						ioConfig.method = lastArg.method;
					}
				}

				return [payload, ioConfig];
			},

			parseIOConfig: function(args) {
				var instance = this;

				var payload = args[0];

				var ioConfig = payload.io || {};

				delete payload.io;

				if (!(ioConfig.on && ioConfig.on.success)) {
					var callbacks = A.Array.filter(args, Lang.isFunction);

					var callbackSuccess = callbacks[0];
					var callbackException = callbacks[1];

					if (!callbackException) {
						callbackException = callbackSuccess;
					}

					A.namespace.call(ioConfig, 'on');

					ioConfig.on.success = function(event) {
						var responseData = this.get('responseData');

						if (responseData && !owns(responseData, 'exception')) {
							if (callbackSuccess) {
								callbackSuccess.call(this, responseData);
							}
						}
						else if (callbackException) {
							var exception = responseData ? responseData.exception : 'The server returned an empty response';

							callbackException.call(this, exception, responseData);
						}
					};
				}

				if (!owns(ioConfig, 'cache') && REGEX_METHOD_GET.test(ioConfig.method)) {
					ioConfig.cache = false;
				}

				if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
					ioConfig.method = 'GET';
				}

				return ioConfig;
			},

			parseIOFormConfig: function(ioConfig, args) {
				var instance = this;

				var form = args[1];

				if (isNode(form)) {
					A.namespace.call(ioConfig, 'form');

					ioConfig.form.id = form._node || form;
				}
			},

			parseStringPayload: function(args) {
				var instance = this;

				var params = {};
				var payload = {};

				var config = args[1];

				if (!Lang.isFunction(config) && !isNode(config)) {
					params = config;
				}

				payload[args[0]] = params;

				return payload;
			}
		},
		true
	);

	A.each(
		['get', 'delete', 'post', 'put', 'update'],
		function(item, index, collection) {
			var methodName = item;

			if (item === 'delete') {
				methodName = 'del';
			}

			Service[methodName] = A.rbind(
				'Service',
				Liferay,
				{
					method: item
				}
			);
		}
	);

	Liferay.Service = Service;

	var components = {};
	var componentsFn = {};

	Liferay.component = function(id, value) {
		var retVal;

		if (arguments.length === 1) {
			var component = components[id];

			if (component && Lang.isFunction(component)) {
				componentsFn[id] = component;

				component = component();

				components[id] = component;
			}

			retVal = component;
		}
		else {
			retVal = (components[id] = value);
		}

		return retVal;
	};

	Liferay._components = components;
	Liferay._componentsFn = components;

	Liferay.Template = {
		PORTLET: '<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>'
	};
})(AUI(), Liferay);