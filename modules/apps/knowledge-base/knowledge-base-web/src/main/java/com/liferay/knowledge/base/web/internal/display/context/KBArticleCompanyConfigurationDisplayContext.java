/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Alicia García
 */
public class KBArticleCompanyConfigurationDisplayContext {

	public KBArticleCompanyConfigurationDisplayContext(
		KBServiceConfigurationProvider kbServiceConfigurationProvider) {

		_kbServiceConfigurationProvider = kbServiceConfigurationProvider;
	}

	public int getCheckInterval() throws ConfigurationException {
		return _kbServiceConfigurationProvider.getCheckInterval();
	}

	public int getExpirationDateNotificationDateWeeks()
		throws ConfigurationException {

		return _kbServiceConfigurationProvider.
			getExpirationDateNotificationDateWeeks();
	}

	private final KBServiceConfigurationProvider
		_kbServiceConfigurationProvider;

}