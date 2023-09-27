/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function limitValue({
	defaultValue,
	max,
	min,
	value,
}: {
	defaultValue: number;
	max: number;
	min: number;
	value: number;
}): number;
export declare function trimLeftZero({
	decimalSymbol,
	thousandsSeparator,
	value,
}: {
	decimalSymbol: string;
	thousandsSeparator: string | null | undefined;
	value: string;
}): string;
