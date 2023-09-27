/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Example:
 * en-US: `123456` => `123,456`
 * es-ES: `123456` => `123.456`
 */
interface Options {
	compactThreshold?: number;
	useCompact?: boolean;
}
export declare function numberFormat(
	languageTag: string,
	number: number,
	options?: Options
): string;
export {};
