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

(function (w) {
	'use strict';

	var KEYDOWN_EVENT = 'keydown';
	var TAB_KEYCODE = 9;
	var ACCESSIBILITY_CLASS = 'is-accessible';
	var TIMEOUT = 5000;

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
