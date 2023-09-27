/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay = window.Liferay || {};

(function () {
	const isFunction = function (val) {
		return typeof val === 'function';
	};

	const isNode = function (node) {
		return node && (node._node || node.jquery || node.nodeType);
	};

	const REGEX_METHOD_GET = /^get$/i;

	const STR_MULTIPART = 'multipart/form-data';

	Liferay.namespace = function namespace(object, path) {
		if (path === undefined) {
			path = object;

			object = this;
		}

		const parts = path.split('.');

		for (let part; parts.length && (part = parts.shift()); ) {
			if (object[part] && object[part] !== Object.prototype[part]) {
				object = object[part];
			}
			else {
				object = object[part] = {};
			}
		}

		return object;
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

	const Service = function () {
		const args = Service.parseInvokeArgs(
			Array.prototype.slice.call(arguments, 0)
		);

		return Service.invoke.apply(Service, args);
	};

	Service.URL_INVOKE = themeDisplay.getPathContext() + '/api/jsonws/invoke';

	Service.bind = function () {
		const args = Array.prototype.slice.call(arguments, 0);

		return function () {
			const newArgs = Array.prototype.slice.call(arguments, 0);

			return Service.apply(Service, args.concat(newArgs));
		};
	};

	Service.parseInvokeArgs = function (args) {
		const instance = this;

		let payload = args[0];

		const ioConfig = instance.parseIOConfig(args);

		if (typeof payload === 'string') {
			payload = instance.parseStringPayload(args);

			instance.parseIOFormConfig(ioConfig, args);

			const lastArg = args[args.length - 1];

			if (typeof lastArg === 'object' && lastArg.method) {
				ioConfig.method = lastArg.method;
			}
		}

		return [payload, ioConfig];
	};

	Service.parseIOConfig = function (args) {
		const payload = args[0];

		const ioConfig = payload.io || {};

		delete payload.io;

		if (!ioConfig.success) {
			const callbacks = args.filter(isFunction);

			let callbackException = callbacks[1];
			const callbackSuccess = callbacks[0];

			if (!callbackException) {
				callbackException = callbackSuccess;
			}

			ioConfig.error = callbackException;

			ioConfig.complete = function (response) {
				if (
					!Object.prototype.hasOwnProperty.call(response, 'exception')
				) {
					if (callbackSuccess) {
						callbackSuccess.call(this, response);
					}
				}
				else if (callbackException) {
					const exception = response
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

		return ioConfig;
	};

	Service.parseIOFormConfig = function (ioConfig, args) {
		const form = args[1];

		if (isNode(form)) {
			if (form.enctype === STR_MULTIPART) {
				ioConfig.contentType = 'multipart/form-data';
			}

			ioConfig.formData = new FormData(form);
		}
	};

	Service.parseStringPayload = function (args) {
		let params = {};
		const payload = {};

		const config = args[1];

		if (!isFunction(config) && !isNode(config)) {
			params = config;
		}

		payload[args[0]] = params;

		return payload;
	};

	Service.invoke = function (payload, ioConfig) {
		const instance = this;

		const cmd = JSON.stringify(payload);

		let data = cmd;

		if (ioConfig.formData) {
			ioConfig.formData.append('cmd', cmd);
			data = ioConfig.formData;
		}

		return Liferay.Util.fetch(instance.URL_INVOKE, {
			body: data,
			headers: {
				contentType: ioConfig.contentType,
			},
			method: 'POST',
		})
			.then((response) =>
				Promise.all([Promise.resolve(response), response.json()])
			)
			.then(([response, content]) => {
				if (response.ok) {
					ioConfig.complete(content);
				}
				else {
					ioConfig.error();
				}
			})
			.catch(ioConfig.error);
	};

	function getHttpMethodFunction(httpMethodName) {
		return function () {
			const args = Array.prototype.slice.call(arguments, 0);

			const method = {method: httpMethodName};

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
			'<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>',
	};
})();
