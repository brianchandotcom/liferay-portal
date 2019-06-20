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
 * Returns true if any change is different
 * @param {object} changes
 * @return {boolean}
 * @review
 */
const shouldUpdatePureComponent = changes => {
	return Object.values(changes)
		.filter(change => change.key !== 'events')
		.some(change => change.newVal !== change.prevVal);
};

/**
 * Returns true if any of the given properties has changed
 * @param {object} changes
 * @param {string[]} properties
 * @return {boolean}
 */
const shouldUpdateOnChangeProperties = (changes, properties) => {
	return Object.values(changes)
		.filter(change => properties.indexOf(change.key) !== -1)
		.some(change => change.newVal !== change.prevVal);
};

export {shouldUpdatePureComponent, shouldUpdateOnChangeProperties};
