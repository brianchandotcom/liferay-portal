/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Andrea Sbarra
 */
public class CPConfigurationEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPConfigurationEntry> {

	public CPConfigurationEntryModelIndexerWriterContributor(
		CPConfigurationEntryLocalService cpConfigurationEntryLocalService) {

		_cpConfigurationEntryLocalService = cpConfigurationEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpConfigurationEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPConfigurationEntry cpConfigurationEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPConfigurationEntryLocalService
		_cpConfigurationEntryLocalService;

}