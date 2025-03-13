/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Debounces function execution.
 */
function debounce(fn: Function, delay: number): Function {
	return function debounced() {
		const args = arguments;

		cancelDebounce(debounced);

		(debounced as any).id = setTimeout(() => {
			fn.apply(null, args);
		}, delay);
	};
}

/**
 * Cancels the scheduled debounced function.
 */
function cancelDebounce(debounced: Function) {
	clearTimeout((debounced as any).id);
}

export default debounce;
export {cancelDebounce, debounce};
