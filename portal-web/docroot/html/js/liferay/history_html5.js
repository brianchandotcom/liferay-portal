AUI().add(
	'liferay-history-html5',
	function(A) {
		var Lang = A.Lang;

		var AObject = A.Object;

		var QueryString = A.QueryString;

		var isEmpty = AObject.isEmpty;

		var isValue = Lang.isValue;

		var WIN = A.config.win;

		var HISTORY = WIN.history;

		var History = Liferay.History;

		var owns = AObject.owns;

		var LOCATION = WIN.location;

		A.mix(
			History.prototype,
			{
				add: function(state, options) {
					var instance = this;

					options = options || {};

					options.url = options.url || instance._updateURI(state);

					return History.superclass.add.call(instance, state, options);
				},

				_init: function(config) {
					var instance = this;

					if (LOCATION.hash) {
						HISTORY.replaceState(null, null, instance._updateURI());
					}

					config = config || {};

					if (!owns(config, 'initialState')) {
						var bookmarkedState = HISTORY && HISTORY.state;

						var initialState = instance._parse(LOCATION.hash.substr(1));

						if (bookmarkedState) {
							initialState = A.merge(initialState, bookmarkedState);
						}

						if (!isEmpty(initialState)) {
							config.initialState = initialState;
						}

						History.superclass._init.apply(instance, arguments);
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

					var queryMap = instance._parse(query);

					if (!state && hash) {
						var hashMap = instance._parse(hash);

						if (!isEmpty(hashMap)) {
							state = hashMap;

							uriData.pop();
						}
					}

					A.mix(queryMap, state, true);

					AObject.each(
						queryMap,
						function(item, index, collection) {
							if (!isValue(item)) {
								delete queryMap[index];
							}
						}
					);

					uriData[0] = QueryString.stringify(queryMap);

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