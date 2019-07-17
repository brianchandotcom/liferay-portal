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

import {isString} from 'metal';

const NEW_LINE = '\r\n';

const REGEX_DECLARATIVE_CLOSE = /-->|\]>/;

const REGEX_DECLARATIVE_OPEN = /<!/;

const REGEX_DIRECTIVE = /<\?/;

const REGEX_DOCTYPE = /!DOCTYPE/;

const REGEX_ELEMENT = /^<\w/;

const REGEX_ELEMENT_CLOSE = /^<\/\w/;

const REGEX_ELEMENT_NAMESPACED = /^<[\w:\-.,]+/;

const REGEX_ELEMENT_NAMESPACED_CLOSE = /^<\/[\w:\-.,]+/;

const REGEX_ELEMENT_OPEN = /<\w/;

const REGEX_NAMESPACE_XML = /xmlns(?::|=)/g;

const REGEX_NAMESPACE_XML_ATTR = /\s*(xmlns)(:|=)/g;

const REGEX_TAG_CLOSE = /<\//;

const REGEX_TAG_OPEN = /</g;

const REGEX_TAG_SINGLE_CLOSE = /\/>/;

const REGEX_WHITESPACE_BETWEEN_TAGS = />\s+</g;

const STR_BLANK = '';

const STR_TOKEN = '~::~';

const TAG_INDENT = '\t';

const DEFAULT_OPTIONS = {
	newLine: NEW_LINE,
	tagIndent: TAG_INDENT
};

/**
 * Returns a formatted XML
 * @param {!String} content String to format
 * @param {Object} options Optional parameter that can accept provided options
 * @return {!String} Formatted content
 */
export default function formatXML(content, options = {}) {
	const {newLine, tagIndent} = {
		...DEFAULT_OPTIONS,
		...options
	};

	if (!isString(content)) {
		throw new TypeError('Parameter content must be a string');
	}

	content = content.trim();
	content = content.replace(REGEX_WHITESPACE_BETWEEN_TAGS, '><');
	content = content.replace(REGEX_TAG_OPEN, STR_TOKEN + '<');
	content = content.replace(REGEX_NAMESPACE_XML_ATTR, STR_TOKEN + '$1$2');

	let commentCounter = 0;
	let inComment = false;
	const items = content.split(STR_TOKEN);
	let level = 0;
	let result = '';

	items.forEach((item, index) => {
		if (REGEX_DECLARATIVE_OPEN.test(item)) {
			result += indent(level, newLine, tagIndent) + item;

			commentCounter++;

			inComment = true;

			if (
				REGEX_DECLARATIVE_CLOSE.test(item) ||
				REGEX_DOCTYPE.test(item)
			) {
				commentCounter--;

				inComment = commentCounter !== 0;
			}
		} else if (REGEX_DECLARATIVE_CLOSE.test(item)) {
			result += item;

			commentCounter--;

			inComment = commentCounter !== 0;
		} else if (
			REGEX_ELEMENT.exec(items[index - 1]) &&
			REGEX_ELEMENT_CLOSE.exec(item) &&
			REGEX_ELEMENT_NAMESPACED.exec(items[index - 1]) ==
				REGEX_ELEMENT_NAMESPACED_CLOSE.exec(item)[0].replace(
					'/',
					STR_BLANK
				)
		) {
			result += item;

			if (!inComment) {
				--level;
			}
		} else if (
			REGEX_ELEMENT_OPEN.test(item) &&
			!REGEX_TAG_CLOSE.test(item) &&
			!REGEX_TAG_SINGLE_CLOSE.test(item)
		) {
			if (inComment) {
				result += item;
			} else {
				result += indent(level++, newLine, tagIndent) + item;
			}
		} else if (
			REGEX_ELEMENT_OPEN.test(item) &&
			REGEX_TAG_CLOSE.test(item)
		) {
			if (inComment) {
				result += item;
			} else {
				result += indent(level, newLine, tagIndent) + item;
			}
		} else if (REGEX_TAG_CLOSE.test(item)) {
			if (inComment) {
				result += item;
			} else {
				result += indent(--level, newLine, tagIndent) + item;
			}
		} else if (REGEX_TAG_SINGLE_CLOSE.test(item)) {
			if (inComment) {
				result += item;
			} else {
				result += indent(level, newLine, tagIndent) + item;
			}
		} else if (REGEX_DIRECTIVE.test(item)) {
			result += indent(level, newLine, tagIndent) + item;
		} else if (REGEX_NAMESPACE_XML) {
			result += indent(level, newLine, tagIndent) + item;
		} else {
			result += item;
		}

		if (new RegExp('^' + newLine).test(result)) {
			result = result.slice(newLine.length);
		}
	});

	return result;
}

/**
 * Returns a string for starting a new line at the specified indent level
 * @param {number} level The level of indentation
 * @return {String} Return a string for starting a new line at the specified indent level
 */
function indent(level, newLine, tagIndent) {
	return newLine + new Array(level + 1).join(tagIndent);
}
