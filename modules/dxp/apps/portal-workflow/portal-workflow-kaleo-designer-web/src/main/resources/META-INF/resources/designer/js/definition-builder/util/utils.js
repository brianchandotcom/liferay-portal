/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

function repeatSymbol(symbol, repetionNumber) {
	let string = '';

	for (let i = repetionNumber; i > 0; i--) {
		string += symbol;
	}

	return string;
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

export {repeatSymbol, uncamelize};
