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

package com.liferay.document.library.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.comment.Comment;
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
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "related.entry.indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = RelatedEntryIndexer.class
)
public class DLFileEntryRelatedEntryIndexer implements RelatedEntryIndexer {

	@Override
	public void addRelatedClassNames(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		_relatedEntryIndexer.addRelatedClassNames(
			contextBooleanFilter, searchContext);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		Comment comment = (Comment)obj;

		FileEntry fileEntry = null;

		try {
			fileEntry = dlAppLocalService.getFileEntry(comment.getClassPK());
		}
		catch (Exception e) {
			return;
		}

		if (fileEntry instanceof LiferayFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
			document.addKeyword(
				Field.TREE_PATH,
				StringUtil.split(dlFileEntry.getTreePath(), CharPool.SLASH));
		}
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(classPK);

			if (fileEntry instanceof LiferayFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				if (dlFileEntry.isInHiddenFolder()) {
					Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
						dlFileEntry.getClassName());

					return indexer.isVisible(dlFileEntry.getClassPK(), status);
				}
			}
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to get file entry", e);
			}

			return false;
		}

		return true;
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
		if (searchContext.isIncludeAttachments()) {
			searchContext.addFullQueryEntryClassName(
				DLFileEntry.class.getName());
		}
	}

	@Reference
	protected DLAppLocalService dlAppLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryRelatedEntryIndexer.class);

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

}