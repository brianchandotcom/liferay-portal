/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria} from '../../types/Criteria';
import {PropertyType} from './constants';

/**
 * Creates a new group object with items.
 */
export declare function createNewGroup(items: Criteria['items']): Criteria;

/**
 * Generates a unique group id.
 */
export declare function generateGroupId(): string;

/**
 * Uses the singular language key if the count is 1. Otherwise uses the plural
 * language key.
 */
export declare function getPluralMessage(
	singular: string,
	plural: string,
	count?: number,
	toString?: boolean
): string | string[];

/**
 * Gets a list of group ids from a criteria object.
 * Used for disallowing groups to be moved into its own deeper nested groups.
 * Example of returned value: ['group_02', 'group_03']
 */
export declare function getChildGroupIds(criteria: Criteria): string[];

/**
 * Gets the list of operators for a supported type.
 * Used for displaying the operators available for each criteria row.
 */
export declare function getSupportedOperatorsFromType<
	Operator extends {
		name: string;
	},
	PropertyKey extends string
>(
	operators: Operator[],
	propertyTypes: Record<PropertyKey, PropertyType>,
	type: PropertyKey
): Operator[];

/**
 * Inserts an item into a list at the specified index.
 */
export declare function insertAtIndex<T>(
	item: T,
	list: T[],
	index: number
): T[];

/**
 * Converts an object of key value pairs to a form data object for passing
 * into a fetch body.
 */
export declare function objectToFormData(
	dataObject: Record<string, string | Blob>
): FormData;

/**
 * Removes an item at the specified index.
 */
export declare function removeAtIndex<T>(list: T[], index: number): T[];

/**
 * Replaces an item in a list at the specified index.
 */
export declare function replaceAtIndex<T>(
	item: T,
	list: T[],
	index: number
): T[] & {
	[x: number]: T;
};

/**
 * Utility function for substituting variables into language keys.
 *
 * Examples:
 * sub(Liferay.Language.get('search-x'), ['all'])
 * => 'search all'
 * sub(Liferay.Language.get('search-x'), [<b>all<b>], false)
 * => 'search <b>all</b>'
 *
 * Join boolean is used to indicate whether to call `.join()` on
 * the array before it is returned. Use `false` if subbing in JSX.
 */
export declare function sub(
	langKey: string,
	args: string[],
	join?: boolean
): string | string[];
export declare function dateToInternationalHuman(
	ISOString: string,
	localeKey?: string
): string;

/**
 * Returns a YYYY-MM-DD date
 * based on a JS Date object
 */
export declare function jsDatetoYYYYMMDD(dateJsObject: string | Date): string;
