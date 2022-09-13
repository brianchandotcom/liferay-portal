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
