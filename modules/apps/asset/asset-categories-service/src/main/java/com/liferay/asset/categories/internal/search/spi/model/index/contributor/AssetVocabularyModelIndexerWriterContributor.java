/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
public class AssetVocabularyModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<AssetVocabulary> {

	public AssetVocabularyModelIndexerWriterContributor(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetVocabularyLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final AssetVocabularyLocalService _assetVocabularyLocalService;

}