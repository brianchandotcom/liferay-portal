/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Utility to traverse an object and get a property value based on a string path
 * Splits the path if there is any '.' into an array of keys you can use to drill down
 * the object hierarchy
 *
 * @param item : object
 * @param path : string ('id', 'embedded.id'). Defaults to 'id'
 * @returns value of the selected property
 */

const getSelectedItemValue = function ({
	item,
	path = 'id',
}: {
	item: any;
	path?: string | null;
}): any {
	if (!path) {
		path = 'id';
	}

	return path.split('.').reduce((acc: any, currentPath: string) => {
		return acc?.[currentPath] ?? null;
	}, item);
};

export default getSelectedItemValue;
