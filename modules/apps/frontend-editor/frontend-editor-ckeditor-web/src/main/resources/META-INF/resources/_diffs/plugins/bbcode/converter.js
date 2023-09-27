/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
		alt: 1,
		class: 1,
		dir: 1,
		height: 1,
		id: 1,
		lang: 1,
		longdesc: 1,
		style: 1,
		title: 1,
		width: 1,
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

	const REGEX_ATTRS = /\s*([^=]+)\s*=\s*"([^"]+)"\s*/g;

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

			if (REGEX_IMAGE_SRC.test(imageSrcInput)) {
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
