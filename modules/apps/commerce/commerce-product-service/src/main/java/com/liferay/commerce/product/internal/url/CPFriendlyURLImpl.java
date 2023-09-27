/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.url;

import com.liferay.commerce.product.configuration.CPFriendlyURLConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = CPFriendlyURL.class)
public class CPFriendlyURLImpl implements CPFriendlyURL {

	@Override
	public String getAssetCategoryURLSeparator(long companyId) {
		CPFriendlyURLConfiguration cpFriendlyURLConfiguration =
			_getCPFriendlyURLConfiguration(companyId);

		return StringPool.SLASH +
			cpFriendlyURLConfiguration.assetCategoryURLSeparator() +
				StringPool.SLASH;
	}

	@Override
	public String getProductURLSeparator(long companyId) {
		CPFriendlyURLConfiguration cpFriendlyURLConfiguration =
			_getCPFriendlyURLConfiguration(companyId);

		return StringPool.SLASH +
			cpFriendlyURLConfiguration.productURLSeparator() + StringPool.SLASH;
	}

	private CPFriendlyURLConfiguration _getCPFriendlyURLConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				CPFriendlyURLConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, CPConstants.SERVICE_NAME_CP_FRIENDLY_URL,
					CPFriendlyURLConfiguration.class.getName()));
		}
		catch (ConfigurationException configurationException) {
			throw new SystemException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}