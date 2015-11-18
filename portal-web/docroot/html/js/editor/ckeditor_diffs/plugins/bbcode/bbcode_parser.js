;(function() {
	var A = AUI();

	var LiferayUtil = Liferay.Util;

	var entities = A.merge(
		LiferayUtil.MAP_HTML_CHARS_ESCAPED,
		{
			'[': '&#91;',
			']': '&#93;',
			'(': '&#40;',
			')': '&#41;'
		}
	);

	var BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', LiferayUtil, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', LiferayUtil, entities);
}());;(function() {
	var REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'<>\[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/ig;

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
})();;(function() {
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
})();;(function() {
	var BBCodeUtil = Liferay.BBCodeUtil;
	var Util = Liferay.Util;
	var CKTools = CKEDITOR.tools;

	var Parser = Liferay.BBCodeParser;

	var hasOwnProperty = Object.prototype.hasOwnProperty;

	var MAP_FONT_SIZE = {
		1: 10,
		2: 12,
		3: 16,
		4: 18,
		5: 24,
		6: 32,
		7: 48,
		defaultSize: 12
	};

	var MAP_HANDLERS = {
		b: '_handleStrong',
		code: '_handleCode',
		email: '_handleEmail',
		font: '_handleFont',
		i: '_handleEm',
		img: '_handleImage',
		list: '_handleList',
		s: '_handleStrikeThrough',
		size: '_handleSize',
		table: '_handleTable',
		td: '_handleTableCell',
		th: '_handleTableHeader',
		tr: '_handleTableRow',
		url: '_handleURL',

		color: '_handleColor',
		colour: '_handleColor',

		'*': '_handleListItem',
		li: '_handleListItem',

		q: '_handleQuote',
		quote: '_handleQuote',

		center: '_handleTextAlign',
		justify: '_handleTextAlign',
		left: '_handleTextAlign',
		right: '_handleTextAlign'
	};

	var MAP_IMAGE_ATTRIBUTES = {
		'alt': 1,
		'class': 1,
		'dir': 1,
		'height': 1,
		'id': 1,
		'lang': 1,
		'longdesc': 1,
		'style': 1,
		'title': 1,
		'width': 1
	};

	var MAP_LIST_STYLES = {
		1: 'list-style-type: decimal;',
		a: 'list-style-type: lower-alpha;'
	};

	var MAP_TOKENS_EXCLUDE_NEW_LINE = {
		'*': 3,
		li: 3,
		tr: 3,
		td: 3,
		th: 3,
		table: 2
	};

	var REGEX_ATTRS = /\s*([^=]+)\s*=\s*"([^"]+)"\s*/g;

	var REGEX_COLOR = /^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$/i;

	var REGEX_ESCAPE_REGEX = /[-[\]{}()*+?.,\\^$|#\s]/g;

	var REGEX_IMAGE_SRC = /^(?:https?:\/\/|\/)[-;\/\?:@&=\+\$,_\.!~\*'\(\)%0-9a-z]{1,512}$/i;

	var REGEX_LASTCHAR_NEWLINE = /\r?\n$/;

	var REGEX_NEW_LINE = /\r?\n/g;

	var REGEX_NUMBER = /^[\\.0-9]{1,8}$/;

	var REGEX_STRING_IS_NEW_LINE = /^\r?\n$/;

	var REGEX_TAG_NAME = /^\/?(?:b|center|code|colou?r|email|i|img|justify|left|pre|q|quote|right|\*|s|size|table|tr|th|td|li|list|font|u|url)$/i;

	var REGEX_URI = /^[-;\/\?:@&=\+\$,_\.!~\*'\(\)%0-9a-z#]{1,512}$|\${\S+}/i;

	var STR_BLANK = '';

	var STR_CODE = 'code';

	var STR_EMAIL = 'email';

	var STR_IMG = 'img';

	var STR_MAILTO = 'mailto:';

	var STR_NEW_LINE = '\n';

	var STR_TAG_ATTR_CLOSE = '">';

	var STR_TAG_ATTR_HREF_OPEN = '<a href="';

	var STR_TAG_A_CLOSE = '</a>';

	var STR_TAG_END_CLOSE = '>';

	var STR_TAG_END_OPEN = '</';

	var STR_TAG_LIST_ITEM_SHORT = '*';

	var STR_TAG_OPEN = '<';

	var STR_TAG_P_CLOSE = '</p>';

	var STR_TAG_SPAN_CLOSE = '</span>';

	var STR_TAG_SPAN_STYLE_OPEN = '<span style="';

	var STR_TAG_URL = 'url';

	var STR_TEXT_ALIGN = '<p style="text-align: ';

	var TOKEN_DATA = Parser.TOKEN_DATA;

	var TOKEN_TAG_END = Parser.TOKEN_TAG_END;

	var TOKEN_TAG_START = Parser.TOKEN_TAG_START;

	var tplImage = new CKEDITOR.template('<img src="{imageSrc}" {attributes} />');

	var Converter = function(config) {
		var instance = this;

		config = config || {};

		instance.init(config);

		instance._config = config;
	};

	Converter.prototype = {
		constructor: Converter,

		init: function(config) {
			var instance = this;

			instance._parser = new Parser(config.parser);

			instance._config = config;

			instance._result = [];
			instance._stack = [];
		},

		convert: function(data) {
			var instance = this;

			var parsedData = instance._parser.parse(data);

			instance._parsedData = parsedData;

			var length = parsedData.length;

			for (instance._tokenPointer = 0; instance._tokenPointer < length; instance._tokenPointer++) {
				var token = parsedData[instance._tokenPointer];

				var type = token.type;

				if (type == TOKEN_TAG_START) {
					instance._handleTagStart(token);
				}
				else if (type == TOKEN_TAG_END) {
					instance._handleTagEnd(token);
				}
				else if (type == TOKEN_DATA) {
					instance._handleData(token);
				}
				else {
					throw 'Internal error. Invalid token type';
				}
			}

			var result = instance._result.join(STR_BLANK);

			instance._reset();

			return result;
		},

		_escapeHTML: Util.escapeHTML,

		_extractData: function(toTagName, consume) {
			var instance = this;

			var result = [];

			var index = instance._tokenPointer + 1;

			var token;

			do {
				token = instance._parsedData[index++];

				if (token.type == TOKEN_DATA) {
					result.push(token.value);
				}

			}
			while ((token.type != TOKEN_TAG_END) && (token.value != toTagName));

			if (consume) {
				instance._tokenPointer = index - 1;
			}

			return result.join(STR_BLANK);
		},

		_getFontSize: function(fontSize) {
			return MAP_FONT_SIZE[fontSize] || MAP_FONT_SIZE.defaultSize;
		},

		_handleCode: function(token) {
			var instance = this;

			instance._noParse = true;

			instance._handleSimpleTag('pre');

			instance._result.push(STR_NEW_LINE);
		},

		_handleColor: function(token) {
			var instance = this;

			var colorName = token.attribute;

			if (!colorName || !REGEX_COLOR.test(colorName)) {
				colorName = 'inherit';
			}

			instance._result.push(STR_TAG_SPAN_STYLE_OPEN + 'color: ' + colorName + STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleData: function(token) {
			var instance = this;

			var emoticonImages = instance._config.emoticonImages;
			var emoticonPath = instance._config.emoticonPath;
			var emoticonSymbols = instance._config.emoticonSymbols;

			var value = instance._escapeHTML(token.value);

			value = instance._handleNewLine(value);

			if (!instance._noParse) {
				var length = emoticonSymbols.length;

				for (var i = 0; i < length; i++) {
					var image = tplImage.output(
						{
							imageSrc: emoticonPath + emoticonImages[i]
						}
					);

					var escapedSymbol = emoticonSymbols[i].replace(REGEX_ESCAPE_REGEX, '\\$&');

					value = value.replace(new RegExp(escapedSymbol, 'g'), image);
				}
			}

			instance._result.push(value);
		},

		_handleEm: function(token) {
			var instance = this;

			instance._handleSimpleTag('em');
		},

		_handleEmail: function(token) {
			var instance = this;

			var href = STR_BLANK;

			var hrefInput = token.attribute || instance._extractData(STR_EMAIL, false);

			if (REGEX_URI.test(hrefInput)) {
				if (hrefInput.indexOf(STR_MAILTO) !== 0) {
					hrefInput = STR_MAILTO + hrefInput;
				}

				href = CKTools.htmlEncodeAttr(hrefInput);
			}

			instance._result.push(STR_TAG_ATTR_HREF_OPEN + href + STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_A_CLOSE);
		},

		_handleFont: function(token) {
			var instance = this;

			var fontName = token.attribute;

			fontName = CKTools.htmlEncodeAttr(fontName);

			instance._result.push(STR_TAG_SPAN_STYLE_OPEN + 'font-family: ' + fontName + STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleImage: function(token) {
			var instance = this;

			var imageSrc = STR_BLANK;

			var imageSrcInput = instance._extractData(STR_IMG, true);

			if (REGEX_IMAGE_SRC.test(imageSrcInput)) {
				imageSrc = CKTools.htmlEncodeAttr(imageSrcInput);
			}

			var result = tplImage.output(
				{
					attributes: instance._handleImageAttributes(token, token.value),
					imageSrc: imageSrc
				}
			);

			instance._result.push(result);
		},

		_handleImageAttributes: function(token) {
			var instance = this;

			var attrs = '';

			if (token.attribute) {
				var bbCodeAttr;

				while ((bbCodeAttr = REGEX_ATTRS.exec(token.attribute))) {
					var attrName = bbCodeAttr[1];

					if (MAP_IMAGE_ATTRIBUTES[attrName]) {
						var attrValue = bbCodeAttr[2];

						if (attrValue) {
							attrs += ' ' + attrName + '="' + instance._escapeHTML(attrValue) + '"';
						}
					}
				}
			}

			return attrs;
		},

		_handleList: function(token) {
			var instance = this;

			var tag = 'ul';
			var styleAttr;

			var listAttribute = token.attribute;

			if (listAttribute) {
				tag = 'ol';

				styleAttr = MAP_LIST_STYLES[listAttribute];
			}

			var result = STR_TAG_OPEN + tag + STR_TAG_END_CLOSE;

			if (styleAttr) {
				result = STR_TAG_OPEN + tag + ' style="' + styleAttr + STR_TAG_ATTR_CLOSE;
			}

			instance._result.push(result);

			instance._stack.push(STR_TAG_END_OPEN + tag + STR_TAG_END_CLOSE);
		},

		_handleListItem: function(token) {
			var instance = this;

			instance._handleSimpleTag('li');
		},

		_handleNewLine: function(value) {
			var instance = this;

			var nextToken;

			if (!instance._noParse) {
				if (REGEX_STRING_IS_NEW_LINE.test(value)) {
					nextToken = instance._parsedData[instance._tokenPointer + 1];

					if (nextToken &&
						hasOwnProperty.call(MAP_TOKENS_EXCLUDE_NEW_LINE, nextToken.value) &&
						(nextToken.type & MAP_TOKENS_EXCLUDE_NEW_LINE[nextToken.value])) {

							value = STR_BLANK;
					}
				}
				else if (REGEX_LASTCHAR_NEWLINE.test(value)) {
					nextToken = instance._parsedData[instance._tokenPointer + 1];

					if (nextToken &&
						(nextToken.type == TOKEN_TAG_END) &&
						(nextToken.value == STR_TAG_LIST_ITEM_SHORT)) {

						value = value.substring(0, value.length - 1);
					}
				}

				if (value) {
					value = value.replace(
						REGEX_NEW_LINE,
						'<br>'
					);
				}
			}

			return value;
		},

		_handleQuote: function(token) {
			var instance = this;

			var cite = token.attribute;

			var result = '<blockquote><p>';

			if (cite && cite.length) {
				cite = BBCodeUtil.escape(cite);

				result += '<cite>' + cite + '</cite>';
			}

			instance._result.push(result);

			instance._stack.push('</p></blockquote>');
		},

		_handleSimpleTag: function(tagName) {
			var instance = this;

			instance._result.push(STR_TAG_OPEN, tagName, STR_TAG_END_CLOSE);

			instance._stack.push(STR_TAG_END_OPEN + tagName + STR_TAG_END_CLOSE);
		},

		_handleSimpleTags: function(token) {
			var instance = this;

			instance._handleSimpleTag(token.value);
		},

		_handleSize: function(token) {
			var instance = this;

			var size = token.attribute;

			if (!size || !REGEX_NUMBER.test(size)) {
				size = '1';
			}

			instance._result.push(STR_TAG_SPAN_STYLE_OPEN, 'font-size: ', instance._getFontSize(size), 'px', STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleStrikeThrough: function(token) {
			var instance = this;

			instance._handleSimpleTag('strike');
		},

		_handleStrong: function(token) {
			var instance = this;

			instance._handleSimpleTag('strong');
		},

		_handleTable: function(token) {
			var instance = this;

			instance._handleSimpleTag('table');
		},

		_handleTableCell: function(token) {
			var instance = this;

			instance._handleSimpleTag('td');
		},

		_handleTableHeader: function(token) {
			var instance = this;

			instance._handleSimpleTag('th');
		},

		_handleTableRow: function(token) {
			var instance = this;

			instance._handleSimpleTag('tr');
		},

		_handleTagEnd: function(token) {
			var instance = this;

			var tagName = token.value;

			if (instance._isValidTag(tagName)) {
				instance._result.push(instance._stack.pop());

				if (tagName == STR_CODE) {
					instance._noParse = false;
				}
			}
		},

		_handleTagStart: function(token) {
			var instance = this;

			var tagName = token.value;

			if (instance._isValidTag(tagName)) {
				var handlerName = MAP_HANDLERS[tagName] || '_handleSimpleTags';

				instance[handlerName](token);
			}
		},

		_handleTextAlign: function(token) {
			var instance = this;

			instance._result.push(STR_TEXT_ALIGN, token.value, STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_P_CLOSE);
		},

		_handleURL: function(token) {
			var instance = this;

			var href = STR_BLANK;

			var hrefInput = token.attribute || instance._extractData(STR_TAG_URL, false);

			if (REGEX_URI.test(hrefInput)) {
				href = CKTools.htmlEncodeAttr(hrefInput);
			}

			instance._result.push(STR_TAG_ATTR_HREF_OPEN + href + STR_TAG_ATTR_CLOSE);

			instance._stack.push(STR_TAG_A_CLOSE);
		},

		_isValidTag: function(tagName) {
			var valid = false;

			if (tagName && tagName.length) {
				valid = REGEX_TAG_NAME.test(tagName);
			}

			return valid;
		},

		_reset: function() {
			var instance = this;

			instance._result.length = 0;
			instance._stack.length = 0;

			instance._parsedData = null;

			instance._noParse = false;
		}
	};

	CKEDITOR.BBCode2HTML = Converter;
})();