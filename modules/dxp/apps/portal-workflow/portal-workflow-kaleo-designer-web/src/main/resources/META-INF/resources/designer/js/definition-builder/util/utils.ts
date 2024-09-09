/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function isObject(value: unknown) {
	if (typeof value === 'object' && value !== null && !Array.isArray(value)) {
		return true;
	}

	return false;
}

function isObjectEmpty(object: Record<string, unknown>) {
	if (!Object.keys(object).length) {
		return true;
	}

	return false;
}

function removeNewLine(string: string) {
	return string.replace(/\r?\n|\r/g, '');
}

function repeatSymbol(symbol: string, repetionNumber: number) {
	let string = '';

	for (let i = repetionNumber; i > 0; i--) {
		string += symbol;
	}

	return string;
}

function replaceTabSpaces(string: string) {
	return string.replace(/\t/g, ' ').trimStart().trimEnd();
}

function stringToBoolean(string: string) {
	if (string === 'false') {
		return false;
	}
	else if (string === 'true') {
		return true;
	}
	else {
		return false;
	}
}

function titleCase(string: string) {
	return string
		.toLowerCase()
		.split(' ')
		.map((word) => {
			return word.charAt(0).toUpperCase() + word.slice(1);
		})
		.join(' ');
}

function uncamelize(string: string, separator: string) {
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
	stringToBoolean,
	titleCase,
	uncamelize,
};
