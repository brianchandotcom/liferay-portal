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

const MAP_HTML_CHARS_ESCAPED = {
	'"': '&#034;',
	'&': '&amp;',
	"'": '&#039;',
	'/': '&#047;',
	'<': '&lt;',
	'>': '&gt;',
	'`': '&#096;',
};

export {MAP_HTML_CHARS_ESCAPED};

const MAP_HTML_CHARS_UNESCAPED = {};

Object.entries(MAP_HTML_CHARS_ESCAPED).forEach(([char, escapedChar]) => {
	MAP_HTML_CHARS_UNESCAPED[escapedChar] = char;
});

const HTML_UNESCAPED_VALUES = Object.keys(MAP_HTML_CHARS_ESCAPED);

const HTML_ESCAPE = new RegExp(`[${HTML_UNESCAPED_VALUES.join('')}]`, 'g');

const HTML_UNESCAPE = /&([^\s;]+);/g;

export function escapeHTML(string) {
	return string.replace(
		HTML_ESCAPE,
		(match) => MAP_HTML_CHARS_ESCAPED[match]
	);
}

export function unescapeHTML(string) {
	return string.replace(HTML_UNESCAPE, (match) => {
		return new DOMParser().parseFromString(match, 'text/html')
			.documentElement.textContent;
	});
}
