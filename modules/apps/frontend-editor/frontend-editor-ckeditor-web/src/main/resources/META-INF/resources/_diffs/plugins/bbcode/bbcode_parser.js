/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const A = AUI();

	const entities = {
		...Liferay.Util.MAP_HTML_CHARS_ESCAPED,
		'(': '&#40;',
		')': '&#41;',
		'[': '&#91;',
		']': '&#93;',
	};

	const BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', Liferay.Util, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', Liferay.Util, entities);
})();
(function () {
	// eslint-disable-next-line no-control-regex
	const REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'<>[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/gi;

	const Lexer = function (data) {
		const instance = this;

		instance._data = data;
	};

	Lexer.prototype = {
		constructor: Lexer,

		getLastIndex() {
			return REGEX_BBCODE.lastIndex;
		},

		getNextToken() {
			const instance = this;

			return REGEX_BBCODE.exec(instance._data);
		},
	};

	Liferay.BBCodeLexer = Lexer;
})();
(function () {
	const hasOwnProperty = Object.prototype.hasOwnProperty;

	const isString = function (val) {
		return typeof val === 'string';
	};

	const ELEMENTS_BLOCK = {
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
		'tr': 1,
	};

	const ELEMENTS_CLOSE_SELF = {
		'*': 1,
	};

	const ELEMENTS_INLINE = {
		b: 1,
		color: 1,
		font: 1,
		i: 1,
		// eslint-disable-next-line @liferay/no-abbreviations
		img: 1,
		s: 1,
		size: 1,
		u: 1,
		url: 1,
	};

	const REGEX_TAG_NAME = /^\/?(?:b|center|code|colou?r|email|i|img|justify|left|pre|q|quote|right|\*|s|size|table|tr|th|td|li|list|font|u|url)$/i;

	const STR_TAG_CODE = 'code';

	const Parser = function (config) {
		const instance = this;

		config = config || {};

		instance._config = config;

		instance.init();
	};

	Parser.prototype = {
		_handleData(token, data) {
			const instance = this;

			let length = data.length;

			let lastIndex = length;

			if (token) {
				lastIndex = instance._lexer.getLastIndex();

				length = lastIndex;

				const tokenItem = token[1] || token[3];

				if (instance._isValidTag(tokenItem)) {
					length = token.index;
				}
			}

			if (length > instance._dataPointer) {
				instance._result.push({
					type: Parser.TOKEN_DATA,
					value: data.substring(instance._dataPointer, length),
				});
			}

			instance._dataPointer = lastIndex;
		},

		_handleTagEnd(token) {
			const instance = this;

			let pos = 0;

			const stack = instance._stack;

			let tagName;

			if (token) {
				if (isString(token)) {
					tagName = token;
				}
				else {
					tagName = token[3];
				}

				tagName = tagName.toLowerCase();

				for (pos = stack.length - 1; pos >= 0; pos--) {
					if (stack[pos] === tagName) {
						break;
					}
				}
			}

			if (pos >= 0) {
				const tokenTagEnd = Parser.TOKEN_TAG_END;

				for (let i = stack.length - 1; i >= pos; i--) {
					instance._result.push({
						type: tokenTagEnd,
						value: stack[i],
					});
				}

				stack.length = pos;
			}
		},

		_handleTagStart(token) {
			const instance = this;

			const tagName = token[1].toLowerCase();

			if (instance._isValidTag(tagName)) {
				const stack = instance._stack;

				if (hasOwnProperty.call(ELEMENTS_BLOCK, tagName)) {
					let lastTag;

					while (
						(lastTag = stack.last()) &&
						hasOwnProperty.call(ELEMENTS_INLINE, lastTag)
					) {
						instance._handleTagEnd(lastTag);
					}
				}

				if (
					hasOwnProperty.call(ELEMENTS_CLOSE_SELF, tagName) &&
					stack.last() === tagName
				) {
					instance._handleTagEnd(tagName);
				}

				stack.push(tagName);

				instance._result.push({
					attribute: token[2],
					type: Parser.TOKEN_TAG_START,
					value: tagName,
				});
			}
		},

		_isValidTag(tagName) {
			let valid = false;

			if (tagName && tagName.length) {
				valid = REGEX_TAG_NAME.test(tagName);
			}

			return valid;
		},

		_reset() {
			const instance = this;

			instance._stack.length = 0;
			instance._result.length = 0;

			instance._dataPointer = 0;
		},

		constructor: Parser,

		init() {
			const instance = this;

			const stack = [];

			stack.last =
				stack.last ||
				function () {
					const instance = this;

					return instance[instance.length - 1];
				};

			instance._result = [];

			instance._stack = stack;

			instance._dataPointer = 0;
		},

		parse(data) {
			const instance = this;

			const lexer = new Liferay.BBCodeLexer(data);

			instance._lexer = lexer;

			let token;

			while ((token = lexer.getNextToken())) {
				instance._handleData(token, data);

				if (token[1]) {
					instance._handleTagStart(token);

					if (token[1].toLowerCase() === STR_TAG_CODE) {
						while (
							(token = lexer.getNextToken()) &&
							token[3] !== STR_TAG_CODE
						) {

							// Continue.

						}

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

			const result = instance._result.slice(0);

			instance._reset();

			return result;
		},
	};

	Parser.TOKEN_DATA = 4;
	Parser.TOKEN_TAG_END = 2;
	Parser.TOKEN_TAG_START = 1;

	Liferay.BBCodeParser = Parser;
})();
(function () {
	const BBCodeUtil = Liferay.BBCodeUtil;
	const CKTools = CKEDITOR.tools;

	const Parser = Liferay.BBCodeParser;

	const hasOwnProperty = Object.prototype.hasOwnProperty;

	const MAP_FONT_SIZE = {
		1: 10,
		2: 12,
		3: 14,
		4: 16,
		5: 18,
		6: 24,
		7: 32,
		8: 48,
		defaultSize: 14,
	};

	const MAP_HANDLERS = {
		'*': '_handleListItem',
		'b': '_handleStrong',
		'center': '_handleTextAlign',
		'code': '_handleCode',
		'color': '_handleColor',
		'colour': '_handleColor',
		'email': '_handleEmail',
		'font': '_handleFont',
		'i': '_handleEm',
		// eslint-disable-next-line @liferay/no-abbreviations
		'img': '_handleImage',
		'justify': '_handleTextAlign',
		'left': '_handleTextAlign',
		'li': '_handleListItem',
		'list': '_handleList',
		'q': '_handleQuote',
		'quote': '_handleQuote',
		'right': '_handleTextAlign',
		's': '_handleStrikeThrough',
		'size': '_handleSize',
		'table': '_handleTable',
		'td': '_handleTableCell',
		'th': '_handleTableHeader',
		'tr': '_handleTableRow',
		'url': '_handleURL',
	};

	const MAP_IMAGE_ATTRIBUTES = {
		'alt': 1,
		'class': 1,
		'data-image-id': 1,
		'dir': 1,
		'height': 1,
		'id': 1,
		'lang': 1,
		'longdesc': 1,
		'style': 1,
		'title': 1,
		'width': 1,
	};

	const MAP_ORDERED_LIST_STYLES = {
		1: 'list-style-type: decimal;',
		A: 'list-style-type: upper-alpha;',
		I: 'list-style-type: upper-roman;',
		a: 'list-style-type: lower-alpha;',
		i: 'list-style-type: lower-roman;',
	};

	const MAP_TOKENS_EXCLUDE_NEW_LINE = {
		'*': 3,
		'li': 3,
		'table': 2,
		'td': 3,
		'th': 3,
		'tr': 3,
	};

	const MAP_UNORDERED_LIST_STYLES = {
		circle: 'list-style-type: circle;',
		disc: 'list-style-type: disc;',
		square: 'list-style-type: square;',
	};

	const REGEX_ATTRS = /\s*([^=]+)\s*=\s*"([^"]*)"\s*/g;

	const REGEX_COLOR = /^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$/i;

	const REGEX_ESCAPE_REGEX = /[-[\]{}()*+?.,\\^$|#\s]/g;

	const REGEX_IMAGE_SRC = /^(?:https?:\/\/|\/)[-;/?:@&=+$,_.!~*'()%0-9a-z]{1,2048}$/i;

	const REGEX_LASTCHAR_NEWLINE = /\r?\n$/;

	const REGEX_NEW_LINE = /\r?\n/g;

	const REGEX_NUMBER = /^[\\.0-9]{1,8}$/;

	const REGEX_STRING_IS_NEW_LINE = /^\r?\n$/;

	const REGEX_URI = /^[-;/?:@&=+$,_.!~*'()%0-9a-zÀ-ÿ#]{1,2048}$|\${\S+}/i;

	const STR_BLANK = '';

	const STR_CODE = 'code';

	const STR_EMAIL = 'email';

	const STR_IMG = 'img';

	const STR_MAILTO = 'mailto:';

	const STR_NEW_LINE = '\n';

	const STR_START = 'start';

	const STR_TAG_A_CLOSE = '</a>';

	const STR_TAG_ATTR_CLOSE = '">';

	const STR_TAG_ATTR_HREF_OPEN = '<a href="';

	const STR_TAG_END_CLOSE = '>';

	const STR_TAG_END_OPEN = '</';

	const STR_TAG_LIST_ITEM_SHORT = '*';

	const STR_TAG_OPEN = '<';

	const STR_TAG_P_CLOSE = '</p>';

	const STR_TAG_SPAN_CLOSE = '</span>';

	const STR_TAG_SPAN_STYLE_OPEN = '<span style="';

	const STR_TAG_URL = 'url';

	const STR_TEXT_ALIGN = '<p style="text-align: ';

	const STR_TYPE = 'type';

	const TOKEN_DATA = Parser.TOKEN_DATA;

	const TOKEN_TAG_END = Parser.TOKEN_TAG_END;

	const TOKEN_TAG_START = Parser.TOKEN_TAG_START;

	const tplImage = new CKEDITOR.template(
		'<img src="{imageSrc}" {attributes} />'
	);

	const Converter = function (config) {
		const instance = this;

		config = config || {};

		instance.init(config);

		instance._config = config;
	};

	Converter.prototype = {
		_escapeHTML: Liferay.Util.escapeHTML,

		_extractData(toTagName, consume) {
			const instance = this;

			const result = [];

			let index = instance._tokenPointer + 1;

			let token;

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
			const instance = this;

			instance._noParse = true;

			instance._handleSimpleTag('pre');

			instance._result.push(STR_NEW_LINE);
		},

		_handleColor(token) {
			const instance = this;

			let colorName = token.attribute;

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
			const instance = this;

			const emoticonImages = instance._config.emoticonImages;
			const emoticonPath = instance._config.emoticonPath;
			const emoticonSymbols = instance._config.emoticonSymbols;

			let value = instance._escapeHTML(token.value);

			value = instance._handleNewLine(value);

			if (!instance._noParse) {
				const length = emoticonSymbols.length;

				for (let i = 0; i < length; i++) {
					const image = tplImage.output({
						imageSrc: emoticonPath + emoticonImages[i],
					});

					const escapedSymbol = emoticonSymbols[i].replace(
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
			const instance = this;

			instance._handleSimpleTag('em');
		},

		_handleEmail(token) {
			const instance = this;

			let href = STR_BLANK;

			let hrefInput =
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
			const instance = this;

			let fontName = token.attribute;

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
			const instance = this;

			let imageSrc = STR_BLANK;

			const imageSrcInput = instance._extractData(STR_IMG, true);

			if (
				imageSrcInput.startsWith('data:image/') ||
				REGEX_IMAGE_SRC.test(imageSrcInput)
			) {
				imageSrc = CKTools.htmlEncodeAttr(imageSrcInput);
			}

			const result = tplImage.output({
				attributes: instance._handleImageAttributes(token, token.value),
				imageSrc,
			});

			instance._result.push(result);
		},

		_handleImageAttributes(token) {
			const instance = this;

			let attrs = STR_BLANK;

			if (token.attribute) {
				let bbCodeAttr;

				while ((bbCodeAttr = REGEX_ATTRS.exec(token.attribute))) {
					const attrName = bbCodeAttr[1];

					if (MAP_IMAGE_ATTRIBUTES[attrName]) {
						const attrValue = bbCodeAttr[2];

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
			const instance = this;

			let listAttributes = STR_BLANK;
			let tag = 'ul';

			if (token.attribute) {
				let listAttribute;

				while ((listAttribute = REGEX_ATTRS.exec(token.attribute))) {
					const attrName = listAttribute[1];
					const attrValue = listAttribute[2];

					let styleAttr;

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
			const instance = this;

			instance._handleSimpleTag('li');
		},

		_handleNewLine(value) {
			const instance = this;

			let nextToken;

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
			const instance = this;

			let cite = token.attribute;

			let result = '<blockquote><p>';

			if (cite && cite.length) {
				cite = BBCodeUtil.escape(cite);

				result += '<cite>' + cite + '</cite>';
			}

			instance._result.push(result);

			instance._stack.push('</p></blockquote>');
		},

		_handleSimpleTag(tagName) {
			const instance = this;

			instance._result.push(STR_TAG_OPEN, tagName, STR_TAG_END_CLOSE);

			instance._stack.push(
				STR_TAG_END_OPEN + tagName + STR_TAG_END_CLOSE
			);
		},

		_handleSimpleTags(token) {
			const instance = this;

			instance._handleSimpleTag(token.value);
		},

		_handleSize(token) {
			const instance = this;

			let size = token.attribute;

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
			const instance = this;

			instance._handleSimpleTag('strike');
		},

		_handleStrong() {
			const instance = this;

			instance._handleSimpleTag('strong');
		},

		_handleTable() {
			const instance = this;

			instance._handleSimpleTag('table');
		},

		_handleTableCell() {
			const instance = this;

			instance._handleSimpleTag('td');
		},

		_handleTableHeader() {
			const instance = this;

			instance._handleSimpleTag('th');
		},

		_handleTableRow() {
			const instance = this;

			instance._handleSimpleTag('tr');
		},

		_handleTagEnd(token) {
			const instance = this;

			const tagName = token.value;

			instance._result.push(instance._stack.pop());

			if (tagName === STR_CODE) {
				instance._noParse = false;
			}
		},

		_handleTagStart(token) {
			const instance = this;

			const tagName = token.value;

			const handlerName = MAP_HANDLERS[tagName] || '_handleSimpleTags';

			instance[handlerName](token);
		},

		_handleTextAlign(token) {
			const instance = this;

			instance._result.push(
				STR_TEXT_ALIGN,
				token.value,
				STR_TAG_ATTR_CLOSE
			);

			instance._stack.push(STR_TAG_P_CLOSE);
		},

		_handleURL(token) {
			const instance = this;

			let href = STR_BLANK;

			const hrefInput =
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
			const instance = this;

			instance._result.length = 0;
			instance._stack.length = 0;

			instance._parsedData = null;

			instance._noParse = false;
		},

		constructor: Converter,

		convert(data) {
			const instance = this;

			const parsedData = instance._parser.parse(data);

			instance._parsedData = parsedData;

			const length = parsedData.length;

			for (
				instance._tokenPointer = 0;
				instance._tokenPointer < length;
				instance._tokenPointer++
			) {
				const token = parsedData[instance._tokenPointer];

				const type = token.type;

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

			const result = instance._result.join(STR_BLANK);

			instance._reset();

			return result;
		},

		init(config) {
			const instance = this;

			instance._parser = new Parser(config.parser);

			instance._config = config;

			instance._result = [];
			instance._stack = [];
		},
	};

	CKEDITOR.BBCode2HTML = Converter;
})();
