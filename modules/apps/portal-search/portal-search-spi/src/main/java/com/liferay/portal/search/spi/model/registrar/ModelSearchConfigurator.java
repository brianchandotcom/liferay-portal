/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.registrar;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface ModelSearchConfigurator<T extends BaseModel<?>> {

	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor();

	public ModelSearchSettings getModelSearchSettings();

	public ModelSummaryContributor getModelSummaryBuilder();

	public ModelVisibilityContributor getModelVisibilityContributor();

}