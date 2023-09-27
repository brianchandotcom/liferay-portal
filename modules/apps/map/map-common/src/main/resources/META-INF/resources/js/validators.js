/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Checks if the given node is an instance of HTMLInputElement.
 * @param {*} node Node to be tested
 * @return {boolean}
 * @review
 */
function isInputNode(node) {
	return node instanceof HTMLInputElement;
}

/**
 * Checks if the given set is a subset of the specified superset.
 * @param {Array[]} superset Group of valid elements.
 * @return {boolean}
 * @review
 */
function isSubsetOf(superset) {
	return (subset) => {
		const subsetLength = subset.length;

		for (let i = 0; i < subsetLength; i++) {
			if (superset.indexOf(subset[i]) === -1) {
				return false;
			}
		}

		return true;
	};
}

export {isInputNode, isSubsetOf};
