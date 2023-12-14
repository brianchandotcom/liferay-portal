/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

/**
 * @author Petteri Karttunen
 */
public class SXPElementModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<SXPElement> {

	public SXPElementModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		SXPElementLocalService sxpElementLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_sxpElementLocalService = sxpElementLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(SXPElement sxpElement) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(sxpElement)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_sxpElementLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(SXPElement sxpElement) {
		return sxpElement.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final SXPElementLocalService _sxpElementLocalService;

}