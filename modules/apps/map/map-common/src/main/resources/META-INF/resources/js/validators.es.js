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
	return subset => {
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
