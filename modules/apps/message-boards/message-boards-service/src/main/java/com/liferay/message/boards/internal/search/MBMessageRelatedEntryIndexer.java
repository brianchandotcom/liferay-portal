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
	immediate = true,
	property = "related.entry.indexer.class.name=com.liferay.message.boards.model.MBMessage",
	service = RelatedEntryIndexer.class
)
public class MBMessageRelatedEntryIndexer extends BaseRelatedEntryIndexer {

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		FileEntry fileEntry = (FileEntry)obj;

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