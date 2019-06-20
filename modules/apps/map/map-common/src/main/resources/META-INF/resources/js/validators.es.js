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

/* eslint no-for-of-loops/no-for-of-loops: "warn" */
/* eslint no-unused-vars: "warn" */

import core from 'metal';

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
		for (const element of subset) {
			if (superset.indexOf(element) === -1) {
				return false;
			}
		}

		return true;
	};
}

export {isInputNode, isSubsetOf};
