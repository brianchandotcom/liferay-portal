/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Mikel Lorza
 */
public interface FriendlyURLSeparatorConfigurationManager {

	public JSONObject getFriendlyURLSeparatorsJSONObject(long companyId)
		throws PortalException;

	public void updateFriendlyURLSeparatorCompanyConfiguration(
			long companyId, String friendlyURLSeparatorsJSON)
		throws PortalException;

}