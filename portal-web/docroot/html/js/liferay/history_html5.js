AUI.add(
	'liferay-history-html5',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.History;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var isEmpty = AObject.isEmpty;
		var isValue = Lang.isValue;
		var owns = AObject.owns;

		var WIN = A.config.win;

		var HISTORY = WIN.history;

		var LOCATION = WIN.location;

		A.mix(
			History.prototype,
			{
				PROTECTED_HASH_KEYS: [ /^liferay$/, /^tab$/, /^_\d+_tab$/ ],

				add: function(state, options) {
					var instance = this;

					options = options || {};

					options.url = options.url || instance._updateURI(state);

					state.liferay = true;

					return History.superclass.add.call(instance, state, options);
				},

				_init: function(config) {
					var instance = this;

					var hash = LOCATION.hash;

					var locationHashValid = (hash.indexOf(History.VALUE_SEPARATOR) != -1);

					if (locationHashValid) {
						HISTORY.replaceState(null, null, instance._updateURI());
					}

					config = config || {};

					if (!owns(config, 'initialState')) {
						if (locationHashValid) {
							config.initialState = instance._parse(hash.substr(1));
						}

						History.superclass._init.call(instance, config);
					}
				},

				_updateURI: function(state) {
					var instance = this;

					var uriData = [
						LOCATION.search.substr(1),
						LOCATION.hash.substr(1)
					];

					var hash = uriData[1];
					var query = uriData[0];

					var queryMap = {};

					if (query) {
						queryMap = instance._parse(query);
					}

					if (!state && hash) {
						var hashMap = instance._parse(hash);

						if (!isEmpty(hashMap)) {
							var protectedHashMap = {};

							state = hashMap;

							A.each(
								state,
								function(value1, key1) {
									A.Array.each(
										instance.PROTECTED_HASH_KEYS,
										function(value2, key2) {
											if (value2.test(key1)) {
												delete state[key1];
												protectedHashMap[key1] = value1;
											}
										}
									);
								}
							);

							uriData.pop();

							uriData.push('#', QueryString.stringify(protectedHashMap));
						}
					}

					A.mix(queryMap, state, true);

					AObject.each(
						queryMap,
						function(item, index) {
							if (!isValue(item)) {
								delete queryMap[index];
							}
						}
					);

					uriData[0] = QueryString.stringify(
						queryMap,
						{
							eq: History.VALUE_SEPARATOR,
							sep: History.PAIR_SEPARATOR
						}
					);

					uriData.unshift(LOCATION.protocol, '//', LOCATION.host, LOCATION.pathname, '?');

					return uriData.join('');
				}
			},
			true
		);
	},
	'',
	{
		requires: ['liferay-history', 'history-html5', 'querystring-stringify-simple']
	}
);