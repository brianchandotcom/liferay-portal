/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

/**
 * @author Feliphe Marinho
 */
public class KaleoDefinitionModelIndexerWriterContributor
	extends ModelIndexerWriterContributor<KaleoDefinition> {

	public KaleoDefinitionModelIndexerWriterContributor(
		KaleoDefinitionLocalService kaleoDefinitionLocalService) {

		super(kaleoDefinitionLocalService::getIndexableActionableDynamicQuery);
	}

	@Override
	public void modelIndexed(KaleoDefinition kaleoDefinition) {
		Indexer<KaleoDefinitionVersion> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				KaleoDefinitionVersion.class);

		try {
			for (KaleoDefinitionVersion kaleoDefinitionVersion :
					kaleoDefinition.getKaleoDefinitionVersions()) {

				indexer.reindex(kaleoDefinitionVersion);
			}
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

}