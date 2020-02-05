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
 * Returns a debounced function that will delay it execution until delay
 * has passed. If window is closed or navigation is performed before
 * this delay, an alert will be shown to prevent it from not being executed.
 * @param {function} callback
 * @param {number} [delay=0]
 * @param {string} [message='Do you want to leave this site...']
 * @return {function}
 * @review
 */
function debouncedAlert(
	callback,
	delay = 0,
	message = Liferay.Language.get('do-you-want-to-leave-this-site')
) {
	let beforeNavigateHandler = null;
	let timeoutId = null;

	/**
	 * @review
	 */
	const clearPendingCallback = () => {
		window.removeEventListener('beforeunload', handleBeforeUnload);

		if (beforeNavigateHandler) {
			beforeNavigateHandler.detach();
			beforeNavigateHandler = null;
		}

		if (timeoutId) {
			clearTimeout(timeoutId);
			timeoutId = null;
		}
	};

	/**
	 * @param {{originalEvent: Event}} event
	 * @review
	 */
	const handleBeforeNavigate = event => {
		if (confirm(message)) {
			clearPendingCallback();
		}
		else {
			event.originalEvent.preventDefault();
		}
	};

	/**
	 * @param {BeforeUnloadEvent} event
	 * @review
	 */
	const handleBeforeUnload = event => {
		event.returnValue = message;

		return message;
	};

	return (...args) => {
		clearPendingCallback();

		beforeNavigateHandler = Liferay.on(
			'beforeNavigate',
			handleBeforeNavigate
		);

		window.addEventListener('beforeunload', handleBeforeUnload);

		timeoutId = setTimeout(() => {
			clearPendingCallback();
			callback(...args);
		}, delay);
	};
}

export {debouncedAlert};
export default debouncedAlert;
