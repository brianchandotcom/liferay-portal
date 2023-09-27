/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns the portlet Id extracted from the provided portletId query parameter
 * @param {!string} portletId The portletId query parameter to extract the portletId from in the form of `_p_p_id_portletId_`
 * @return {string} Portlet ID
 * @review
 */
const REGEX_PORTLET_ID = /^(?:p_p_id)?_(.*)_.*$/;

export default function getPortletId(portletId) {
	return portletId.replace(REGEX_PORTLET_ID, '$1');
}
