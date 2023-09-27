/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createPortletURL from './create_portlet_url.es';

/**
 * Returns an action portlet URL in form of a URL object by setting the lifecycle parameter
 * @param {!string} basePortletURL The base portlet URL to be modified in this utility
 * @param {object} parameters Search parameters to be added or changed in the base URL
 * @return {URL} Action Portlet URL
 * @review
 */
export default function createActionURL(basePortletURL, parameters = {}) {
	return createPortletURL(basePortletURL, {
		...parameters,
		p_p_lifecycle: '1',
	});
}
