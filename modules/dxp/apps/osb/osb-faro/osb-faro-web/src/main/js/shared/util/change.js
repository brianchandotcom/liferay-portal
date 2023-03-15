import {isFinite, isNaN} from 'lodash';

export function formatChange(change) {
	if (change === 0) {
		return change;
	}

	return change > 0
		? `+${change.toLocaleString()}`
		: `-${Math.abs(change).toLocaleString()}`;
}

/**
 * Get the percentage change. Will return null if
 * the change is not finite.
 * @param {number} curVal
 * @param {number} prevVal
 * @return {string|null} percentage change
 */
export function getFinitePercentChange(curVal, prevVal) {
	const percentage = ((curVal - prevVal) / prevVal) * 100;

	if (isFinite(percentage)) {
		return percentage.toFixed(1);
	}

	return null;
}

/**
 * Get net change value for display.
 * @param {number} prev - The previous value.
 * @param {number} current - The current value.
 * @return {[string, number]} An array with the first item being
 * the string representation of net change and the second item
 * being the numerical percentage change.
 */
export function getNetChange(prev, current) {
	const change = current - prev;

	if (!change) {
		return;
	}

	const percentage = Math.abs(Math.floor((change / prev) * 100));

	return [
		change > 0
			? `+${change.toLocaleString()}`
			: `-${Math.abs(change).toLocaleString()}`,
		isFinite(percentage) ? percentage : isNaN(percentage) ? 0 : 100
	];
}

/**
 * if change is NaN, return zero, otherwise, return as a number.
 * @param {number!} change - Change represented as a number.
 */
export function getSafeChange(change) {
	return isNaN(Number(change)) ? 0 : Number(change);
}
