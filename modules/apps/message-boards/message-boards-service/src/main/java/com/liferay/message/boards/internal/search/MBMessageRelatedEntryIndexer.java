/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.SearchContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "related.entry.indexer.class.name=com.liferay.message.boards.model.MBMessage",
	service = RelatedEntryIndexer.class
)
public class MBMessageRelatedEntryIndexer extends BaseRelatedEntryIndexer {

	@Override
	public void addRelatedEntryFields(Document document, Object object)
		throws Exception {

		FileEntry fileEntry = (FileEntry)object;

		MBMessage mbMessage = mbMessageLocalService.fetchFileEntryMessage(
			fileEntry.getFileEntryId());

		if (mbMessage == null) {
			return;
		}

		document.addKeyword(Field.CATEGORY_ID, mbMessage.getCategoryId());

		document.addKeyword("discussion", false);
		document.addKeyword("threadId", mbMessage.getThreadId());
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		try {
			MBMessage mbMessage = mbMessageLocalService.getMessage(classPK);

			if (mbMessage.isDiscussion()) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					mbMessage.getClassName());

				return indexer.isVisible(mbMessage.getClassPK(), status);
			}
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to get message boards message", exception);
			}

			return false;
		}

		return true;
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
		if (searchContext.isIncludeDiscussions()) {
			searchContext.addFullQueryEntryClassName(MBMessage.class.getName());

			searchContext.setAttribute("discussion", Boolean.TRUE);
		}
	}

	@Reference
	protected MBMessageLocalService mbMessageLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageRelatedEntryIndexer.class);

}