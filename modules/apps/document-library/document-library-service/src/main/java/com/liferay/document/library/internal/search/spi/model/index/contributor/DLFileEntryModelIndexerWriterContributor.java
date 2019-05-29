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

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelIndexerWriterContributor.class
)
public class DLFileEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DLFileEntry> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setInterval(_dlFileIndexingInterval);
		batchIndexingActionable.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(dlFileEntry)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				dlFileEntryLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DLFileEntry dlFileEntry) {
		return dlFileEntry.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(DLFileEntry dlFileEntry) {
		DLFileVersion dlFileVersion = null;

		try {
			dlFileVersion = dlFileEntry.getFileVersion();
		}
		catch (NoSuchFileVersionException nsfve) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get file version for file entry " +
						dlFileEntry.getFileEntryId(),
					nsfve);
			}

			return IndexerWriterMode.SKIP;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		if (!dlFileVersion.isApproved() && !dlFileEntry.isInTrash()) {
			return IndexerWriterMode.SKIP;
		}

		return IndexerWriterMode.UPDATE;
	}

	@Activate
	protected void activate() {
		_dlFileIndexingInterval = GetterUtil.getInteger(
			props.get(PropsKeys.DL_FILE_INDEXING_INTERVAL), 500);
	}

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

	@Reference
	protected Props props;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryModelIndexerWriterContributor.class);

	private int _dlFileIndexingInterval;

}