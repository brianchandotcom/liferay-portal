/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Store Utility
 *
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Util.Session
 * @module liferay-store
 */

AUI.add(
	'liferay-store',
	(A) => {
		const Lang = A.Lang;

		const isObject = Lang.isObject;

		const TOKEN_SERIALIZE = 'serialize://';

		const Store = function (key, value) {
			let method;

			if (Lang.isFunction(value)) {
				method = 'get';

				if (Array.isArray(key)) {
					method = 'getAll';
				}
			}
			else {
				method = 'set';

				if (isObject(key)) {
					method = 'setAll';
				}
				else if (arguments.length === 1) {
					method = null;
				}
			}

			if (method) {
				Store[method].apply(Store, arguments);
			}
		};

		A.mix(Store, {
			_getValues(cmd, key, callback) {
				const instance = this;

				const config = {
					callback,
					data: {
						cmd,
						key,
					},
				};

				if (cmd === 'getAll') {
					config.dataType = 'json';
				}

				instance._ioRequest(config);
			},

			_ioRequest(config) {
				config.data.p_auth = Liferay.authToken;

				const body = new URLSearchParams();

				Object.keys(config.data).forEach((key) => {
					if (Array.isArray(config.data[key])) {
						config.data[key].forEach((value) => {
							body.append(key, value);
						});
					}
					else {
						body.set(key, config.data[key]);
					}
				});

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						body,
						method: 'POST',
					}
				)
					.then((response) => {
						if (config.dataType === 'json') {
							return response.json();
						}
						else {
							return response.text();
						}
					})
					.then((data) => {
						if (config.dataType === 'json') {
							if (
								Lang.isString(data) &&
								data.indexOf(TOKEN_SERIALIZE) === 0
							) {
								try {
									data = JSON.parse(
										data.substring(TOKEN_SERIALIZE.length)
									);
								}
								catch (error) {}
							}
						}

						if (typeof config.callback === 'function') {
							config.callback(data);
						}
					});
			},

			_setValues(data) {
				const instance = this;

				instance._ioRequest({
					data,
				});
			},

			get(key, callback) {
				const instance = this;

				instance._getValues('get', key, callback);
			},

			getAll(keys, callback) {
				const instance = this;

				instance._getValues('getAll', keys, callback);
			},

			set(key, value) {
				const instance = this;

				const object = {};

				if (isObject(value)) {
					value = TOKEN_SERIALIZE + JSON.stringify(value);
				}

				object[key] = value;

				instance._setValues(object);
			},

			setAll(object) {
				const instance = this;

				instance._setValues(object);
			},
		});

		Liferay.Store = Store;
	},
	''
);
