/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.virtual.host.configuration.provider;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Dante Wang
 */
public interface SiteVirtualHostConfigurationProvider {

	public boolean isDefaultInstanceURLBypassAllowed(long companyId)
		throws ConfigurationException;

	public boolean isStrictModeEnabled(long companyId)
		throws ConfigurationException;

}