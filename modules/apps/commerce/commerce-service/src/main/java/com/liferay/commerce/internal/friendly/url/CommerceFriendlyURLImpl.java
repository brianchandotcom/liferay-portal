/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.friendly.url;

import com.liferay.commerce.configuration.CommerceFriendlyURLConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.friendly.url.CommerceFriendlyURL;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceFriendlyURL.class)
public class CommerceFriendlyURLImpl implements CommerceFriendlyURL {

	@Override
	public String getOrderURLSeparator(long companyId) {
		CommerceFriendlyURLConfiguration commerceFriendlyURLConfiguration =
			_getCommerceFriendlyURLConfiguration(companyId);

		return StringPool.SLASH +
			commerceFriendlyURLConfiguration.orderURLSeparator() +
				StringPool.SLASH;
	}

	private CommerceFriendlyURLConfiguration
		_getCommerceFriendlyURLConfiguration(long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				CommerceFriendlyURLConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId,
					CommerceConstants.SERVICE_NAME_COMMERCE_FRIENDLY_URL,
					CommerceFriendlyURLConfiguration.class.getName()));
		}
		catch (ConfigurationException configurationException) {
			throw new SystemException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}