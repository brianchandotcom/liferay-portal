/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Filter an Array by checking if the String includes the query
 */
interface FilterArrayByQueryProps<T> {
	array: T[];
	creationLanguageId?: Liferay.Language.Locale;
	query: string;
	str: string;
}
export declare function filterArrayByQuery<T>({
	array,
	creationLanguageId,
	query,
	str,
}: FilterArrayByQueryProps<T>): T[];
export {};
