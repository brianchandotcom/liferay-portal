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

const htmlEscapedValues = [];

const htmlUnescapedValues = [];

Object.entries(MAP_HTML_CHARS_ESCAPED).forEach((entry) => {
	MAP_HTML_CHARS_UNESCAPED[entry[1]] = entry[0];

	htmlEscapedValues.push(entry[1]);
	htmlUnescapedValues.push(entry[0]);
});

const LEFT_SQUARE_BRACKET = '[';
const RIGHT_SQUARE_BRACKET = ']';

const HTML_ESCAPE = new RegExp(
	LEFT_SQUARE_BRACKET + htmlUnescapedValues.join('') + RIGHT_SQUARE_BRACKET,
	'g'
);

function _escapeHTML(preventDoubleEscape, entities, entitiesValues, match) {
	let result;

	if (preventDoubleEscape) {
		const argumentsArray = Array.from(arguments);
		const length = argumentsArray.length;

		const offset = argumentsArray[length - 2];
		const string = argumentsArray[length - 1];

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
}

export default function escapeHTML(string, preventDoubleEscape, entities) {
	let regex = HTML_ESCAPE;

	const entitiesList = [];
	let entitiesValues;

	if (entities && typeof entities === 'object') {
		entitiesValues = [];

		Object.keys(entities).forEach((entry) => {
			entitiesList.push(entry[1]);

			entitiesValues.push(entry[0]);
		});

		regex = new RegExp(
			LEFT_SQUARE_BRACKET +
				escapeRegEx(entitiesList.join('')) +
				RIGHT_SQUARE_BRACKET,
			'g'
		);
	}
	else {
		entities = MAP_HTML_CHARS_ESCAPED;

		entitiesValues = htmlEscapedValues;
	}

	return string.replace(
		regex,
		_escapeHTML.bind(null, !!preventDoubleEscape, entities, entitiesValues)
	);
}
