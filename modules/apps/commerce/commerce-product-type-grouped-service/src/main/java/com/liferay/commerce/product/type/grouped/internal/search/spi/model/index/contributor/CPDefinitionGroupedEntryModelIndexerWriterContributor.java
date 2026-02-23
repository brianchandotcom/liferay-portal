/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Brian I. Kim
 */
public class CPDefinitionGroupedEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPDefinitionGroupedEntry> {

	public CPDefinitionGroupedEntryModelIndexerWriterContributor(
		CPDefinitionGroupedEntryLocalService
			cpDefinitionGroupedEntryLocalService) {

		_cpDefinitionGroupedEntryLocalService =
			cpDefinitionGroupedEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionGroupedEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

}