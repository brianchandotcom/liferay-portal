/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const CKTools = CKEDITOR.tools;

	const NEW_LINE = '\n';

	const REGEX_CREOLE_RESERVED_CHARACTERS = /(\/{1,2}|={1,6}|\[{1,2}|\]{1,2}|\\{1,2}|\*{1,}|----|{{2,3}|}{2,3}|#{1,})/g;

	const REGEX_HEADER = /^h([1-6])$/i;

	const REGEX_LASTCHAR_NEWLINE = /(\r?\n\s*)$/;

	const REGEX_NEWLINE = /\r?\n/g;

	const REGEX_NON_BREAKING_SPACE = /\u00a0/g;

	const REGEX_NOT_WHITESPACE = /[^\t\n\r ]/;

	const REGEX_URL_PREFIX = /^(?:\/|https?|ftp):\/\//i;

	const REGEX_ZERO_WIDTH_SPACE = /\u200B/g;

	const STR_BLANK = '';

	const STR_EQUALS = '=';

	const STR_LIST_ITEM_ESCAPE_CHARACTERS = '\\\\';

	const STR_PIPE = '|';

	const STR_SPACE = ' ';

	const TAG_BOLD = '**';

	const TAG_EMPHASIZE = '//';

	const TAG_LIST_ITEM = 'li';

	const TAG_ORDERED_LIST = 'ol';

	const TAG_ORDERED_LIST_ITEM = '#';

	const TAG_PARAGRAPH = 'p';

	const TAG_PRE = 'pre';

	const TAG_TELETYPETEXT = 'tt';

	const TAG_UNORDERED_LIST = 'ul';

	const TAG_UNORDERED_LIST_ITEM = '*';

	let attachmentURLPrefix;

	const brFiller = CKEDITOR.env.needsBrFiller ? '<br>' : '';

	const enterModeEmptyValue = {
		1: ['<p>' + brFiller + '</p>'],
		2: [brFiller],
		3: ['<div>' + brFiller + '</div>'],
	};

	const CreoleDataProcessor = function (editor) {
		const instance = this;

		instance._editor = editor;
	};

	CreoleDataProcessor.prototype = {
		_appendNewLines(total) {
			const instance = this;

			let count = 0;

			const endResult = instance._endResult;

			const newLinesAtEnd = REGEX_LASTCHAR_NEWLINE.exec(
				endResult.slice(-2).join(STR_BLANK)
			);

			if (newLinesAtEnd) {
				count = newLinesAtEnd[1].length;
			}

			while (count++ < total) {
				endResult.push(NEW_LINE);
			}
		},

		_convert(data) {
			const instance = this;

			const node = document.createElement('div');

			node.innerHTML = data;

			instance._handle(node);

			const endResult = instance._endResult.join(STR_BLANK);

			instance._endResult = null;

			instance._listsStack.length = 0;

			return endResult;
		},

		_endResult: null,

		_handle(node) {
			const instance = this;

			if (!instance._endResult) {
				instance._endResult = [];
			}

			const children = node.childNodes;

			const pushTagList = instance._pushTagList;

			for (let i = 0; i < children.length; i++) {
				const listTagsIn = [];
				const listTagsOut = [];
				const stylesTagsIn = [];
				const stylesTagsOut = [];

				const child = children[i];

				if (instance._skipParse) {
					instance._handleData(child.data || child.outerHTML, node);

					continue;
				}
				else if (instance._isIgnorable(child)) {
					continue;
				}

				instance._handleElementStart(child, listTagsIn, listTagsOut);
				instance._handleStyles(child, stylesTagsIn, stylesTagsOut);

				pushTagList.call(instance, listTagsIn);
				pushTagList.call(instance, stylesTagsIn);

				instance._handle(child);

				instance._handleElementEnd(child, listTagsIn, listTagsOut);

				pushTagList.call(instance, stylesTagsOut.reverse());
				pushTagList.call(instance, listTagsOut);
			}

			instance._handleData(node.data, node);
		},

		_handleBreak(element, listTagsIn, _listTagsOut) {
			const instance = this;

			let newLineCharacter = STR_LIST_ITEM_ESCAPE_CHARACTERS;

			const nextSibling = element.nextSibling;

			if (instance._skipParse) {
				newLineCharacter = NEW_LINE;

				listTagsIn.push(newLineCharacter);
			}
			else if (
				element.previousSibling &&
				nextSibling &&
				nextSibling !== NEW_LINE
			) {
				listTagsIn.push(newLineCharacter);
			}
		},

		_handleData(data, _element) {
			const instance = this;

			if (data) {
				if (!instance._skipParse) {
					data = data.replace(REGEX_NEWLINE, STR_BLANK);
					data = data.replace(REGEX_ZERO_WIDTH_SPACE, STR_BLANK);
					data = data.replace(REGEX_NON_BREAKING_SPACE, STR_SPACE);

					if (!instance._verbatim) {
						data = data.replace(
							REGEX_CREOLE_RESERVED_CHARACTERS,
							(_match, p1, _offset, _string) => {
								let res = '';

								if (!instance._endResult.length) {
									res += '~' + p1;
								}
								else {
									const lastResultString =
										instance._endResult[
											instance._endResult.length - 1
										];

									const lastResultCharacter =
										lastResultString[
											lastResultString.length - 1
										];

									if (
										lastResultCharacter !== '~' &&
										lastResultCharacter !== p1[0]
									) {
										res += '~';
									}

									res += p1;
								}

								return res;
							}
						);
					}
				}

				instance._endResult.push(data);
			}
		},

		_handleElementEnd(element, _listTagsIn, listTagsOut) {
			const instance = this;

			let tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();
			}

			if (tagName === TAG_PARAGRAPH) {
				if (!instance._isLastItemNewLine()) {
					if (instance._hasParentNode(element, 'table', Infinity)) {
						instance._endResult.push(
							STR_LIST_ITEM_ESCAPE_CHARACTERS
						);
					}
					else {
						instance._endResult.push(NEW_LINE);
					}
				}
			}
			else if (
				tagName === TAG_UNORDERED_LIST ||
				tagName === TAG_ORDERED_LIST
			) {
				instance._listsStack.pop();

				let newLinesCount = 1;

				if (!instance._hasParentNode(element, TAG_LIST_ITEM)) {
					newLinesCount = 2;
				}

				instance._appendNewLines(newLinesCount);
			}
			else if (tagName === TAG_PRE) {
				if (!instance._isLastItemNewLine()) {
					instance._endResult.push(NEW_LINE);
				}
			}
			else if (tagName === 'table') {
				listTagsOut.push(NEW_LINE);
			}

			instance._skipParse = false;
			instance._verbatim = false;
		},

		_handleElementStart(element, listTagsIn, listTagsOut) {
			const instance = this;

			let tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();

				const regexHeader = REGEX_HEADER.exec(tagName);
				const elementContent = element.textContent
					.toString()
					.replace(REGEX_ZERO_WIDTH_SPACE, STR_BLANK);

				if (tagName === TAG_PARAGRAPH) {
					instance._handleParagraph(element, listTagsIn, listTagsOut);
				}
				else if (tagName === 'br') {
					instance._handleBreak(element, listTagsIn, listTagsOut);
				}
				else if (tagName === 'a') {
					instance._handleLink(element, listTagsIn, listTagsOut);
				}
				else if (
					(tagName === 'strong' || tagName === 'b') &&
					!!elementContent.length
				) {
					instance._handleStrong(element, listTagsIn, listTagsOut);
				}
				else if (
					(tagName === 'em' || tagName === 'i') &&
					!!elementContent.length
				) {
					instance._handleEm(element, listTagsIn, listTagsOut);
				}
				else if (tagName === 'img') {
					instance._handleImage(element, listTagsIn, listTagsOut);
				}
				else if (tagName === TAG_UNORDERED_LIST) {
					instance._handleUnorderedList(
						element,
						listTagsIn,
						listTagsOut
					);
				}
				else if (tagName === TAG_LIST_ITEM) {
					instance._handleListItem(element, listTagsIn, listTagsOut);
				}
				else if (tagName === TAG_ORDERED_LIST) {
					instance._handleOrderedList(
						element,
						listTagsIn,
						listTagsOut
					);
				}
				else if (tagName === 'hr') {
					instance._handleHr(element, listTagsIn, listTagsOut);
				}
				else if (tagName === TAG_PRE) {
					instance._handlePre(element, listTagsIn, listTagsOut);
				}
				else if (tagName === TAG_TELETYPETEXT) {
					instance._handleTT(element, listTagsIn, listTagsOut);
				}
				else if (regexHeader) {
					instance._handleHeader(
						element,
						listTagsIn,
						listTagsOut,
						regexHeader
					);
				}
				else if (tagName === 'th') {
					instance._handleTableHeader(
						element,
						listTagsIn,
						listTagsOut
					);
				}
				else if (tagName === 'tr') {
					instance._handleTableRow(element, listTagsIn, listTagsOut);
				}
				else if (tagName === 'td') {
					instance._handleTableCell(element, listTagsIn, listTagsOut);
				}
			}
		},

		_handleEm(_element, listTagsIn, listTagsOut) {
			listTagsIn.push(TAG_EMPHASIZE);
			listTagsOut.push(TAG_EMPHASIZE);
		},

		_handleHeader(_element, listTagsIn, listTagsOut, params) {
			const instance = this;

			let res = new Array(parseInt(params[1], 10) + 1);

			res = res.join(STR_EQUALS);

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push(res, STR_SPACE);
			listTagsOut.push(STR_SPACE, res, NEW_LINE);

			instance._verbatim = true;
		},

		_handleHr(element, listTagsIn, _listTagsOut) {
			const instance = this;

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('----', NEW_LINE);
		},

		_handleImage(element, listTagsIn, listTagsOut) {
			const attrAlt = element.getAttribute('alt');
			let attrSrc = element.getAttribute('src');

			attrSrc = attrSrc.replace(attachmentURLPrefix, STR_BLANK);

			listTagsIn.push('{{', attrSrc);

			if (attrAlt) {
				listTagsIn.push(STR_PIPE, attrAlt);
			}

			listTagsOut.push('}}');
		},

		_handleLink(element, listTagsIn, listTagsOut) {
			const instance = this;

			let hrefAttribute = element.getAttribute('href');

			if (hrefAttribute) {
				if (!REGEX_URL_PREFIX.test(hrefAttribute)) {
					hrefAttribute = decodeURIComponent(hrefAttribute);
				}

				const linkText = element.textContent || element.innerText;

				listTagsIn.push('[[');

				if (linkText === hrefAttribute) {
					instance._verbatim = true;
				}
				else {
					listTagsIn.push(hrefAttribute, STR_PIPE);
				}

				listTagsOut.push(']]');
			}
		},

		_handleListItem(element, listTagsIn, _listTagsOut) {
			const instance = this;

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			const listsStack = instance._listsStack;

			const listsStackLength = listsStack.length;

			listTagsIn.push(
				new Array(listsStackLength + 1).join(
					listsStack[listsStackLength - 1]
				)
			);
		},

		_handleOrderedList(_element, _listTagsIn) {
			const instance = this;

			instance._listsStack.push(TAG_ORDERED_LIST_ITEM);
		},

		_handleParagraph(element, _listTagsIn, listTagsOut) {
			const instance = this;

			if (!instance._hasParentNode(element, 'table', Infinity)) {
				if (instance._isDataAvailable()) {
					instance._appendNewLines(2);
				}

				listTagsOut.push(NEW_LINE);
			}
		},

		_handlePre(_element, listTagsIn, listTagsOut) {
			const instance = this;

			instance._skipParse = true;

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				instance._endResult.push(NEW_LINE);
			}

			listTagsIn.push('{{{', NEW_LINE);
			listTagsOut.push('}}}', NEW_LINE);
		},

		_handleStrong(element, listTagsIn, listTagsOut) {
			const instance = this;

			const previousSibling = element.previousSibling;

			if (
				instance._isParentNode(element, TAG_LIST_ITEM) &&
				(!previousSibling || instance._isIgnorable(previousSibling))
			) {
				listTagsIn.push(STR_SPACE);
			}

			listTagsIn.push(TAG_BOLD);
			listTagsOut.push(TAG_BOLD);
		},

		_handleStyles(element, stylesTagsIn, stylesTagsOut) {
			const style = element.style;

			if (style) {
				if (style.fontWeight.toLowerCase() === 'bold') {
					stylesTagsIn.push(TAG_BOLD);
					stylesTagsOut.push(TAG_BOLD);
				}

				if (style.fontStyle.toLowerCase() === 'italic') {
					stylesTagsIn.push(TAG_EMPHASIZE);
					stylesTagsOut.push(TAG_EMPHASIZE);
				}
			}
		},

		_handleTT(_element, listTagsIn, listTagsOut) {
			const instance = this;

			instance._skipParse = true;

			listTagsIn.push('{{{');
			listTagsOut.push('}}}');
		},

		_handleTableCell(_element, listTagsIn, _listTagsOut) {
			listTagsIn.push(STR_PIPE);
		},

		_handleTableHeader(_element, listTagsIn, _listTagsOut) {
			listTagsIn.push(STR_PIPE, STR_EQUALS);
		},

		_handleTableRow(element, listTagsIn, listTagsOut) {
			const instance = this;

			if (instance._isDataAvailable()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsOut.push(STR_PIPE);
		},

		_handleUnorderedList(_element, _listTagsIn, _listTagsOut) {
			const instance = this;

			instance._listsStack.push(TAG_UNORDERED_LIST_ITEM);
		},

		_hasClass(element, className) {
			return (
				(STR_SPACE + element.className + STR_SPACE).indexOf(
					STR_SPACE + className + STR_SPACE
				) > -1
			);
		},

		_hasParentNode(element, tags, level) {
			const instance = this;

			if (!CKTools.isArray(tags)) {
				tags = [tags];
			}

			let result = false;

			const parentNode = element.parentNode;

			const tagName =
				parentNode &&
				parentNode.tagName &&
				parentNode.tagName.toLowerCase();

			if (tagName) {
				const length = tags.length;

				for (let i = 0; i < length; i++) {
					result = instance._tagNameMatch(tagName, tags[i]);

					if (result) {
						break;
					}
				}
			}

			if (!result && parentNode && (!isFinite(level) || --level)) {
				result = instance._hasParentNode(parentNode, tags, level);
			}

			return result;
		},

		_isDataAvailable() {
			const instance = this;

			const endResult = instance._endResult;

			return endResult && endResult.length;
		},

		_isIgnorable(node) {
			const instance = this;

			const nodeType = node.nodeType;

			return (
				node.isElementContentWhitespace ||
				nodeType === 8 ||
				(nodeType === 3 && instance._isWhitespace(node))
			);
		},

		_isLastItemNewLine() {
			const instance = this;

			const endResult = instance._endResult;

			return (
				endResult && REGEX_LASTCHAR_NEWLINE.test(endResult.slice(-1))
			);
		},

		_isParentNode(element, tagName) {
			const instance = this;

			return instance._hasParentNode(element, tagName, 1);
		},

		_isWhitespace(node) {
			return (
				node.isElementContentWhitespace ||
				!REGEX_NOT_WHITESPACE.test(node.data)
			);
		},

		_listsStack: [],

		_pushTagList(tagsList) {
			const instance = this;

			let tag;

			const endResult = instance._endResult;
			const length = tagsList.length;

			for (let i = 0; i < length; i++) {
				tag = tagsList[i];

				endResult.push(tag);
			}
		},

		_skipParse: false,

		_tagNameMatch(tagSrc, tagDest) {
			return (
				(tagDest instanceof RegExp && tagDest.test(tagSrc)) ||
				tagSrc === tagDest
			);
		},

		_verbatim: true,

		constructor: CreoleDataProcessor,

		toDataFormat(html) {
			const instance = this;

			const data = instance._convert(html);

			return data;
		},

		toHtml(data, config) {
			const instance = this;

			if (config) {
				const fragment = CKEDITOR.htmlParser.fragment.fromHtml(data);

				const writer = new CKEDITOR.htmlParser.basicWriter();

				config.filter.applyTo(fragment);

				fragment.writeHtml(writer);

				data = writer.getHtml();
			}
			else {
				const div = document.createElement('div');

				if (!instance._creoleParser) {
					instance._creoleParser = new CKEDITOR.CreoleParser({
						imagePrefix: attachmentURLPrefix,
					});
				}

				instance._creoleParser.parse(div, data);

				data = div.innerHTML;
			}

			return data || enterModeEmptyValue[instance._editor.enterMode];
		},
	};

	CKEDITOR.plugins.add('creole_data_processor', {
		init(editor) {
			attachmentURLPrefix = editor.config.attachmentURLPrefix;

			editor.dataProcessor = new CreoleDataProcessor(editor);

			editor.fire('customDataProcessorLoaded');
		},

		requires: ['htmlwriter'],
	});
})();
