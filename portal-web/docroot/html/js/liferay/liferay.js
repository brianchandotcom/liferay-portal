Liferay = window.Liferay || {};

;(function(A, Liferay) {
	var Lang = A.Lang;

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

	var Service = {
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
				on: {
					success: function(event, id, obj) {
						var instance = this;

						instance.destroy();

						if (callback) {
							callback.call(instance, instance.get('responseData'), obj);
						}
						else {
							xHR = obj;
						}
					}
				},
				sync: !callback
			};

			var xHR = null;

			if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
				config.method = 'GET';
			}

			A.io.request(instance.actionUrl, config);

			if (xHR) {
				return eval('(' + xHR.responseText + ')');
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
	};

	Liferay.Service = Service;

	Liferay.Template = {
		PORTLET: '<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>'
	};
})(AUI(), Liferay);