/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function debounce(func, wait, immediate) {
	let timeout;

	return () => {
		const context = this;
		const args = arguments;
		function later() {
			timeout = null;
			if (!immediate) {
				func.apply(context, args);
			}
		}
		const callNow = immediate && !timeout;

		clearTimeout(timeout);
		timeout = setTimeout(later, wait);
		if (callNow) {
			func.apply(context, args);
		}
	};
}
