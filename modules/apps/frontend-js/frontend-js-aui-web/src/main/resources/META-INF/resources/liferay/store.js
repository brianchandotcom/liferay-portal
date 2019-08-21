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

AUI.add(
	'liferay-store',
	function(A) {
		var Lang = A.Lang;

		var isObject = Lang.isObject;

		var TOKEN_SERIALIZE = 'serialize://';

		var Store = function(key, value) {
			var method;

			if (Lang.isFunction(value)) {
				method = 'get';

				if (Array.isArray(key)) {
					method = 'getAll';
				}
			} else {
				method = 'set';

				if (isObject(key)) {
					method = 'setAll';
				} else if (arguments.length == 1) {
					method = null;
				}
			}

			if (method) {
				Store[method].apply(Store, arguments);
			}
		};

		A.mix(Store, {
			get: function(key, callback) {
				var instance = this;

				instance._getValues('get', key, callback);
			},

			getAll: function(keys, callback) {
				var instance = this;

				instance._getValues('getAll', keys, callback);
			},

			set: function(key, value) {
				var instance = this;

				var obj = {};

				if (isObject(value)) {
					value = TOKEN_SERIALIZE + JSON.stringify(value);
				}

				obj[key] = value;

				instance._setValues(obj);
			},

			setAll: function(obj) {
				var instance = this;

				instance._setValues(obj);
			},

			_getValues: function(cmd, key, callback) {
				var instance = this;

				var config = {
					callback,
					data: {
						cmd,
						key
					}
				};

				if (cmd == 'getAll') {
					config.dataType = 'json';
				}

				instance._ioRequest(config);
			},

			_ioRequest: function(config) {
				var instance = this;

				config.data.p_auth = Liferay.authToken;

				var doAsUserIdEncoded = themeDisplay.getDoAsUserIdEncoded();

				if (doAsUserIdEncoded) {
					config.data.doAsUserId = doAsUserIdEncoded;
				}

				const data = new URLSearchParams();

				Object.keys(config.data).forEach(key => {
					data.append(key, config.data[key]);
				});

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						body: data,
						headers: {
							'Content-Type':
								'application/x-www-form-urlencoded; charset=UTF-8'
						},
						method: 'POST'
					}
				)
					.then(response => {
						if (config.dataType === 'json') {
							return response.json();
						} else {
							return response.text();
						}
					})
					.then(data => {
						if (config.dataType === 'json') {
							if (
								Lang.isString(responseData) &&
								responseData.indexOf(TOKEN_SERIALIZE) === 0
							) {
								try {
									responseData = JSON.parse(
										responseData.substring(
											TOKEN_SERIALIZE.length
										)
									);
								} catch (e) {}
							}
						}

						if (Liferay.Util.isFunction(config.callback)) {
							config.callback(data);
						}
					});
			},

			_setValues: function(data) {
				var instance = this;

				instance._ioRequest({
					data: data
				});
			}
		});

		Liferay.Store = Store;
	},
	'',
	{
		requires: ['aui-io-request']
	}
);
