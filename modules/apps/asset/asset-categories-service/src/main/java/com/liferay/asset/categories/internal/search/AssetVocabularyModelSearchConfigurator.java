/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(service = ModelSearchConfigurator.class)
public class AssetVocabularyModelSearchConfigurator
	implements ModelSearchConfigurator<AssetVocabulary> {

	@Override
	public String getClassName() {
		return AssetVocabulary.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_VOCABULARY_ID, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<AssetVocabulary>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.asset.kernel.model.AssetVocabulary)"
	)
	private ModelIndexerWriterContributor<AssetVocabulary>
		_modelIndexWriterContributor;

}