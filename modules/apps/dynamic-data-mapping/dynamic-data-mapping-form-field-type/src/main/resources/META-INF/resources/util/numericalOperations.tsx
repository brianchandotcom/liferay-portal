/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const NON_NUMERIC_REGEX = /[\D]/g;

export const limitValue = ({
	defaultValue,
	max,
	min,
	value,
}: {
	defaultValue: number;
	max: number;
	min: number;
	value: number;
}) => {
	if (isNaN(value) || value < min) {
		return defaultValue;
	}
	else if (value > max) {
		return max;
	}

	return value;
};

export const trimLeftZero: (options: {
	decimalSymbol: string;
	thousandsSeparator: string | null | undefined;
	value: string;
}) => string = ({decimalSymbol, thousandsSeparator, value}) => {
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
};
