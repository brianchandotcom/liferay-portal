import * as d3 from 'd3';
import moment from 'moment';
import momentDurationFormatSetup from 'moment-duration-format';
import {isNumber, round} from 'lodash';

momentDurationFormatSetup(moment);

/**
 * Remove Zero Precision
 * @param {string} str
 */
const removeZeroPrecision = str => str.replace(/\.(0)+(k|G|M)?$/, '$2');

/**
 * To Rounded Single Precision
 * @param {number} number
 */
export const toRounded = (number, precision = 1) => {
	const formatFixedPercent = d3.format(`3.${precision}f`);

	return removeZeroPrecision(formatFixedPercent(number).trim());
};

/**
 * To Locale
 * @param {number} number
 */
export const toLocale = number => {
	const formatToLocale = d3.format(',');

	return formatToLocale(number);
};

/**
 * To Thousands
 * Formats the given number to an abbreviated number if the value is
 * greater than or equal to 1000.
 * e.g. 1,656,000 => 1.66M
 * @param {number} number
 * @returns {string}
 */
export const toThousands = number =>
	toThousandsBase(number, factor => round(number * factor, 2));

export const toThousandsBase = (number, setFactor) => {
	if (!isNumber(number)) {
		return '';
	}

	if (number < 1e3) {
		return String(round(number, 2));
	}

	let factor = 1e-3;
	let suffix = 'K';

	if (number >= 1e6 && number < 1e9) {
		factor = 1e-6;
		suffix = 'M';
	} else if (number >= 1e9 && number < 1e12) {
		factor = 1e-9;
		suffix = 'B';
	} else if (number >= 1e12) {
		factor = 1e-12;
		suffix = 'T';
	}

	return `${setFactor(factor)}${suffix}`.toUpperCase();
};

/**
 * To Fixes Point
 * @param {number} number
 */
export const toFixedPoint = number => {
	const format = d3.format(',.0f');

	return removeZeroPrecision(format(number)).trim().toUpperCase();
};

/**
 * To Int
 * @param {string} str
 */
export const toInt = str => parseInt(str, 10);

/**
 * To Duration
 * @param {string} time
 * @param {string} measurement
 */
export const toDuration = (
	time,
	format = 'DD[d] hh[h] mm[m] ss[s]',
	measurement = 'milliseconds'
) => {
	if (time === 0) {
		format = 'DD[d] hh[h] mm[m] s[s]';
	}

	return moment.duration(time, measurement).format(format);
};

const multipliers = {
	B: 1000000000,
	K: 1000,
	M: 1000000
};

/**
 * Undo Thousands
 * @param {string} formatted
 */
export const undoThousands = formatted => {
	if (!formatted) {
		return 0;
	}

	const regex = /(\d+((.|,)\d+)?)([a-zA-Z])?/;
	const matches = formatted.match(regex);

	if (!matches) {
		return 0;
	}

	const number = parseFloat(matches[1]);
	const multiplier = matches[4];

	if (multiplier) {
		return number * (multipliers[multiplier] || 1);
	}

	return toInt(number);
};

/**
 * Calculates the percentage. Will return null if
 * the percentage is not finite, also truncates
 * to match de desired decimalPlaces quantity
 * @param {number} curVal
 * @param {number} totalVal
 * @param {number} decimalPlaces
 * @return {string|null} percentage
 */
export function getFinitePercent(
	curVal,
	totalVal,
	decimalPlaces = 1,
	applyTrunc = true
) {
	const percentage = (curVal / totalVal) * 100;

	if (isFinite(percentage)) {
		return applyTrunc ? percentage.toFixed(decimalPlaces) : percentage;
	}

	return null;
}
