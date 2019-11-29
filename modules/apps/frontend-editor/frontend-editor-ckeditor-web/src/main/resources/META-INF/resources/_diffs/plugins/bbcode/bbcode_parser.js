/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function() {
	var A = AUI();

	var LString = A.Lang.String;

	var entities = A.merge(Liferay.Util.MAP_HTML_CHARS_ESCAPED, {
		'(': '&#40;',
		')': '&#41;',
		'[': '&#91;',
		']': '&#93;'
	});

	var BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', LString, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', LString, entities);
})();
(function() {
	// eslint-disable-next-line no-control-regex
	var REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'<>[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/gi;

	var Lexer = function(data) {
		var instance = this;

		instance._data = data;
	};

	Lexer.prototype = {
		constructor: Lexer,

		getLastIndex() {
			return REGEX_BBCODE.lastIndex;
		},

		getNextToken() {
			var instance = this;

			return REGEX_BBCODE.exec(instance._data);
		}
	};

	Liferay.BBCodeLexer = Lexer;
})();
(function() {
	var hasOwnProperty = Object.prototype.hasOwnProperty;

	var isString = function(val) {
		return typeof val == 'string';
	};

	var ELEMENTS_BLOCK = {
		'*': 1,
		center: 1,
		code: 1,
		justify: 1,
		left: 1,
		li: 1,
		list: 1,
		q: 1,
		quote: 1,
		right: 1,
		table: 1,
		td: 1,
		th: 1,
		tr: 1
	};

	var ELEMENTS_CLOSE_SELF = {
		'*': 1
	};

	var ELEMENTS_INLINE = {
		b: 1,
		color: 1,
		font: 1,
		i: 1,
		img: 1,
		s: 1,
		size: 1,
		u: 1,
		url: 1
	};

	var REGEX_TAG_NAME = /^\/?(?:b|center|code|colou?r|email|i|img|justify|left|pre|q|quote|right|\*|s|size|table|tr|th|td|li|list|font|u|url)$/i;

	var STR_TAG_CODE = 'code';

	var Parser = function(config) {
		var instance = this;

		config = config || {};

		instance._config = config;

		instance.init();
	};

	Parser.prototype = {
		_handleData(token, data) {
			var instance = this;

			var length = data.length;

			var lastIndex = length;

			if (token) {
				lastIndex = instance._lexer.getLastIndex();

				length = lastIndex;

				var tokenItem = token[1] || token[3];

				if (instance._isValidTag(tokenItem)) {
					length = token.index;
				}
			}

			if (length > instance._dataPointer) {
				instance._result.push({
					type: Parser.TOKEN_DATA,
					value: data.substring(instance._dataPointer, length)
				});
			}

			instance._dataPointer = lastIndex;
		},

		_handleTagEnd(token) {
			var instance = this;

			var pos = 0;

			var stack = instance._stack;

			var tagName;

			if (token) {
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
					instance._result.push({
						type: tokenTagEnd,
						value: stack[i]
					});
				}

				stack.length = pos;
			}
		},

		_handleTagStart(token) {
			var instance = this;

			var tagName = token[1].toLowerCase();

			if (instance._isValidTag(tagName)) {
				var stack = instance._stack;

				if (hasOwnProperty.call(ELEMENTS_BLOCK, tagName)) {
					var lastTag;

					while (
						(lastTag = stack.last()) &&
						hasOwnProperty.call(ELEMENTS_INLINE, lastTag)
					) {
						instance._handleTagEnd(lastTag);
					}
				}

				if (
					hasOwnProperty.call(ELEMENTS_CLOSE_SELF, tagName) &&
					stack.last() == tagName
				) {
					instance._handleTagEnd(tagName);
				}

				stack.push(tagName);

				instance._result.push({
					attribute: token[2],
					type: Parser.TOKEN_TAG_START,
					value: tagName
				});
			}
		},

		_isValidTag(tagName) {
			var valid = false;

			if (tagName && tagName.length) {
				valid = REGEX_TAG_NAME.test(tagName);
			}

			return valid;
		},

		_reset() {
			var instance = this;

			instance._stack.length = 0;
			instance._result.length = 0;

			instance._dataPointer = 0;
		},

		constructor: Parser,

		init() {
			var instance = this;

			var stack = [];

			stack.last =
				stack.last ||
				function() {
					var instance = this;

					return instance[instance.length - 1];
				};

			instance._result = [];

			instance._stack = stack;

			instance._dataPointer = 0;
		},

		parse(data) {
			var instance = this;

			var lexer = new Liferay.BBCodeLexer(data);

			instance._lexer = lexer;

			var token;

			while ((token = lexer.getNextToken())) {
				instance._handleData(token, data);

				if (token[1]) {
					instance._handleTagStart(token);

					if (token[1].toLowerCase() == STR_TAG_CODE) {
						while (
							(token = lexer.getNextToken()) &&
							token[3] != STR_TAG_CODE
						);

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
		}
	};

	Parser.TOKEN_DATA = 4;
	Parser.TOKEN_TAG_END = 2;
	Parser.TOKEN_TAG_START = 1;

	Liferay.BBCodeParser = Parser;
})();
(function() {
	var A = AUI();

	var BBCodeUtil = Liferay.BBCodeUtil;
	var CKTools = CKEDITOR.tools;

	var Parser = Liferay.BBCodeParser;

	var hasOwnProperty = Object.prototype.hasOwnProperty;

	var MAP_FONT_SIZE = {
		1: 10,
		2: 12,
		3: 14,
		4: 16,
		5: 18,
		6: 24,
		7: 32,
		8: 48,
		defaultSize: 14
	};

	var MAP_HANDLERS = {
		'*': '_handleListItem',
		b: '_handleStrong',
		center: '_handleTextAlign',
		code: '_handleCode',
		color: '_handleColor',
		colour: '_handleColor',
		email: '_handleEmail',
		font: '_handleFont',
		i: '_handleEm',
		img: '_handleImage',
		justify: '_handleTextAlign',
		left: '_handleTextAlign',
		li: '_handleListItem',
		list: '_handleList',
		q: '_handleQuote',
		quote: '_handleQuote',
		right: '_handleTextAlign',
		s: '_handleStrikeThrough',
		size: '_handleSize',
		table: '_handleTable',
		td: '_handleTableCell',
		th: '_handleTableHeader',
		tr: '_handleTableRow',
		url: '_handleURL'
	};

	var MAP_IMAGE_ATTRIBUTES = {
		alt: 1,
		class: 1,
		'data-image-id': 1,
		dir: 1,
		height: 1,
		id: 1,
		lang: 1,
		longdesc: 1,
		style: 1,
		title: 1,
		width: 1
	};

	var MAP_ORDERED_LIST_STYLES = {
		1: 'list-style-type: decimal;',
		A: 'list-style-type: upper-alpha;',
		I: 'list-style-type: upper-roman;',
		a: 'list-style-type: lower-alpha;',
		i: 'list-style-type: lower-roman;'
	};

	var MAP_TOKENS_EXCLUDE_NEW_LINE = {
		'*': 3,
		li: 3,
		table: 2,
		td: 3,
		th: 3,
		tr: 3
	};

	var MAP_UNORDERED_LIST_STYLES = {
		circle: 'list-style-type: circle;',
		disc: 'list-style-type: disc;',
		square: 'list-style-type: square;'
	};

	var REGEX_ATTRS = /\s*([^=]+)\s*=\s*"([^"]*)"\s*/g;

	var REGEX_COLOR = /^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$/i;

	var REGEX_ESCAPE_REGEX = /[-[\]{}()*+?.,\\^$|#\s]/g;

	var REGEX_IMAGE_SRC = /^(?:https?:\/\/|\/)[-;/?:@&=+$,_.!~*'()%0-9a-z]{1,2048}$/i;

	var REGEX_LASTCHAR_NEWLINE = /\r?\n$/;

	var REGEX_NEW_LINE = /\r?\n/g;

	var REGEX_NUMBER = /^[\\.0-9]{1,8}$/;

	var REGEX_STRING_IS_NEW_LINE = /^\r?\n$/;

	var REGEX_URI = /^[-;/?:@&=+$,_.!~*'()%0-9a-zÀ-ÿ#]{1,2048}$|\${\S+}/i;

	var STR_BLANK = '';

	var STR_CODE = 'code';

	var STR_EMAIL = 'email';

	var STR_IMG = 'img';

	var STR_MAILTO = 'mailto:';

	var STR_NEW_LINE = '\n';

	var STR_START = 'start';

	var STR_TAG_A_CLOSE = '</a>';

	var STR_TAG_ATTR_CLOSE = '">';

	var STR_TAG_ATTR_HREF_OPEN = '<a href="';

	var STR_TAG_END_CLOSE = '>';

	var STR_TAG_END_OPEN = '</';

	var STR_TAG_LIST_ITEM_SHORT = '*';

	var STR_TAG_OPEN = '<';

	var STR_TAG_P_CLOSE = '</p>';

	var STR_TAG_SPAN_CLOSE = '</span>';

	var STR_TAG_SPAN_STYLE_OPEN = '<span style="';

	var STR_TAG_URL = 'url';

	var STR_TEXT_ALIGN = '<p style="text-align: ';

	var STR_TYPE = 'type';

	var TOKEN_DATA = Parser.TOKEN_DATA;

	var TOKEN_TAG_END = Parser.TOKEN_TAG_END;

	var TOKEN_TAG_START = Parser.TOKEN_TAG_START;

	var tplImage = new CKEDITOR.template(
		'<img src="{imageSrc}" {attributes} />'
	);

	var Converter = function(config) {
		var instance = this;

		config = config || {};

		instance.init(config);

		instance._config = config;
	};

	Converter.prototype = {
		_escapeHTML: A.Lang.String.escapeHTML,

		_extractData(toTagName, consume) {
			var instance = this;

			var result = [];

			var index = instance._tokenPointer + 1;

			var token;

			do {
				token = instance._parsedData[index++];

				if (token && token.type === TOKEN_DATA) {
					result.push(token.value);
				}
			} while (
				token &&
				token.type !== TOKEN_TAG_END &&
				token.value !== toTagName
			);

			if (consume) {
				instance._tokenPointer = index - 1;
			}

			return result.join(STR_BLANK);
		},

		_getFontSize(fontSize) {
			return MAP_FONT_SIZE[fontSize] || MAP_FONT_SIZE.defaultSize;
		},

		_handleCode() {
			var instance = this;

			instance._noParse = true;

			instance._handleSimpleTag('pre');

			instance._result.push(STR_NEW_LINE);
		},

		_handleColor(token) {
			var instance = this;

			var colorName = token.attribute;

			if (!colorName || !REGEX_COLOR.test(colorName)) {
				colorName = 'inherit';
			}

			instance._result.push(
				STR_TAG_SPAN_STYLE_OPEN +
					'color: ' +
					colorName +
					STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleData(token) {
			var instance = this;

			var emoticonImages = instance._config.emoticonImages;
			var emoticonPath = instance._config.emoticonPath;
			var emoticonSymbols = instance._config.emoticonSymbols;

			var value = instance._escapeHTML(token.value);

			value = instance._handleNewLine(value);

			if (!instance._noParse) {
				var length = emoticonSymbols.length;

				for (var i = 0; i < length; i++) {
					var image = tplImage.output({
						imageSrc: emoticonPath + emoticonImages[i]
					});

					var escapedSymbol = emoticonSymbols[i].replace(
						REGEX_ESCAPE_REGEX,
						'\\$&'
					);

					value = value.replace(
						new RegExp(escapedSymbol, 'g'),
						image
					);
				}
			}

			instance._result.push(value);
		},

		_handleEm() {
			var instance = this;

			instance._handleSimpleTag('em');
		},

		_handleEmail(token) {
			var instance = this;

			var href = STR_BLANK;

			var hrefInput =
				token.attribute || instance._extractData(STR_EMAIL, false);

			if (REGEX_URI.test(hrefInput)) {
				if (hrefInput.indexOf(STR_MAILTO) !== 0) {
					hrefInput = STR_MAILTO + hrefInput;
				}

				href = CKTools.htmlEncodeAttr(hrefInput);
			}

			instance._result.push(
				STR_TAG_ATTR_HREF_OPEN + href + STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_A_CLOSE);
		},

		_handleFont(token) {
			var instance = this;

			var fontName = token.attribute;

			fontName = CKTools.htmlEncodeAttr(fontName);

			instance._result.push(
				STR_TAG_SPAN_STYLE_OPEN +
					'font-family: ' +
					fontName +
					STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleImage(token) {
			var instance = this;

			var imageSrc = STR_BLANK;

			var imageSrcInput = instance._extractData(STR_IMG, true);

			if (REGEX_IMAGE_SRC.test(imageSrcInput)) {
				imageSrc = CKTools.htmlEncodeAttr(imageSrcInput);
			}

			var result = tplImage.output({
				attributes: instance._handleImageAttributes(token, token.value),
				imageSrc
			});

			instance._result.push(result);
		},

		_handleImageAttributes(token) {
			var instance = this;

			var attrs = STR_BLANK;

			if (token.attribute) {
				var bbCodeAttr;

				while ((bbCodeAttr = REGEX_ATTRS.exec(token.attribute))) {
					var attrName = bbCodeAttr[1];

					if (MAP_IMAGE_ATTRIBUTES[attrName]) {
						var attrValue = bbCodeAttr[2];

						if (attrValue) {
							attrs +=
								' ' +
								attrName +
								'="' +
								instance._escapeHTML(attrValue) +
								'"';
						}
					}
				}
			}

			return attrs;
		},

		_handleList(token) {
			var instance = this;

			var listAttributes = STR_BLANK;
			var tag = 'ul';

			if (token.attribute) {
				var listAttribute;

				while ((listAttribute = REGEX_ATTRS.exec(token.attribute))) {
					var attrName = listAttribute[1];
					var attrValue = listAttribute[2];

					var styleAttr;

					if (attrName === STR_TYPE) {
						if (MAP_ORDERED_LIST_STYLES[attrValue]) {
							styleAttr = MAP_ORDERED_LIST_STYLES[attrValue];

							tag = 'ol';
						}
						else {
							styleAttr = MAP_UNORDERED_LIST_STYLES[attrValue];
						}

						if (styleAttr) {
							listAttributes += ' style="' + styleAttr + '"';
						}
					}
					else if (
						attrName === STR_START &&
						REGEX_NUMBER.test(attrValue)
					) {
						listAttributes += ' start="' + attrValue + '"';
					}
				}
			}

			instance._result.push(
				STR_TAG_OPEN + tag + listAttributes + STR_TAG_END_CLOSE
			);

			instance._stack.push(STR_TAG_END_OPEN + tag + STR_TAG_END_CLOSE);
		},

		_handleListItem() {
			var instance = this;

			instance._handleSimpleTag('li');
		},

		_handleNewLine(value) {
			var instance = this;

			var nextToken;

			if (!instance._noParse) {
				if (REGEX_STRING_IS_NEW_LINE.test(value)) {
					nextToken =
						instance._parsedData[instance._tokenPointer + 1];

					if (
						nextToken &&
						hasOwnProperty.call(
							MAP_TOKENS_EXCLUDE_NEW_LINE,
							nextToken.value
						) &&
						nextToken.type &
							MAP_TOKENS_EXCLUDE_NEW_LINE[nextToken.value]
					) {
						value = STR_BLANK;
					}
				}
				else if (REGEX_LASTCHAR_NEWLINE.test(value)) {
					nextToken =
						instance._parsedData[instance._tokenPointer + 1];

					if (
						nextToken &&
						nextToken.type === TOKEN_TAG_END &&
						nextToken.value === STR_TAG_LIST_ITEM_SHORT
					) {
						value = value.substring(0, value.length - 1);
					}
				}

				if (value) {
					value = value.replace(REGEX_NEW_LINE, '<br>');
				}
			}

			return value;
		},

		_handleQuote(token) {
			var instance = this;

			var cite = token.attribute;

			var result = '<blockquote>';

			if (cite && cite.length) {
				cite = BBCodeUtil.escape(cite);

				result += '<cite>' + cite + '</cite>';
			}

			instance._result.push(result);

			instance._stack.push('</blockquote>');
		},

		_handleSimpleTag(tagName) {
			var instance = this;

			instance._result.push(STR_TAG_OPEN, tagName, STR_TAG_END_CLOSE);

			instance._stack.push(
				STR_TAG_END_OPEN + tagName + STR_TAG_END_CLOSE
			);
		},

		_handleSimpleTags(token) {
			var instance = this;

			instance._handleSimpleTag(token.value);
		},

		_handleSize(token) {
			var instance = this;

			var size = token.attribute;

			if (!size || !REGEX_NUMBER.test(size)) {
				size = '1';
			}

			instance._result.push(
				STR_TAG_SPAN_STYLE_OPEN,
				'font-size: ',
				instance._getFontSize(size),
				'px;',
				STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_SPAN_CLOSE);
		},

		_handleStrikeThrough() {
			var instance = this;

			instance._handleSimpleTag('strike');
		},

		_handleStrong() {
			var instance = this;

			instance._handleSimpleTag('strong');
		},

		_handleTable() {
			var instance = this;

			instance._handleSimpleTag('table');
		},

		_handleTableCell() {
			var instance = this;

			instance._handleSimpleTag('td');
		},

		_handleTableHeader() {
			var instance = this;

			instance._handleSimpleTag('th');
		},

		_handleTableRow() {
			var instance = this;

			instance._handleSimpleTag('tr');
		},

		_handleTagEnd(token) {
			var instance = this;

			var tagName = token.value;

			instance._result.push(instance._stack.pop());

			if (tagName === STR_CODE) {
				instance._noParse = false;
			}
		},

		_handleTagStart(token) {
			var instance = this;

			var tagName = token.value;

			var handlerName = MAP_HANDLERS[tagName] || '_handleSimpleTags';

			instance[handlerName](token);
		},

		_handleTextAlign(token) {
			var instance = this;

			instance._result.push(
				STR_TEXT_ALIGN,
				token.value,
				STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_P_CLOSE);
		},

		_handleURL(token) {
			var instance = this;

			var href = STR_BLANK;

			var hrefInput =
				token.attribute || instance._extractData(STR_TAG_URL, false);

			if (REGEX_URI.test(hrefInput)) {
				href = CKTools.htmlEncodeAttr(hrefInput);
			}

			instance._result.push(
				STR_TAG_ATTR_HREF_OPEN + href + STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_A_CLOSE);
		},

		_reset() {
			var instance = this;

			instance._result.length = 0;
			instance._stack.length = 0;

			instance._parsedData = null;

			instance._noParse = false;
		},

		constructor: Converter,

		convert(data) {
			var instance = this;

			var parsedData = instance._parser.parse(data);

			instance._parsedData = parsedData;

			var length = parsedData.length;

			for (
				instance._tokenPointer = 0;
				instance._tokenPointer < length;
				instance._tokenPointer++
			) {
				var token = parsedData[instance._tokenPointer];

				var type = token.type;

				if (type === TOKEN_TAG_START) {
					instance._handleTagStart(token);
				}
				else if (type === TOKEN_TAG_END) {
					instance._handleTagEnd(token);
				}
				else if (type === TOKEN_DATA) {
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

		init(config) {
			var instance = this;

			instance._parser = new Parser(config.parser);

			instance._config = config;

			instance._result = [];
			instance._stack = [];
		}
	};

	CKEDITOR.BBCode2HTML = Converter;
})();
