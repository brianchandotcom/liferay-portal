/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration.provider;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.virtual.host.configuration.provider.SiteVirtualHostConfigurationProvider;
import com.liferay.site.internal.configuration.SiteVirtualHostConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(service = SiteVirtualHostConfigurationProvider.class)
public class SiteVirtualHostConfigurationProviderImpl
	implements SiteVirtualHostConfigurationProvider {

	@Override
	public boolean isDefaultInstanceURLBypassAllowed(long companyId)
		throws ConfigurationException {

		SiteVirtualHostConfiguration siteVirtualHostConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SiteVirtualHostConfiguration.class, companyId);

		return siteVirtualHostConfiguration.allowDefaultInstanceURLBypass();
	}

	@Override
	public boolean isStrictModeEnabled(long companyId)
		throws ConfigurationException {

		SiteVirtualHostConfiguration siteVirtualHostConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SiteVirtualHostConfiguration.class, companyId);

		return siteVirtualHostConfiguration.strictModeEnabled();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}