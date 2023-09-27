/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Debounces function execution.
 * @param {!function()} fn
 * @param {number} delay
 * @return {!function()}
 */
function debounce(fn, delay) {
	return function debounced() {
		const args = arguments;
		cancelDebounce(debounced);
		debounced.id = setTimeout(() => {
			fn(...args);
		}, delay);
	};
}

/**
 * Cancels the scheduled debounced function.
 * @param {function()} debounced
 */
function cancelDebounce(debounced) {
	clearTimeout(debounced.id);
}

export default debounce;
export {cancelDebounce, debounce};
