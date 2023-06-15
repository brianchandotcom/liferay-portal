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

package com.liferay.asset.kernel.configuration.provider;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetCategoryConfigurationProviderUtil {

	public static boolean isSearchHierarchical(long companyId) {
		try {
			return _assetCategoryConfigurationProvider.isSearchHierarchical(
				companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get asset category configuration for company " +
					companyId,
				configurationException);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryConfigurationProviderUtil.class);

	private static volatile AssetCategoryConfigurationProvider
		_assetCategoryConfigurationProvider =
			ServiceProxyFactory.newServiceTrackedInstance(
				AssetCategoryConfigurationProvider.class,
				AssetCategoryConfigurationProviderUtil.class,
				"_assetCategoryConfigurationProvider", false);

}