/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.configuration.provider;

import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.kernel.configuration.provider.AssetCategoryConfigurationProvider;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AssetCategoryConfigurationProvider.class)
public class AssetCategoryConfigurationProviderImpl
	implements AssetCategoryConfigurationProvider {

	@Override
	public boolean isSearchHierarchical(long companyId)
		throws ConfigurationException {

		AssetCategoriesCompanyConfiguration
			assetCategoriesCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AssetCategoriesCompanyConfiguration.class, companyId);

		return assetCategoriesCompanyConfiguration.
			includeChildrenCategoriesWhenSearchingParentCategories();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}