/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.url;

import com.liferay.commerce.product.configuration.CPFriendlyURLConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(enabled = false, immediate = true, service = CPFriendlyURL.class)
public class CPFriendlyURLImpl implements CPFriendlyURL {

	@Override
	public String getAssetCategoryURLSeparator(long companyId) {
		CPFriendlyURLConfiguration cpURLConfiguration =
			_getCPFriendlyURLConfiguration(companyId);

		return StringPool.SLASH +
			cpURLConfiguration.assetCategoryURLSeparator() + StringPool.SLASH;
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