AUI().add(
	'liferay-history',
	function(A) {
		var Lang = A.Lang;

		var AObject = A.Object;

		var HistoryBase = A.HistoryBase;

		var QueryString = A.QueryString;

		var isEmpty = AObject.isEmpty;

		var isValue = Lang.isValue;

		var owns = AObject.owns;

		var WIN = A.config.win;

		var HISTORY = WIN.history;

		var HTML5 = HistoryBase.html5;

		var LOCATION = WIN.location;

		var History = A.Component.create(
			{
				EXTENDS: A.History,

				NAME: 'liferayhistory',

				prototype: {
					get: function(key) {
						var instance = this;

						var value = History.superclass.get.apply(this, arguments);

						if (!isValue(value) && isValue(key)) {
							var query = LOCATION.search;

							var queryMap = instance._parse(query.substr(1));

							if (owns(queryMap, key)) {
								value = queryMap[key];
							}
						}

						return value;
					},

					_parse: A.cached(
						function(str) {
							return QueryString.parse(str);
						}
					)
				}
			}
		);

		if (HTML5) {
			History.prototype.add = function(state, options) {
				var instance = this;

				options = options || {};

				options.url = options.url || instance._updateURI(state);

				return History.superclass.add.call(instance, state, options);
			};

			History.prototype._init = function(config) {
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
			};

			History.prototype._updateURI = function(state) {
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
			};
		}

		var HistoryManager = new History();

		HistoryManager.SRC_ADD = HistoryBase.SRC_ADD;
		HistoryManager.SRC_REPLACE = HistoryBase.SRC_REPLACE;

		HistoryManager.SRC_HASH = A.HistoryHash ? A.HistoryHash.SRC_HASH : 'hash';
		HistoryManager.SRC_POPSTATE = A.HistoryHTML5 ? A.HistoryHTML5.SRC_POPSTATE : 'popstate';

		HistoryManager.HTML5 = HTML5;

		Liferay.History = History;
		Liferay.HistoryManager = HistoryManager;
	},
	'',
	{
		requires: ['history', 'querystring']
	}
);