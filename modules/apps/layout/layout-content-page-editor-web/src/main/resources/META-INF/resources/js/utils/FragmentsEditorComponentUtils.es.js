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

import {debounce} from 'frontend-js-web';

/**
 * Similar to React's useEffect hook, it allows a component to execute
 * a callback when some group of properties has changed.
 *
 * @param {object} component
 * @param {string[]} properties
 * @param {function} callback
 */
const onPropertiesChanged = (component, properties, callback) => {
	const debouncedCallback = debounce(() => {
		if (!component.disposed_) {
			callback();
		}
	}, 100);

	properties.forEach(property => {
		component.on(`${property}Changed`, debouncedCallback);
	});
};

/**
 * Returns true if any change is different
 * @param {object} changes
 * @param {string[]} [ignoredProperties=['events']]
 * @return {boolean}
 * @review
 */
const shouldUpdatePureComponent = (changes, ignoredProperties = ['events']) => {
	return Object.values(changes)
		.filter(change => !ignoredProperties.includes(change.key))
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

export {
	onPropertiesChanged,
	shouldUpdatePureComponent,
	shouldUpdateOnChangeProperties
};
