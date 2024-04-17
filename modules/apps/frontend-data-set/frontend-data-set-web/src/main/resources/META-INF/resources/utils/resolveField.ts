/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FDS_ARRAY_FIELD_NAME_DELIMITER,
	FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX,
	FDS_NESTED_FIELD_NAME_DELIMITER,
	FDS_NESTED_FIELD_NAME_PARENT_SUFFIX,
} from '../constants';

export const resolveField = function resolveField(
	path: string | Array<string>,
	item: any
) {
	if (
		Array.isArray(path) ||
		!!(
			!path.includes(FDS_ARRAY_FIELD_NAME_DELIMITER) &&
			!path.includes(FDS_NESTED_FIELD_NAME_DELIMITER)
		)
	) {
		const rootPropertyName = typeof path === 'string' ? path : path[0];

		return {
			resolvedFieldname: path,
			resolvedItem: item,
			rootPropertyName,
		};
	}

	const itemPath = path
		.replace(/\[\]/g, '.')
		.split(FDS_NESTED_FIELD_NAME_DELIMITER);

	if (
		path.includes(FDS_ARRAY_FIELD_NAME_PARENT_SUFFIX) ||
		path.includes(FDS_NESTED_FIELD_NAME_PARENT_SUFFIX)
	) {
		itemPath.pop();
	}

	const resolvedFieldname = itemPath[itemPath.length - 1];
	let resolvedItem = undefined;

	function flatten(obj: any) {
		const key = resolvedFieldname;

		return obj.map((prop: any) => {
			if (!prop) {
				return {[key]: undefined};
			}

			const {[key]: value} = prop;

			return {[key]: value};
		});
	}

	if (itemPath.length > 1) {
		resolvedItem =
			itemPath.slice(0, -1).reduce((navigatedValue, chunk) => {
				if (Array.isArray(navigatedValue)) {
					const next = navigatedValue.map((value: any) => {
						return value[chunk];
					});

					// Flat result to avoid nested arrays

					return [].concat.apply([], next);
				}
				else if (navigatedValue.constructor.name === 'Object') {
					return navigatedValue[chunk];
				}
			}, item) || {};

		if (Array.isArray(resolvedItem)) {
			resolvedItem = flatten(resolvedItem).flat();
		}
	}
	else {
		resolvedItem = item;
	}

	return {
		resolvedFieldname,
		resolvedItem,
		rootPropertyName: itemPath[0],
	};
};
