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

const REGEX = /([/])?(?:(?::(\w+)(?:\(((?:\\.|[^\\()])*)\))?|\(((?:\\.|[^\\()])+)\))([+*?])?)/g;

/**
 * Converts the given array of regex matches to a more readable object format.
 * @param {!Array<string>} matches
 * @return {!Object}
 */
function convertMatchesToObj(matches) {
	return {
		match: matches[0],
		modifier: matches[5],
		name: matches[2],
		paramPattern: matches[3],
		prefix: matches[1],
		unnamedPattern: matches[4],
	};
}

/**
 * Converts the given tokens parsed from a route format string to a regex.
 * @param {!Array<string|!Object>} tokens
 * @return {!RegExp}
 */
function convertTokensToRegex(tokens) {
	let regex = '';
	for (let i = 0; i < tokens.length; i++) {
		if (typeof tokens[i] === 'string') {
			regex += escape(tokens[i]);
		}
		else {
			let capture = encloseNonCapturingGroup(tokens[i].pattern);
			if (tokens[i].repeat) {
				capture += encloseNonCapturingGroup('\\/' + capture) + '*';
			}
			capture = escape(tokens[i].prefix) + `(${capture})`;
			if (tokens[i].optional) {
				if (!tokens[i].partial) {
					capture = encloseNonCapturingGroup(capture);
				}
				capture += '?';
			}
			regex += capture;
		}
	}

	return new RegExp('^' + makeTrailingSlashOptional(regex) + '$');
}

/**
 * Encloses the given regex pattern into a non capturing group.
 * @param {string} pattern
 * @return {string}
 */
function encloseNonCapturingGroup(pattern) {
	return `(?:${pattern})`;
}

/**
 * Escapes the given string to show up in the path regex.
 * @param {string} str
 * @return {string}
 */
function escape(str) {
	return str.replace(/([.+*?=^!:${}()[]|\/\\])/g, '\\$1');
}

/**
 * Makes trailing slash optional on paths.
 * @param {string} regex
 * @param {string}
 */
function makeTrailingSlashOptional(regex) {
	if (regex.endsWith('/')) {
		regex += '?';
	}
	else {
		regex += '\\/?';
	}

	return regex;
}

/**
 * Parses the given route format string into tokens representing its contents.
 * @param {!Array|string} routeOrTokens Either a route format string or tokens
 *     previously parsed via the `parse` function.
 * @return {!Array<string|!Object>} An array of tokens that can be either plain
 *     strings (part of the route) or objects containing informations on params.
 */
export function parse(routeOrTokens) {
	if (typeof routeOrTokens !== 'string') {
		return routeOrTokens;
	}

	const route = routeOrTokens;
	let unnamedCount = 0;
	const tokens = [];
	let currPath = '';
	let index = 0;

	let matches = REGEX.exec(route);
	while (matches) {
		const data = convertMatchesToObj(matches);

		currPath = route.slice(index, matches.index);
		index = matches.index + data.match.length;
		tokens.push(currPath);

		tokens.push({
			name: data.name ? data.name : '' + unnamedCount++,
			optional: data.modifier === '*' || data.modifier === '?',
			partial: route[index] && route[index] !== data.prefix,
			pattern: data.paramPattern || data.unnamedPattern || '[^\\/]+',
			prefix: data.prefix || '',
			repeat: data.modifier === '*' || data.modifier === '+',
		});

		matches = REGEX.exec(route);
	}

	if (index < route.length) {
		tokens.push(route.substr(index));
	}

	return tokens;
}

/**
 * Converts the given route format string to a regex that can extract param
 * data from paths matching it.
 * @param {!Array|string} routeOrTokens Either a route format string or tokens
 *     previously parsed via the `parse` function.
 * @return {!RegExp}
 */
export function toRegex(routeOrTokens) {
	return convertTokensToRegex(parse(routeOrTokens));
}

/**
 * Extracts data from the given path according to the specified route format.
 * @param {!Array|string} routeOrTokens Either a route format string or tokens
 *     previously parsed via the `parse` function.
 * @param {string} The path to extract param data from.
 * @return {Object<string, string>} The data object, or null if the path doesn't
 *     match the given format.
 */
export function extractData(routeOrTokens, path) {
	const data = {};
	const tokens = parse(routeOrTokens);
	const match = path.match(convertTokensToRegex(tokens));

	if (!match) {
		return null;
	}

	let paramIndex = 1;
	for (let i = 0; i < tokens.length; i++) {
		if (typeof tokens[i] !== 'string') {
			let value = match[paramIndex++];
			if (value !== undefined) {
				if (tokens[i].repeat) {
					value = value.split('/');
				}
				data[tokens[i].name] = value;
			}
		}
	}

	return data;
}
