/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const NON_NUMERIC_REGEX = /[\D]/g;

export function limitValue({
	defaultValue,
	max,
	min,
	value,
}: {
	defaultValue: number;
	max: number;
	min: number;
	value: number;
}) {
	if (isNaN(value) || value < min) {
		return defaultValue;
	}
	else if (value > max) {
		return max;
	}

	return value;
}

export function trimLeftZero({
	decimalSymbol,
	thousandsSeparator,
	value,
}: {
	decimalSymbol: string;
	thousandsSeparator: string | null | undefined;
	value: string;
}): string {
	if (
		value.length > 1 &&
		(value[0] === '0' || value[0] === thousandsSeparator) &&
		!value[1].match(NON_NUMERIC_REGEX)
	) {
		if (value[0] === '0') {
			value = value.replace('0', '');
		}
		else if (thousandsSeparator) {
			value = value.replace(thousandsSeparator, '');
		}

		return trimLeftZero({
			decimalSymbol,
			thousandsSeparator,
			value,
		});
	}

	return value;
}
