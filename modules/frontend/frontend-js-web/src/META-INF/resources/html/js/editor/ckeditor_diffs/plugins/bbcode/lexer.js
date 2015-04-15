;(function() {
	var REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'\(\)<>\[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/ig;

	var Lexer = function(data) {
		var instance = this;

		instance._data = data;
	};

	Lexer.prototype = {
		constructor: Lexer,

		getLastIndex: function() {
			return REGEX_BBCODE.lastIndex;
		},

		getNextToken: function() {
			var instance = this;

			return REGEX_BBCODE.exec(instance._data);
		}
	};

	Liferay.BBCodeLexer = Lexer;
})();