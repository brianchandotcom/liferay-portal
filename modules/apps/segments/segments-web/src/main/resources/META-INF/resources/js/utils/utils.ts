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

import {format, isValid, parseISO} from 'date-fns';

import {Criteria} from '../../types/Criteria';
import {CONJUNCTIONS, PropertyType} from './constants';

const GROUP_ID_NAMESPACE = 'group_';

const SPLIT_REGEX = /({\d+})/g;

/**
 * Creates a new group object with items.
 */
export function createNewGroup(items: Criteria['items']): Criteria {
	return {
		conjunctionName: CONJUNCTIONS.AND,
		groupId: generateGroupId(),
		items,
	};
}

let uniqueIdCounter_ = 1;

/**
 * Generates a unique group id.
 */
export function generateGroupId() {
	return `${GROUP_ID_NAMESPACE}${uniqueIdCounter_++}`;
}

/**
 * Uses the singular language key if the count is 1. Otherwise uses the plural
 * language key.
 */
export function getPluralMessage(
	singular: string,
	plural: string,
	count: number = 0,
	toString: boolean = false
) {
	const message = count === 1 ? singular : plural;

	return sub(message, [count.toString()], toString);
}

/**
 * Gets a list of group ids from a criteria object.
 * Used for disallowing groups to be moved into its own deeper nested groups.
 * Example of returned value: ['group_02', 'group_03']
 */
export function getChildGroupIds(criteria: Criteria) {
	let childGroupIds: string[] = [];

	if (criteria.items && criteria.items.length) {
		childGroupIds = criteria.items.reduce((groupIdList: string[], item) => {
			return 'groupId' in item
				? [...groupIdList, item.groupId, ...getChildGroupIds(item)]
				: groupIdList;
		}, []);
	}

	return childGroupIds;
}

/**
 * Gets the list of operators for a supported type.
 * Used for displaying the operators available for each criteria row.
 */
export function getSupportedOperatorsFromType<
	Operator extends {name: string},
	PropertyKey extends string
>(
	operators: Operator[],
	propertyTypes: Record<PropertyKey, PropertyType>,
	type: PropertyKey
) {
	return operators.filter((operator) => {
		const validOperators = propertyTypes[type];

		return validOperators && validOperators.includes(operator.name);
	});
}

/**
 * Inserts an item into a list at the specified index.
 */
export function insertAtIndex<T>(item: T, list: T[], index: number) {
	return [...list.slice(0, index), item, ...list.slice(index, list.length)];
}

/**
 * Converts an object of key value pairs to a form data object for passing
 * into a fetch body.
 */
export function objectToFormData(dataObject: Record<string, string | Blob>) {
	const formData = new FormData();

	Object.keys(dataObject).forEach((key) => {
		formData.set(key, dataObject[key]);
	});

	return formData;
}

/**
 * Removes an item at the specified index.
 */
export function removeAtIndex<T>(list: T[], index: number) {
	return list.filter((fItem, fIndex) => fIndex !== index);
}

/**
 * Replaces an item in a list at the specified index.
 */
export function replaceAtIndex<T>(item: T, list: T[], index: number) {
	return Object.assign(list, {
		[index]: item,
	});
}

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
export function sub(langKey: string, args: string[], join = true) {
	const keyArray = langKey
		.split(SPLIT_REGEX)
		.filter((val) => val.length !== 0);

	for (let i = 0; i < args.length; i++) {
		const arg = args[i];

		const indexKey = `{${i}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, arg);

			argIndex = keyArray.indexOf(indexKey);
		}
	}

	return join ? keyArray.join('') : keyArray;
}

export function dateToInternationalHuman(
	ISOString: string,
	localeKey = Liferay.ThemeDisplay.getBCP47LanguageId()
) {
	const date = new Date(ISOString);

	const intl = new Intl.DateTimeFormat(localeKey, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	});

	return intl.format(date);
}

/**
 * Returns a YYYY-MM-DD date
 * based on a JS Date object
 */
export function jsDatetoYYYYMMDD(dateJsObject: string | Date) {
	if (!isValid(dateJsObject)) {
		dateJsObject = parseISO(dateJsObject as string);
	}

	return format(dateJsObject as Date, 'yyyy-MM-dd');
}
