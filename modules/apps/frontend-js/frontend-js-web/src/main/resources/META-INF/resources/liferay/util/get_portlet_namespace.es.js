/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
