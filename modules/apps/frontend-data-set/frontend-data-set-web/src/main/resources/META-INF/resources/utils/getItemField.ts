/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX,
	FDS_NESTED_FIELD_NAME_DELIMITER,
	FDS_NESTED_FIELD_NAME_PARENT_SUFFIX,
} from '../constants';

/**
 * Drills down an object using the path
 * @param path : string
 * @param item : any
 * @returns The part of the item indicated by the path
 *
 * path: 'a.b.c'
 * item: {a: {b: {c: [1, 2, 3]}}}
 * returns: [1, 2, 3]
 *
 * path: 'a.b*'
 * item: {a: {b: {c: [1, 2, 3]}}}
 * returns: {c: [1, 2, 3]}
 *
 * path: 'a[].b.c'
 * item: {a: [{b: {c: 1, d: 3}}, {b: {c: 2, d: 3}}, {b: {c: 3, d: 3}}]}
 * returns: [{c:1}, {c: 2}, {c: 3}]
 */
export const getItemField = function (path: string, item: any) {
	const itemPath = path
		.replace(/\[\]/g, '.')
		.split(FDS_NESTED_FIELD_NAME_DELIMITER);

	if (
		path.includes(FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX) ||
		path.includes(FDS_NESTED_FIELD_NAME_PARENT_SUFFIX)
	) {
		itemPath.pop();
	}

	const fieldname: string = itemPath[itemPath.length - 1];
	let resolvedItem: any = undefined;

	if (itemPath.length > 1) {
		resolvedItem =
			itemPath.slice(0, -1).reduce((navigatedValue, chunk) => {
				if (Array.isArray(navigatedValue)) {
					const next = navigatedValue.map((value: any) => {
						return value[chunk];
					});

					return [].concat.apply([], next);
				}
				else if (navigatedValue.constructor.name === 'Object') {
					return navigatedValue[chunk];
				}
			}, item) || {};

		if (Array.isArray(resolvedItem)) {
			resolvedItem = pick(resolvedItem, fieldname);
		}
	}
	else {
		resolvedItem = item;
	}

	return resolvedItem;
};

/**
 * Extracts
 * @param collection : Array of objects
 * @param fieldname : Name of the object key to extract
 * @returns : Array of objects that include only the selected key
 *
 * Example
 * collection: [{a: 1, b: 1}, {a: 2, b: 1}]
 * fieldname: 'a'
 * result: [{a: 1}, {a: 2}]
 */
function pick<T, K extends keyof T>(
	collection: Array<T>,
	fieldname: K
): Array<{
	[K: string]: any;
}> {
	const key = fieldname;

	return collection.map((object: any) => {
		if (!object) {
			return {[key]: undefined};
		}

		const {[key]: value} = object;

		return {[key]: value};
	});
}
