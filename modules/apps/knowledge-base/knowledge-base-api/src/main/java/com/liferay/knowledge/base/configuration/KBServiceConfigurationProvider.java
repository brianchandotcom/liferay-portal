/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.configuration;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.io.IOException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alicia García
 */
@ProviderType
public interface KBServiceConfigurationProvider {

	public int getCheckInterval() throws ConfigurationException;

	public int getExpirationDateNotificationDateWeeks()
		throws ConfigurationException;

	public void updateExpirationDateConfiguration(
			int checkInterval, int expirationDateNotificationDateWeeks)
		throws IOException;

}