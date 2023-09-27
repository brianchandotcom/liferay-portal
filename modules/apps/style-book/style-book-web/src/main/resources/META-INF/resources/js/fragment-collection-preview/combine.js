/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
