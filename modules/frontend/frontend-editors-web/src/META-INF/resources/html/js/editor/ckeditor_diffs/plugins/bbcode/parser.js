;(function() {
	var hasOwnProperty = Object.prototype.hasOwnProperty;

	var isString = function(val) {
		return (typeof val == 'string');
	};

	var ELEMENTS_BLOCK = {
		'*': 1,
		'center': 1,
		'code': 1,
		'justify': 1,
		'left': 1,
		'li': 1,
		'list': 1,
		'q': 1,
		'quote': 1,
		'right': 1,
		'table': 1,
		'td': 1,
		'th': 1,
		'tr': 1
	};

	var ELEMENTS_CLOSE_SELF = {
		'*': 1
	};

	var ELEMENTS_INLINE = {
		'b': 1,
		'color': 1,
		'font': 1,
		'i': 1,
		'img': 1,
		's': 1,
		'size': 1,
		'u': 1,
		'url': 1
	};

	var STR_TAG_CODE = 'code';

	var Parser = function(config) {
		var instance = this;

		config = config || {};

		instance._config = config;

		instance.init();
	};

	Parser.prototype = {
		constructor: Parser,

		init: function() {
			var instance = this;

			var stack = [];

			stack.last = stack.last || function() {
				var instance = this;

				return instance[instance.length - 1];
			};

			instance._result = [];

			instance._stack = stack;

			instance._dataPointer = 0;
		},

		parse: function(data) {
			var instance = this;

			var lexer = new Liferay.BBCodeLexer(data);

			instance._lexer = lexer;

			var token;

			while ((token = lexer.getNextToken())) {
				instance._handleData(token, data);

				if (token[1]) {
					instance._handleTagStart(token);

					if (token[1].toLowerCase() == STR_TAG_CODE) {
						while ((token = lexer.getNextToken()) && token[3] != STR_TAG_CODE);

						instance._handleData(token, data);

						if (token) {
							instance._handleTagEnd(token);
						}
						else {
							break;
						}
					}
				}
				else {
					instance._handleTagEnd(token);
				}
			}

			instance._handleData(null, data);

			instance._handleTagEnd();

			var result = instance._result.slice(0);

			instance._reset();

			return result;
		},

		_handleData: function(token, data) {
			var instance = this;

			var length = data.length;

			var lastIndex = length;

			if (token) {
				length = token.index;

				lastIndex = instance._lexer.getLastIndex();
			}

			if (length > instance._dataPointer) {
				instance._result.push(
					{
						type: Parser.TOKEN_DATA,
						value: data.substring(instance._dataPointer, length)
					}
				);
			}

			instance._dataPointer = lastIndex;
		},

		_handleTagEnd: function(token) {
			var instance = this;

			var pos = 0;

			var stack = instance._stack;

			if (token) {
				var tagName;

				if (isString(token)) {
					tagName = token;
				}
				else {
					tagName = token[3];
				}

				tagName = tagName.toLowerCase();

				for (pos = stack.length - 1; pos >= 0; pos--) {
					if (stack[pos] == tagName) {
						break;
					}
				}
			}

			if (pos >= 0) {
				var tokenTagEnd = Parser.TOKEN_TAG_END;

				for (var i = stack.length - 1; i >= pos; i--) {
					instance._result.push(
						{
							type: tokenTagEnd,
							value: stack[i]
						}
					);
				}

				stack.length = pos;
			}
		},

		_handleTagStart: function(token) {
			var instance = this;

			var tagName = token[1].toLowerCase();

			var stack = instance._stack;

			if (hasOwnProperty.call(ELEMENTS_BLOCK, tagName)) {
				var lastTag;

				while ((lastTag = stack.last()) && hasOwnProperty.call(ELEMENTS_INLINE, lastTag)) {
					instance._handleTagEnd(lastTag);
				}
			}

			if (hasOwnProperty.call(ELEMENTS_CLOSE_SELF, tagName) && stack.last() == tagName) {
				instance._handleTagEnd(tagName);
			}

			stack.push(tagName);

			instance._result.push(
				{
					attribute: token[2],
					type: Parser.TOKEN_TAG_START,
					value: tagName
				}
			);
		},

		_reset: function() {
			var instance = this;

			instance._stack.length = 0;
			instance._result.length = 0;

			instance._dataPointer = 0;
		}
	};

	Parser.TOKEN_DATA = 4;
	Parser.TOKEN_TAG_END = 2;
	Parser.TOKEN_TAG_START = 1;

	Liferay.BBCodeParser = Parser;
})();