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

const ESCAPE_REGEX = /([.*+?^$(){}|[\]/\\])/g;

// See: https://github.com/liferay/alloy-ui/blob/835547dd7302c7cd7f5d0c329230f9dd15b93d62/src/aui-base/js/aui-lang.js#L216-L219

function escapeRegEx(str) {
	return str.replace(ESCAPE_REGEX, '\\$1');
}

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

Object.keys(MAP_HTML_CHARS_ESCAPED).forEach(([char, escapedChar]) => {
	MAP_HTML_CHARS_UNESCAPED[escapedChar] = char;
});

const HTML_ESCAPED_VALUES = Object.values(MAP_HTML_CHARS_ESCAPED);

const HTML_UNESCAPED_VALUES = Object.keys(MAP_HTML_CHARS_ESCAPED);

const HTML_ESCAPE = new RegExp('[' + HTML_UNESCAPED_VALUES.join('') + ']', 'g');

export default function escapeHTML(string, preventDoubleEscape, entities) {
	let regex = HTML_ESCAPE;

	const entitiesList = [];
	let entitiesValues;

	if (entities && typeof entities === 'object') {
		entitiesValues = [];

		Object.keys(entities).forEach(([char, escapedChar]) => {
			entitiesList.push(escapedChar);

			entitiesValues.push(char);
		});

		regex = new RegExp('[' + escapeRegEx(entitiesList.join('')) + ']', 'g');
	}
	else {
		entities = MAP_HTML_CHARS_ESCAPED;

		entitiesValues = HTML_ESCAPED_VALUES;
	}

	return string.replace(regex, (match, offset, string) => {
		let result;

		if (preventDoubleEscape) {
			const nextSemicolonIndex = string.indexOf(';', offset);

			if (nextSemicolonIndex >= 0) {
				const entity = string.substring(offset, nextSemicolonIndex + 1);

				if (entitiesValues.indexOf(entity) >= 0) {
					result = match;
				}
			}
		}

		if (!result) {
			result = entities[match];
		}

		return result;
	});
}
