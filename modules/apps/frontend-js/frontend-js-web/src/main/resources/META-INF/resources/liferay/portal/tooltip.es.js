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
 * Prepares given element to show tooltip
 * @param {HTMLElement} element The portlet's namespace
 * @param {String} text Text to show in tooltip
 * @deprecated As of Athanasius (7.3.x), replaced by ClayTooltip
 */
export function showTooltip(element, text) {
	element.setAttribute('title', text);

	element.classList.add('lfr-portal-tooltip');
}
