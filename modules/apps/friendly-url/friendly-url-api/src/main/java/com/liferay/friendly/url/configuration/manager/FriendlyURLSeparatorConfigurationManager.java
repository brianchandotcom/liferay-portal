/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration.manager;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Mikel Lorza
 */
public interface FriendlyURLSeparatorConfigurationManager {

	public String getFriendlyURLSeparatorsJSONObjectString(long companyId)
		throws ConfigurationException;

	public void updateFriendlyURLSeparatorCompanyConfiguration(
			long companyId, String friendlyURLSeparatorsJSONObjectString)
		throws ConfigurationException;

}