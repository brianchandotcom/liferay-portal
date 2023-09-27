/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function convertMapToArr(mapInstance) {
	const array = [];

	mapInstance.forEach((value, key) => array.push([key, value]));

	return array;
}

function getMapKeys(mapInstance) {
	const array = [];

	mapInstance.forEach((value, key) => array.push(key));

	return array;
}

export {convertMapToArr, getMapKeys};
