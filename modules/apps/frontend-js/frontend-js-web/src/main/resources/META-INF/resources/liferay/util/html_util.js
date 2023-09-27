/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
