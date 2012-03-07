Liferay = window.Liferay || {};

;(function(A, Liferay) {
	var Lang = A.Lang;

	var REGEX_SELECTOR_ID = /^#/;

	var REGEX_TRIM_SLASH = /^\/|\/$/g;

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
	 * service {string|object}: Either the service name, or an IO configuration object containing a property named service.
	 *
	 * Optional
	 * data {object|node|string}: The data to send to the service. If the object passed is the ID of a form or a form element, the form fields will be serialized and used as the data.
	 * successCallback {function}: A function to execute when the server returns a response. It receives a JSON object as it's first parameter.
	 * exceptionCallback {function}: A function to execute when the response from the server contains a service exception. It receives a the exception message as it's first parameter.
	 */

	var Service = function(service, data, successCallback, exceptionCallback, methodType) {
		var config = {
			dataType: 'json'
		};

		var argLength = arguments.length;

		var lastArg = arguments[argLength - 1];

		var method;

		if (argLength >= 1 && Lang.isObject(lastArg, true)) {
			methodType = lastArg.method || null;

			method = methodType;
		}

		if (service) {
			var serviceData = data;

			if (Lang.isObject(service, true)) {
				var serviceConfig = service;

				var ioConfig = serviceConfig.io;

				if (ioConfig) {
					A.mix(config, ioConfig, true);

					delete serviceConfig.io;
				}

				for (var i in serviceConfig) {
					if (A.Object.owns(serviceConfig, i)) {
						service = i;
						serviceData = serviceConfig[i];

						break;
					}
				}
			}

			if (data) {
				var typeString = Lang.isString(data);

				if (!typeString && Lang.isFunction(data)) {
					if (Lang.isFunction(successCallback)) {
						exceptionCallback = successCallback;
					}

					successCallback = data;

					serviceData = null;
				}
				else if (typeString || (data.nodeType || data._node)) {
					var formConfig = A.namespace.call(config, 'form');

					if (typeString) {
						data = data.replace(REGEX_SELECTOR_ID, '');
					}

					formConfig.id = data._node || data;

					serviceData = null;
				}
			}

			config.data = serviceData;

			if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
				method = 'GET';
			}

			var callbackConfig = A.namespace.call(config, 'on');

			if (!Lang.isFunction(successCallback)) {
				successCallback = null;
			}

			if (!Lang.isFunction(exceptionCallback)) {
				exceptionCallback = null;
			}

			if (!callbackConfig.success && successCallback) {
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
		}

		if (service) {
			service = service.replace(REGEX_TRIM_SLASH, '');
		}

		if (!service) {
			throw 'You must specify a service.';
		}

		if (String(method).toUpperCase() == 'GET') {
			config.cache = false;
		}

		config.method = method;

		return Service._ioRequest(Service.URL_BASE + service, config);
	};

	Service.URL_BASE = themeDisplay.getPathContext() + '/api/jsonws/';

	A.mix(
		Service,
		{
			actionUrl: themeDisplay.getPathMain() + '/portal/json_service',

			classNameSuffix: 'ServiceUtil',

			ajax: function(options, callback) {
				var instance = this;

				options.serviceParameters = Service.getParameters(options);
				options.doAsUserId = themeDisplay.getDoAsUserIdEncoded();

				var config = {
					cache: false,
					data: options,
					dataType: 'json',
					on: {}
				};

				var xHR = null;

				if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
					config.method = 'GET';
				}

				if (callback) {
					config.on.success = function(event, id, obj) {
						callback.call(this, this.get('responseData'), obj);
					};
				}
				else {
					config.on.success = function(event, id, obj) {
						xHR = obj;
					};

					config.sync = true;
				}

				instance._ioRequest(instance.actionUrl, config);

				if (xHR) {
					return eval('(' + xHR.responseText + ')');
				}
			},

			_ioRequest: function(uri, config) {
				var instance = this;

				if (A.io && A.io.request) {
					A.io.request(uri, config);
				}
				else {
					A.use(
						'aui-io-request',
						function(A) {
							A.io.request(uri, config);
						}
					);
				}
			},

			getParameters: function(options) {
				var instance = this;

				var serviceParameters = [];

				for (var key in options) {
					if ((key != 'servletContextName') && (key != 'serviceClassName') && (key != 'serviceMethodName') && (key != 'serviceParameterTypes')) {
						serviceParameters.push(key);
					}
				}

				return instance._getJSONParser().stringify(serviceParameters);
			},

			namespace: function(namespace) {
				var curLevel = Liferay || {};

				if (typeof namespace == 'string') {
					var levels = namespace.split('.');

					for (var i = (levels[0] == 'Liferay') ? 1 : 0; i < levels.length; i++) {
						curLevel[levels[i]] = curLevel[levels[i]] || {};
						curLevel = curLevel[levels[i]];
					}
				}
				else {
					curLevel = namespace || {};
				}

				return curLevel;
			},

			register: function(serviceName, servicePackage, servletContextName) {
				var module = Service.namespace(serviceName);

				module.servicePackage = servicePackage.replace(/[.]$/, '') + '.';

				if (servletContextName) {
					module.servletContextName = servletContextName;
				}

				return module;
			},

			registerClass: function(serviceName, className, prototype) {
				var module = serviceName || {};
				var moduleClassName = module[className] = {};

				moduleClassName.serviceClassName = module.servicePackage + className + Service.classNameSuffix;

				A.Object.each(
					prototype,
					function(item, index, collection) {
						var handler = item;

						if (!Lang.isFunction(handler)) {
							handler = function(params, callback) {
								params.serviceClassName = moduleClassName.serviceClassName;
								params.serviceMethodName = index;

								if (module.servletContextName) {
									params.servletContextName = module.servletContextName;
								}

								return Service.ajax(params, callback);
							};
						}

						moduleClassName[index] = handler;
					}
				);
			},

			_getJSONParser: function() {
				var instance = this;

				if (!instance._JSONParser) {
					var JSONParser = A.JSON;

					if (!JSONParser) {
						JSONParser = AUI({}).use('json').JSON;
					}

					instance._JSONParser = JSONParser;
				}

				return instance._JSONParser;
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

	Liferay.Template = {
		PORTLET: '<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>'
	};
})(AUI(), Liferay);