;(function(_) {
	_.mixin(
		{
			sub: function(string, data) {
				return string.replace ? string.replace(
					/\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g,
					function (match, key) {
						return _.isUndefined(data[key]) ? match : data[key];
					}
				) : string;
			}
		}
	);
})(_);