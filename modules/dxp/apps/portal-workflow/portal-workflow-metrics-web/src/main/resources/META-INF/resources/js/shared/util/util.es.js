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

import {formatNumber} from './numeral.es';

/**
 * Returns the percent number passing as
 * parameter the current number and total number.
 * @param {number} number1
 * @param {number} number2
 * @returns {number}
 */
const getPercentage = (number1, number2) => {
	const result = number1 / number2;

	return isValidNumber(result) ? result : 0;
};

/**
 * Returns the formatted percent number passing as
 * parameter the current number and total number.
 * @param {number} number1
 * @param {number} number2
 * @returns {number}
 */
const getFormattedPercentage = (number1, number2) => {
	const percentage = getPercentage(number1, number2);

	return formatNumber(percentage, '0[.]00%');
};

/**
 * Return true if number is valid
 * @param {number} number
 */
const isValidNumber = number => {
	return !isNaN(number) && number !== Infinity ? true : false;
};

const capitalize = str => str.replace(/^\w/, letter => letter.toUpperCase());

const toUppercase = str => (str && str.length ? str.toUpperCase() : str);

export {
	capitalize,
	getFormattedPercentage,
	getPercentage,
	isValidNumber,
	toUppercase
};
