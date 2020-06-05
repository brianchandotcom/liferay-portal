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

export function closest(element, selector) {
	var matches = window.document.querySelectorAll(selector);
	var i;
	do {
		i = matches.length;
		// eslint-disable-next-line no-empty
		while (--i >= 0 && matches.item(i) !== element) {}
	} while (i < 0 && (element = element.parentElement));

	return element;
}
