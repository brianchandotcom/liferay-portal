/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function sumTotalOfValuesOfArray(array) {
	const totalValue = array
		?.map((indexValue) => {
			return Object.values(indexValue)[0];
		})
		?.reduce((totalSum, value) => totalSum + value, 0);

	return totalValue;
}

export function getArrayOfValuesFromArrayOfObjects(arrayOfObjects) {
	const valuesArray = arrayOfObjects?.map((values) => {
		return Object.values(values)[0];
	});

	return valuesArray;
}
