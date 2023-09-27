/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type indexOfPipedChars = string | number;
export declare function createAutoCorrectedDatePipe(
	dateFormat?: string,
	{
		maxYear,
		minYear,
	}?: {
		maxYear?: number | undefined;
		minYear?: number | undefined;
	}
): (
	conformedValue: string
) =>
	| false
	| {
			indexesOfPipedChars: indexOfPipedChars[];
			value: string;
	  };
export {};
