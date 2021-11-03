/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken",
	service = ModelIndexerWriterContributor.class
)
public class KaleoInstanceTokenModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<KaleoInstanceToken> {

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
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				kaleoInstanceTokenLocalService.
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
				kaleoInstanceLocalService.getKaleoInstance(
					kaleoInstanceToken.getKaleoInstanceId()));
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

	@Reference
	protected KaleoInstanceLocalService kaleoInstanceLocalService;

	@Reference
	protected KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService;

}