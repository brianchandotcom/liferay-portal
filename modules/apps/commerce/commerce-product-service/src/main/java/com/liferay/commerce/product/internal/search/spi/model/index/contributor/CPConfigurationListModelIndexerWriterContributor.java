/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Andrea Sbarra
 */
public class CPConfigurationListModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPConfigurationList> {

	public CPConfigurationListModelIndexerWriterContributor(
		CPConfigurationListLocalService cpConfigurationListLocalService) {

		_cpConfigurationListLocalService = cpConfigurationListLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpConfigurationListLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPConfigurationList cpConfigurationList) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPConfigurationListLocalService
		_cpConfigurationListLocalService;

}