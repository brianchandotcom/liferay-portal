/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function isObject(value) {
	if (typeof value === 'object' && value !== null && !Array.isArray(value)) {
		return true;
	}

	return false;
}

function isObjectEmpty(object) {
	if (!Object.keys(object).length) {
		return true;
	}

	return false;
}

function removeNewLine(string) {
	return string.replace(/\r?\n|\r/g, '');
}

function repeatSymbol(symbol, repetionNumber) {
	let string = '';

	for (let i = repetionNumber; i > 0; i--) {
		string += symbol;
	}

	return string;
}

function replaceTabSpaces(string) {
	return string.replace(/\t/g, ' ').trimStart().trimEnd();
}

function titleCase(string) {
	return string
		.toLowerCase()
		.split(' ')
		.map((word) => {
			return word.charAt(0).toUpperCase() + word.slice(1);
		})
		.join(' ');
}

function uncamelize(string, separator) {
	if (!separator) {
		separator = '_';
	}

	return string
		.replace(/([a-z\d])([A-Z])/g, '$1' + separator + '$2')
		.replace(/([A-Z]+)([A-Z][a-z\d]+)/g, '$1' + separator + '$2')
		.toLowerCase();
}

export {
	isObject,
	isObjectEmpty,
	removeNewLine,
	repeatSymbol,
	replaceTabSpaces,
	titleCase,
	uncamelize,
};
