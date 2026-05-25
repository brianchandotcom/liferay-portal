/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type ThresholdLevel = 'exceeded' | 'normal' | 'warning';

const WARNING_THRESHOLD = 0.75;

export function getMonthlyConsumptionPercent(
	allowance: number,
	consumed: number
): number {
	if (allowance <= 0) {
		return 100;
	}

	return Math.max(0, Math.min(Math.round((consumed / allowance) * 100), 100));
}

export function getMonthlyThreshold(
	allowance: number,
	consumed: number
): ThresholdLevel {
	if (allowance <= 0) {
		return 'exceeded';
	}

	const ratio = consumed / allowance;

	if (ratio >= 1) {
		return 'exceeded';
	}

	if (ratio >= WARNING_THRESHOLD) {
		return 'warning';
	}

	return 'normal';
}
