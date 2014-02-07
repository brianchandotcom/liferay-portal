AUI.add(
	'liferay-store',
	function(A) {
		var Lang = A.Lang;
		var isObject = Lang.isObject;

		var TOKEN_SERIALIZE = 'serialize://';

		var Store = function(key, value) {
			var instance = Store;

			var method;

			if (Lang.isFunction(value)) {
				method = 'get';

				if (Lang.isArray(key)) {
					method = 'getAll';
				}
			}
			else {
				method = 'set';

				if (isObject(key)) {
					method = 'setAll';
				}
				else if (arguments.length == 1) {
					method = null;
				}
			}

			if (method) {
				instance[method].apply(Store, arguments);
			}
		};

		A.mix(
			Store,
			{
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
						value = TOKEN_SERIALIZE + A.JSON.stringify(value);
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
						after: {
							success: function(event) {
								var responseData = this.get('responseData');

								if (Lang.isString(responseData) && responseData.indexOf(TOKEN_SERIALIZE) === 0) {
									try {
										responseData = A.JSON.parse(responseData.substring(TOKEN_SERIALIZE.length));
									}
									catch (e) {
									}
								}

								callback(responseData);
							}
						},
						data: {
							cmd: cmd
						}
					};

					config.data.key = key;

					if (cmd == 'getAll') {
						config.dataType = 'json';
					}

					instance._ioRequest(config);
				},

				_ioRequest: function(config) {
					var instance = this;

					config.data.p_auth = Liferay.authToken;

					A.io.request(
						themeDisplay.getPathMain() + '/portal/session_click',
						config
					);
				},

				_setValues: function(data) {
					var instance = this;

					instance._ioRequest(
						{
							data: data
						}
					);
				}
			}
		);

		Liferay.Store = Store;
	},
	'',
	{
		requires: ['aui-io-request']
	}
);