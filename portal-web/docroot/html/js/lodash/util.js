;(function(_) {
	var REGEX_SUB = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;

	_.mixin(
		{
			sub: function(string, data) {
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