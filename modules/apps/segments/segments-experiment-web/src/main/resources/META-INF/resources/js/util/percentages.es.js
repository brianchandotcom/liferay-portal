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

/**
 * Example: `0.22` => `22`
 */
export function indexToPercentageNumber(index) {
	return parseInt(index * 100, 10);
}

/**
 * Example: `0.22` => `"22%"`
 */
export function indexToPercentageString(index) {
	return indexToPercentageNumber(index) + '%';
}

/**
 * Example: `22` => `0.22`
 */
export function percentageNumberToIndex(percentageNumber) {
	const fixedIndexString = parseFloat(percentageNumber / 100).toFixed(2);

	return parseFloat(fixedIndexString);
}

export const MAX_CONFIDENCE_LEVEL = 99;
export const MIN_CONFIDENCE_LEVEL = 80;

export const INITIAL_CONFIDENCE_LEVEL = 95;
