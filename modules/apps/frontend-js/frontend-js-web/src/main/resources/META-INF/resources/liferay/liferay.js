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

Liferay = window.Liferay || {};

(function($, Liferay) {
	var isFunction = function(val) {
		return typeof val === 'function';
	};

	var isNode = function(node) {
		return node && (node._node || node.jquery || node.nodeType);
	};

	var REGEX_METHOD_GET = /^get$/i;

	var STR_MULTIPART = 'multipart/form-data';

	Liferay.namespace = function namespace(obj, path) {
		if (path === undefined) {
			path = obj;

			obj = this;
		}

		var parts = path.split('.');

		for (var part; parts.length && (part = parts.shift()); ) {
			if (obj[part] && obj[part] !== Object.prototype[part]) {
				obj = obj[part];
			} else {
				obj = obj[part] = {};
			}
		}

		return obj;
	};

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
		var args = Service.parseInvokeArgs(
			Array.prototype.slice.call(arguments, 0)
		);

		return Service.invoke.apply(Service, args);
	};

	Service.URL_INVOKE = themeDisplay.getPathContext() + '/api/jsonws/invoke';

	Service.bind = function() {
		var args = Array.prototype.slice.call(arguments, 0);

		return function() {
			var newArgs = Array.prototype.slice.call(arguments, 0);

			return Service.apply(Service, args.concat(newArgs));
		};
	};

	Service.parseInvokeArgs = function(args) {
		var instance = this;

		var payload = args[0];

		var ioConfig = instance.parseIOConfig(args);

		if (typeof payload === 'string') {
			payload = instance.parseStringPayload(args);

			instance.parseIOFormConfig(ioConfig, args);

			var lastArg = args[args.length - 1];

			if (typeof lastArg === 'object' && lastArg.method) {
				ioConfig.method = lastArg.method;
			}
		}

		return [payload, ioConfig];
	};

	Service.parseIOConfig = function(args) {
		var payload = args[0];

		var ioConfig = payload.io || {};

		delete payload.io;

		if (!ioConfig.success) {
			var callbacks = args.filter(isFunction);

			var callbackException = callbacks[1];
			var callbackSuccess = callbacks[0];

			if (!callbackException) {
				callbackException = callbackSuccess;
			}

			ioConfig.error = callbackException;

			ioConfig.complete = function(response) {
				if (
					response !== null &&
					!Object.prototype.hasOwnProperty.call(response, 'exception')
				) {
					if (callbackSuccess) {
						callbackSuccess.call(this, response);
					}
				} else if (callbackException) {
					var exception = response
						? response.exception
						: 'The server returned an empty response';

					callbackException.call(this, exception, response);
				}
			};
		}

		if (
			!Object.prototype.hasOwnProperty.call(ioConfig, 'cache') &&
			REGEX_METHOD_GET.test(ioConfig.type)
		) {
			ioConfig.cache = false;
		}

		if (Liferay.PropsValues.NTLM_AUTH_ENABLED && Liferay.Browser.isIe()) {
			ioConfig.type = 'GET';
		}

		return ioConfig;
	};

	Service.parseIOFormConfig = function(ioConfig, args) {
		var form = args[1];

		if (isNode(form)) {
			if (form.enctype == STR_MULTIPART) {
				ioConfig.contentType = 'multipart/form-data';
			}

			ioConfig.formData = new FormData(form);
		}
	};

	Service.parseStringPayload = function(args) {
		var params = {};
		var payload = {};

		var config = args[1];

		if (!isFunction(config) && !isNode(config)) {
			params = config;
		}

		payload[args[0]] = params;

		return payload;
	};

	Service.invoke = function(payload, ioConfig) {
		var instance = this;

		var cmd = JSON.stringify(payload);

		var data = cmd;

		if (ioConfig.formData) {
			ioConfig.formData.append('cmd', cmd);
			data = ioConfig.formData;
		}

		return Liferay.Util.fetch(instance.URL_INVOKE, {
			body: data,
			headers: {
				contentType: ioConfig.contentType
			},
			method: 'POST'
		})
			.then(response => response.json())
			.then(ioConfig.complete)
			.catch(ioConfig.error);
	};

	function getHttpMethodFunction(httpMethodName) {
		return function() {
			var args = Array.prototype.slice.call(arguments, 0);

			var method = {method: httpMethodName};

			args.push(method);

			return Service.apply(Service, args);
		};
	}

	Service.get = getHttpMethodFunction('get');
	Service.del = getHttpMethodFunction('delete');
	Service.post = getHttpMethodFunction('post');
	Service.put = getHttpMethodFunction('put');
	Service.update = getHttpMethodFunction('update');

	Liferay.Service = Service;

	Liferay.Template = {
		PORTLET:
			'<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>'
	};
})(AUI.$, Liferay);

(function(A, Liferay) {
	A.mix(
		A.namespace('config.io'),
		{
			method: 'POST',
			uriFormatter(value) {
				return Liferay.Util.getURLWithSessionId(value);
			}
		},
		true
	);
})(AUI(), Liferay);
