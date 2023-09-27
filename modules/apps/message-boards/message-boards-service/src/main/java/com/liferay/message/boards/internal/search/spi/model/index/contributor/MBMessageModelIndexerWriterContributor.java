/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBMessage",
	service = ModelIndexerWriterContributor.class
)
public class MBMessageModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<MBMessage> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_IN_TRASH,
							WorkflowConstants.STATUS_PENDING
						}));
			});
		batchIndexingActionable.setPerformActionMethod(
			(MBMessage mbMessage) -> {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Reindexing message boards messages for message ",
							"board category ID ", mbMessage.getCategoryId(),
							" and group ID ", mbMessage.getGroupId()));
				}

				if (mbMessage.isDiscussion() && mbMessage.isRoot()) {
					return;
				}

				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(mbMessage));
			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_mbMessageLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(MBMessage mbMessage) {
		return mbMessage.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(MBMessage mbMessage) {
		int status = mbMessage.getStatus();

		if (mbMessage.isDiscussion() && mbMessage.isRoot()) {
			return IndexerWriterMode.SKIP;
		}
		else if ((status == WorkflowConstants.STATUS_APPROVED) ||
				 (status == WorkflowConstants.STATUS_IN_TRASH) ||
				 (status == WorkflowConstants.STATUS_PENDING)) {

			return IndexerWriterMode.UPDATE;
		}

		return IndexerWriterMode.DELETE;
	}

	@Override
	public void modelIndexed(MBMessage mbMessage) {
		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		try {
			for (FileEntry attachmentsFileEntry :
					mbMessage.getAttachmentsFileEntries()) {

				indexer.reindex((DLFileEntry)attachmentsFileEntry.getModel());
			}
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}

		if (mbMessage.getMessageId() == mbMessage.getRootMessageId()) {
			return;
		}

		Indexer<MBMessage> mbThreadIndexer =
			IndexerRegistryUtil.nullSafeGetIndexer(MBMessage.class);

		try {
			MBThread mbThread = _mbThreadLocalService.fetchThread(
				mbMessage.getThreadId());

			mbThreadIndexer.reindex(
				_mbMessageLocalService.fetchMBMessage(
					mbThread.getRootMessageId()));
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageModelIndexerWriterContributor.class);

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}