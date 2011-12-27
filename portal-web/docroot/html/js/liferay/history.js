AUI.add(
	'liferay-history',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var isValue = Lang.isValue;
		var owns = AObject.owns;

		var WIN = A.config.win;

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
							return QueryString.parse(str, History.PAIR_SEPARATOR, History.VALUE_SEPARATOR);
						}
					)
				},

				PAIR_SEPARATOR: '&',

				VALUE_SEPARATOR: '='
			}
		);

		Liferay.History = History;
	},
	'',
	{
		requires: ['querystring-parse-simple']
	}
);