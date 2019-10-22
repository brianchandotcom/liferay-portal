/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
