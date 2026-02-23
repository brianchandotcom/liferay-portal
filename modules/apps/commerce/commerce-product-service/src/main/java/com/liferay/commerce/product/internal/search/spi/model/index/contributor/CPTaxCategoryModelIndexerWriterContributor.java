/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Mahmoud Azzam
 */
public class CPTaxCategoryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPTaxCategory> {

	public CPTaxCategoryModelIndexerWriterContributor(
		CPTaxCategoryLocalService cpTaxCategoryLocalService) {

		_cpTaxCategoryLocalService = cpTaxCategoryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpTaxCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(CPTaxCategory cpTaxCategory) {
		return IndexerWriterMode.UPDATE;
	}

	private final CPTaxCategoryLocalService _cpTaxCategoryLocalService;

}