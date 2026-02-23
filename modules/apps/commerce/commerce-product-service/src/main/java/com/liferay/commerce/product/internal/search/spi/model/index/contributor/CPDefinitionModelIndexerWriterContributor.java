/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Brian I. Kim
 */
public class CPDefinitionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPDefinition> {

	public CPDefinitionModelIndexerWriterContributor(
		CPDefinitionLocalService cpDefinitionLocalService) {

		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(CPDefinition cpDefinition) {
		return IndexerWriterMode.UPDATE;
	}

	private final CPDefinitionLocalService _cpDefinitionLocalService;

}