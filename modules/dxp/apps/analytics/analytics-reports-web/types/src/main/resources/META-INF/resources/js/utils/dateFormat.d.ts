/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function generateDateFormatters(
	key: string
): {
	formatChartTitle: ([initialDate, finalDate]: Date[]) => string;
	formatLongDate: (value: string) => string;
	formatNumericDay: (value: string) => string;
	formatNumericHour: (value: string) => string;
};
