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

/**
 * Filter an Array by checking if the String includes the query
 */
interface FilterArrayByQueryProps<T> {
	array: T[];
	creationLanguageId?: Locale;
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
