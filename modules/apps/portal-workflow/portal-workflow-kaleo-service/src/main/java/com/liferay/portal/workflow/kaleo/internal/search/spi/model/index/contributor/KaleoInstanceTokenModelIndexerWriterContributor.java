/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;

/**
 * @author István András Dézsi
 */
public class KaleoInstanceTokenModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<KaleoInstanceToken> {

	public KaleoInstanceTokenModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		KaleoInstanceLocalService kaleoInstanceLocalService,
		KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_kaleoInstanceLocalService = kaleoInstanceLocalService;
		_kaleoInstanceTokenLocalService = kaleoInstanceTokenLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(KaleoInstanceToken kaleoInstanceToken) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						kaleoInstanceToken)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_kaleoInstanceTokenLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(KaleoInstanceToken kaleoInstanceToken) {
		return kaleoInstanceToken.getCompanyId();
	}

	@Override
	public void modelIndexed(KaleoInstanceToken kaleoInstanceToken) {
		Indexer<KaleoInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			KaleoInstance.class);

		try {
			indexer.reindex(
				_kaleoInstanceLocalService.getKaleoInstance(
					kaleoInstanceToken.getKaleoInstanceId()));
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final KaleoInstanceLocalService _kaleoInstanceLocalService;
	private final KaleoInstanceTokenLocalService
		_kaleoInstanceTokenLocalService;

}