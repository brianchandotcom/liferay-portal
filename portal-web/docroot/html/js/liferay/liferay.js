Liferay = window.Liferay || {};

(function(A, Liferay) {
	var Lang = A.Lang;

	var owns = A.Object.owns;

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

	var URL_BASE = themeDisplay.getPathContext() + '/api/jsonws/';

	Service.URL_BASE = URL_BASE;

	Service.URL_INVOKE = URL_BASE + 'invoke';

	A.mix(
		Service,
		{
			bind: function() {
				var instance = this;

				var args = A.Array(arguments, 0, true);

				args.unshift(Liferay.Service, Liferay);

				return A.bind.apply(A, args);
			},

			invoke: function(payload, ioConfig) {
				var instance = this;

				return A.io.request(
					Service.URL_INVOKE,
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

				args = A.Array(args, 0, true);

				var payload = args[0];

				var ioConfig = instance.parseIOConfig(args);

				if (Lang.isString(payload)) {
					payload = instance.parseStringPayload(args);
				}

				return [payload, ioConfig];
			},

			parseIOConfig: function(args) {
				var instance = this;

				var payload = args[0];

				var ioConfig = payload.io || {};

				delete payload.io;

				var ioData = args[1];

				if (ioData && !Lang.isFunction(ioData) && (ioData.nodeType || ioData._node)) {
					args.splice(1, 1);

					var formConfig = A.namespace.call(ioConfig, 'form');

					formConfig.id = ioData._node || ioData;
				}

				var callbackConfig = A.namespace.call(ioConfig, 'on');

				if (!callbackConfig.success) {
					var callbacks = A.Array.filter(
						args,
						function(item, index, collection) {
							return index > 0 && Lang.isFunction(item);
						}
					);

					var successCallback = callbacks[0];
					var	exceptionCallback = callbacks[1];

					callbackConfig.success = function(event) {
						var responseData = this.get('responseData');

						if ((!responseData || responseData.exception) && exceptionCallback) {
							var exception = responseData ? responseData.exception : 'The server returned an empty response';

							exceptionCallback.call(this, exception, responseData);
						}
						else {
							successCallback.call(this, responseData);
						}
					};
				}

				var method = ioConfig.method;

				var argLength = args.length;

				var lastArg = args[argLength - 1];

				if (argLength >= 1 && Lang.isObject(lastArg, true)) {
					method = lastArg.method || null;
				}

				if (!owns(ioConfig, 'cache') && REGEX_METHOD_GET.test(method)) {
					ioConfig.cache = false;
				}

				if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
					method = 'GET';
				}

				if (method) {
					ioConfig.method = method;
				}

				return ioConfig;
			},

			parseStringPayload: function(args) {
				var instance = this;

				var params = {};
				var payload = {};

				if (!Lang.isFunction(args[1])) {
					params = args[1];
				}

				payload[args[0]] = params;

				return payload;
			}
		}
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