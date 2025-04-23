/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author David Truong
 */
public class CTRemoteModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTRemote> {

	public CTRemoteModelIndexerWriterContributor(
		CTRemoteLocalService ctRemoteLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ctRemoteLocalService = ctRemoteLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		if (!CTCollectionThreadLocal.isProductionMode()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Skip indexing of ", CTRemote.class.getName(),
						" because this can only be performed in production ",
						"mode."));
			}

			return;
		}

		batchIndexingActionable.setPerformActionMethod(
			(CTRemote ctRemote) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(ctRemote)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ctRemoteLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CTRemote ctRemote) {
		return ctRemote.getCompanyId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTRemoteModelIndexerWriterContributor.class);

	private final CTRemoteLocalService _ctRemoteLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}