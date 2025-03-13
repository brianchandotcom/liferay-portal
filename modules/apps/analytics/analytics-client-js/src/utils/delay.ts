/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Get Fibonnaci number.
 */
export function fib(n: number): number {
	return n <= 1 ? 1 : fib(n - 1) + fib(n - 2);
}

/**
 * Calculate retry delay in milliseconds, bounded
 * by a miniumum and maximum value.
 */
export function getRetryDelay(
	attemptNumber: number,
	maxAttempts: number
): number {
	return fib(Math.min(attemptNumber, maxAttempts)) * 1000;
}
