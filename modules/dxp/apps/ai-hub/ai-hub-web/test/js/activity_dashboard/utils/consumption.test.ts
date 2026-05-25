/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getMonthlyConsumptionPercent,
	getMonthlyThreshold,
} from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/utils/consumption';

describe('getMonthlyConsumptionPercent', () => {
	it('caps at 100 above the allowance', () => {
		expect(getMonthlyConsumptionPercent(10000, 12000)).toBe(100);
	});

	it('returns 0 at 0% of the allowance', () => {
		expect(getMonthlyConsumptionPercent(10000, 0)).toBe(0);
	});

	it('returns 0 when the consumed value is negative', () => {
		expect(getMonthlyConsumptionPercent(10000, -500)).toBe(0);
	});

	it('returns 100 when the allowance is negative', () => {
		expect(getMonthlyConsumptionPercent(-1, 100)).toBe(100);
	});

	it('returns 100 when the allowance is zero', () => {
		expect(getMonthlyConsumptionPercent(0, 0)).toBe(100);
	});

	it('returns the rounded percentage of the consumed allowance', () => {
		expect(getMonthlyConsumptionPercent(10000, 5000)).toBe(50);
	});

	it('rounds fractional percentages to the nearest integer', () => {
		expect(getMonthlyConsumptionPercent(10000, 7549)).toBe(75);
		expect(getMonthlyConsumptionPercent(10000, 7550)).toBe(76);
	});
});

describe('getMonthlyThreshold', () => {
	it('returns "exceeded" above 100% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 12000)).toBe('exceeded');
	});

	it('returns "exceeded" at 100% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 10000)).toBe('exceeded');
	});

	it('returns "exceeded" when the allowance is negative', () => {
		expect(getMonthlyThreshold(-1, 100)).toBe('exceeded');
	});

	it('returns "exceeded" when the allowance is zero', () => {
		expect(getMonthlyThreshold(0, 0)).toBe('exceeded');
	});

	it('returns "normal" at 0% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 0)).toBe('normal');
	});

	it('returns "normal" at 50% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 5000)).toBe('normal');
	});

	it('returns "normal" just under the 75% warning boundary', () => {
		expect(getMonthlyThreshold(10000, 7499)).toBe('normal');
	});

	it('returns "warning" at 75% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 7500)).toBe('warning');
	});

	it('returns "warning" at 90% of the allowance', () => {
		expect(getMonthlyThreshold(10000, 9000)).toBe('warning');
	});

	it('returns "warning" just under the 100% exceeded boundary', () => {
		expect(getMonthlyThreshold(10000, 9999)).toBe('warning');
	});
});
