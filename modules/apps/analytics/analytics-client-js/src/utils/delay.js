/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Get Fibonnaci number.
 *
 * @param {Number} n - The position in Fibonnaci sequence.
 * @returns {Number} - Fibonnaci value at nth position.
 */
export function fib(n) {
	return n <= 1 ? 1 : fib(n - 1) + fib(n - 2);
}

/**
 * Calculate retry delay in milliseconds, bounded
 * by a miniumum and maximum value.
 *
 * @param {Number} attemptNumber - The current attempt number.
 * @param {Number} maxAttempts - The maximum number of attempts to limit delay increase.
 * @returns {Number} - Retry delay in milliseconds.
 */
export function getRetryDelay(attemptNumber, maxAttempts) {
	return fib(Math.min(attemptNumber, maxAttempts)) * 1000;
}
