/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function (w) {
	'use strict';

	const KEYDOWN_EVENT = 'keydown';
	const TAB_KEYCODE = 9;
	const ACCESSIBILITY_CLASS = 'is-accessible';
	const TIMEOUT = 5000;

	const removeAfter = setTimeout(() => {
		w.removeEventListener(KEYDOWN_EVENT, needsAccessibility);
		clearTimeout(removeAfter);
	}, TIMEOUT);

	function needsAccessibility(event) {
		const isTabbing = event.which === TAB_KEYCODE;

		if (isTabbing) {
			w.document.body.classList.add(ACCESSIBILITY_CLASS);
			w.removeEventListener(KEYDOWN_EVENT, needsAccessibility);
		}
	}

	w.addEventListener(KEYDOWN_EVENT, needsAccessibility);
})(window);
