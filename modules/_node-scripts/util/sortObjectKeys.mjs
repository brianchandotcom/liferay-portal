/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function sortObjectKeys(object) {
	const objectCopy = {...object};

	const sortedKeys = Object.keys(object).sort();

	sortedKeys.forEach((key) => {
		delete object[key];
	});

	sortedKeys.forEach((key) => {
		object[key] = objectCopy[key];
	});

	sortedKeys.forEach((key) => {
		if (typeof object[key] === 'object') {
			sortObjectKeys(object[key]);
		}
	});
}
