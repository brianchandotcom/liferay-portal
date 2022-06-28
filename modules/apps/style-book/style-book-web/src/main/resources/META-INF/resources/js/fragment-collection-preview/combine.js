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

export function combine(...arrays) {
	const combinations = [];

	function addCombinations(combination, restArrays) {
		const nonemptyArrays = restArrays.filter((array) => array.length);

		if (!nonemptyArrays.length && !!combination.length) {
			combinations.push(combination);
		}
		else if (nonemptyArrays.length) {
			const [nextArray, ...nextRest] = nonemptyArrays;

			nextArray.forEach((element) => {
				addCombinations([...combination, element], nextRest);
			});
		}
	}

	addCombinations([], arrays);

	return combinations;
}
