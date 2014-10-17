;(function(_) {
	var REGEX_SUB = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;

	_.mixin(
		{
			sub: function(string, data) {
				if (arguments.length > 2 || !_.isObject(data)) {
					data = _.toArray(arguments).slice(1);
				}

				return string.replace ? string.replace(
					REGEX_SUB,
					function (match, key) {
						return _.isUndefined(data[key]) ? match : data[key];
					}
				) : string;
			}
		}
	);
})(AUI._);