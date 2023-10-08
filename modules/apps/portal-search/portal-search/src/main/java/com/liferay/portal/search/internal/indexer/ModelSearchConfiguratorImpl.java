/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Michael C. Han
 */
public class ModelSearchConfiguratorImpl<T extends BaseModel<?>>
	implements ModelSearchConfigurator<T> {

	public ModelSearchConfiguratorImpl(
		ModelIndexerWriterContributor<T> modelIndexerWriterContributor,
		ModelVisibilityContributor modelVisibilityContributor,
		ModelSearchSettings modelSearchSettings,
		ModelSummaryContributor modelSummaryContributor) {

		_modelIndexerWriterContributor = modelIndexerWriterContributor;
		_modelVisibilityContributor = modelVisibilityContributor;
		_modelSearchSettings = modelSearchSettings;
		_modelSummaryContributor = modelSummaryContributor;
	}

	@Override
	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor() {
		return _modelIndexerWriterContributor;
	}

	@Override
	public ModelSearchSettings getModelSearchSettings() {
		return _modelSearchSettings;
	}

	@Override
	public ModelSummaryContributor getModelSummaryBuilder() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	private final ModelIndexerWriterContributor<T>
		_modelIndexerWriterContributor;
	private final ModelSearchSettings _modelSearchSettings;
	private final ModelSummaryContributor _modelSummaryContributor;
	private final ModelVisibilityContributor _modelVisibilityContributor;

}