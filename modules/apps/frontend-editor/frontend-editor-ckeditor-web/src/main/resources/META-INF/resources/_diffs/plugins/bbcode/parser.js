/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
