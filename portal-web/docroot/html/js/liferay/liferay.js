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

	var ServiceUtil = {
		invoke: function(payload, ioConfig) {
			var instance = this;

			if (ioConfig) {
				if (!owns(ioConfig, 'cache') && REGEX_METHOD_GET.test(ioConfig.method)) {
					ioConfig.cache = false;
				}

				if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
					ioConfig.method = 'GET';
				}
			}

			return A.io.request(
				themeDisplay.getPathContext() + '/api/jsonws/invoke',
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
			}

			return [ payload, ioConfig ];
		},

		parseIOConfig: function(args) {
			var instance = this;

			var payload = args[0];

			var ioConfig = payload.io;

			payload.io = undefined;

			if (!ioConfig) {
				var callbacks = [];
				var callbackIndex = 0;

				while (args[callbackIndex++]) {
					var callback = args[callbackIndex++];

					if (Lang.isFunction(callback)) {
						callbacks.push(callback);
					}
				}

				var callbackSuccess = callbacks[0];
				var callbackException = callbacks[1];

				ioConfig = {
					on: {
						success: function(event) {
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
						}
					}
				};
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
	};

	Liferay.Service = function() {
		var instance = this;

		var args = ServiceUtil.parseInvokeArgs(arguments);

		ServiceUtil.invoke.apply(ServiceUtil, args);
	};

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
}(AUI(), Liferay));