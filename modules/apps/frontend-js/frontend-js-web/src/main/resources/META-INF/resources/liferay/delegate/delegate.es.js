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
 * Checks if element is disabled or whether it exists in a disabled element tree
 * @param {!Element} element The DOM element to check.
 * @return {boolean}
 */
function isDisabled(node) {
	return !!(node.disabled || node.closest('[disabled]'));
}

/**
 * Listens to the specified event on the given DOM element, but only calls the
 * given callback listener when it's triggered by elements that match the
 * given selector or target element.
 * @param {!Element} element The DOM element the event should be listened on.
 * @param {string} eventName The name of the event to listen to.
 * @param {string} selector Css selector that should match the event for the
 *     listener to be triggered.
 * @param {!function(!Object)} callback Function to be called when the event
 *     is triggered. It will receive the normalized event object.
 * @return {function} Can be used to remove the listener.
 */
function delegate(element, eventName, selector, callback) {
	const eventHandler = (event) => {
		if (eventName === 'click' && isDisabled(event.target)) {
			return;
		}

		if (!event.defaultPrevented && event.target.matches(selector)) {
			callback(event);
		}
	};

	element.addEventListener(eventName, eventHandler);

	return {
		dispose() {
			element.removeEventListener(eventName, eventHandler);
		},
	};
}

export default delegate;
