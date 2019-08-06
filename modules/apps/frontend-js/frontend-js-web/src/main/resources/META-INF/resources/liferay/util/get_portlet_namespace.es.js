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
 * Returns the portlet namespace with underscores prepended and appended to it
 * @param {!string} portletId The portlet ID to add underscores to
 * @return {string} Portlet namespace with underscores prepended and appended
 * @review
 */
export default function getPortletNamespace(portletId) {
	if (typeof portletId !== 'string') {
		throw new TypeError('portletId must be a string');
	}

	return `_${portletId}_`;
}
