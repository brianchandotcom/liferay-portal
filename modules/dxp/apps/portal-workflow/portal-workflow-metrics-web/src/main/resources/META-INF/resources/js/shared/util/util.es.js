/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatNumber} from './numeral.es';

/**
 * Return true if number is valid
 * @param {number} number
 */
const isValidNumber = (number) => {
	return !isNaN(number) && number !== Infinity ? true : false;
};

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

const capitalize = (str) =>
	str.replace(/^\w/, (letter) => letter.toUpperCase());

const toUppercase = (str) => (str && str.length ? str.toUpperCase() : str);

const getSLAStatusIconInfo = (slaStatus) => {
	const items = {
		OnTime: {
			bgColor: 'bg-success-light',
			name: 'check-circle',
			textColor: 'text-success',
		},
		Overdue: {
			bgColor: 'bg-danger-light',
			name: 'exclamation-circle',
			textColor: 'text-danger',
		},
		Untracked: {
			bgColor: 'bg-info-light',
			name: 'hr',
			textColor: 'text-info',
		},
	};

	return items[slaStatus];
};

export {
	capitalize,
	getFormattedPercentage,
	getPercentage,
	getSLAStatusIconInfo,
	isValidNumber,
	toUppercase,
};
