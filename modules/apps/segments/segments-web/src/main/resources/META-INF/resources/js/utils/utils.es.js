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

import dateFns from 'date-fns';
import {getUid} from 'metal';

import {CONJUNCTIONS} from './constants.es';

const GROUP_ID_NAMESPACE = 'group_';

const SPLIT_REGEX = /({\d+})/g;

/**
 * Creates a new group object with items.
 * @param {Array} items The items to add to the new group.
 * @return {Object} The new group object.
 */
export const createNewGroup = items => ({
	conjunctionName: CONJUNCTIONS.AND,
	groupId: generateGroupId(),
	items
});

/**
 * Generates a unique group id.
 * @return {string} The unique id.
 */
export function generateGroupId() {
	return `${GROUP_ID_NAMESPACE}${getUid()}`;
}

/**
 * Uses the singular language key if the count is 1. Otherwise uses the plural
 * language key.
 * @param {string} singular The language key in singular form.
 * @param {string} plural The language key in plural form.
 * @param {number} count The amount to display in the message.
 * @param {boolean} toString If the message should be converted to a string.
 * @return {(string|Array)} The translated message.
 */
export function getPluralMessage(singular, plural, count = 0, toString) {
	const message = count === 1 ? singular : plural;

	return sub(message, [count], toString);
}

/**
 * Gets a list of group ids from a criteria object.
 * Used for disallowing groups to be moved into its own deeper nested groups.
 * Example of returned value: ['group_02', 'group_03']
 * @param {Object} criteria The criteria object to search through.
 * @return {Array}
 */
export function getChildGroupIds(criteria) {
	let childGroupIds = [];

	if (criteria.items && criteria.items.length) {
		childGroupIds = criteria.items.reduce((groupIdList, item) => {
			return item.groupId
				? [...groupIdList, item.groupId, ...getChildGroupIds(item)]
				: groupIdList;
		}, []);
	}

	return childGroupIds;
}

/**
 * Gets the list of operators for a supported type.
 * Used for displaying the operators available for each criteria row.
 * @param {Array} operators The full list of supported operators.
 * @param {Object} propertyTypes A map of property types and the operators
 * supported for each type.
 * @param {string} type The type to get the supported operators for.
 */
export function getSupportedOperatorsFromType(operators, propertyTypes, type) {
	return operators.filter(operator => {
		const validOperators = propertyTypes[type];

		return validOperators && validOperators.includes(operator.name);
	});
}

/**
 * Inserts an item into a list at the specified index.
 * @param {*} item The item that will be inserted.
 * @param {Array} list The list where the item will be inserted into.
 * @param {number} index The position where the item will be inserted.
 * @return {Array}
 */
export function insertAtIndex(item, list, index) {
	return [...list.slice(0, index), item, ...list.slice(index, list.length)];
}

/**
 * Converts an object of key value pairs to a form data object for passing
 * into a fetch body.
 * @param {Object} dataObject The data to be converted.
 */
export function objectToFormData(dataObject) {
	const formData = new FormData();

	Object.keys(dataObject).forEach(key => {
		formData.set(key, dataObject[key]);
	});

	return formData;
}

/**
 * Removes an item at the specified index.
 * @param {Array} list The list the where an item will be removed.
 * @param {number} index The position where the item will be removed.
 * @return {Array}
 */
export function removeAtIndex(list, index) {
	return list.filter((fItem, fIndex) => fIndex !== index);
}

/**
 * Replaces an item in a list at the specified index.
 * @param {*} item The item that will be added.
 * @param {Array} list The list where an item will be replaced.
 * @param {number} index The position where the item will be replaced.
 * @return {Array}
 */
export function replaceAtIndex(item, list, index) {
	return Object.assign(list, {
		[index]: item
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
 * @param {string} langKey This is the language key used from our properties file
 * @param {string} args Arguments to pass into language key
 * @param {string} join Boolean used to indicate whether to call `.join()` on
 * the array before it is returned. Use `false` if subbing in JSX.
 * @return {(string|Array)}
 */
export function sub(langKey, args, join = true) {
	const keyArray = langKey.split(SPLIT_REGEX).filter(val => val.length !== 0);

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
	ISOString,
	localeKey = navigator.language
) {
	const date = new Date(ISOString);

	const options = {
		day: 'numeric',
		month: 'long',
		year: 'numeric'
	};

	const intl = new Intl.DateTimeFormat(localeKey, options);

	return intl.format(date);
}

/**
 * Returns a YYYY-MM-DD date
 * based on a JS Date object
 *
 * @export
 * @param {Date} dateJsObject
 * @returns {string}
 */
export function jsDatetoYYYYMMDD(dateJsObject) {
	const DATE_FORMAT = 'YYYY-MM-DD';

	return dateFns.format(dateJsObject, DATE_FORMAT);
}
