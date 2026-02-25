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
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;

/**
 * @author István András Dézsi
 */
public class KaleoInstanceTokenModelIndexerWriterContributor
	extends ModelIndexerWriterContributor<KaleoInstanceToken> {

	public KaleoInstanceTokenModelIndexerWriterContributor(
		KaleoInstanceLocalService kaleoInstanceLocalService,
		KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService) {

		super(
			kaleoInstanceTokenLocalService::getIndexableActionableDynamicQuery);

		_kaleoInstanceLocalService = kaleoInstanceLocalService;
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

	private final KaleoInstanceLocalService _kaleoInstanceLocalService;

}