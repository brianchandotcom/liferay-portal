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

import {isNode} from 'react-flow-renderer';

function getCollidingElements(elements, newElementPosition) {
	const collidingElements = [];

	elements.forEach((element) => {
		if (
			isNode(element) &&
			isOverlapping(element.position, newElementPosition)
		) {
			collidingElements.push(element.id);
		}
	});

	return collidingElements;
}

function isObject(value) {
	if (typeof value === 'object' && value !== null && !Array.isArray(value)) {
		return true;
	}

	return false;
}

function isObjectEmpty(object) {
	if (Object.keys(object).length === 0) {
		return true;
	}

	return false;
}

function isOverlapping(elementPosition, newElementPosition) {
	const isInHorizontalBounds =
		newElementPosition.x < elementPosition.x + 280 &&
		newElementPosition.x + 280 > elementPosition.x;

	const isInVerticalBounds =
		newElementPosition.y < elementPosition.y + 100 &&
		newElementPosition.y + 100 > elementPosition.y;

	const isOverlapping = isInHorizontalBounds && isInVerticalBounds;

	return isOverlapping;
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
	getCollidingElements,
	isObject,
	isObjectEmpty,
	removeNewLine,
	repeatSymbol,
	replaceTabSpaces,
	uncamelize,
};
