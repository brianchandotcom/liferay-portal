/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
public class AssetCategoryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<AssetCategory> {

	public AssetCategoryModelIndexerWriterContributor(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;

}