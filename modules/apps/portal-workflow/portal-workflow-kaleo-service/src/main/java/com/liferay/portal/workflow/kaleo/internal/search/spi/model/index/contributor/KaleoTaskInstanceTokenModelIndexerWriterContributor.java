/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

/**
 * @author Rafael Praxedes
 */
public class KaleoTaskInstanceTokenModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<KaleoTaskInstanceToken> {

	public KaleoTaskInstanceTokenModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		KaleoTaskInstanceTokenLocalService kaleoTaskInstanceTokenLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_kaleoTaskInstanceTokenLocalService =
			kaleoTaskInstanceTokenLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(KaleoTaskInstanceToken kaleoTaskInstanceToken) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						kaleoTaskInstanceToken)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_kaleoTaskInstanceTokenLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		return kaleoTaskInstanceToken.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

}