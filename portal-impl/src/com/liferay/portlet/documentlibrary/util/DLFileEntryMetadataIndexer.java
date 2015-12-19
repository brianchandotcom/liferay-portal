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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Marcellus Tavares
 */
public class DLFileEntryMetadataIndexer
	extends BaseIndexer<DLFileEntryMetadata> implements DDMStructureIndexer {

	public static final String CLASS_NAME = DLFileEntryMetadata.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void reindexDDMStructures(List<Long> ddmStructureIds)
		throws SearchException {

		if (IndexWriterHelperUtil.isIndexReadOnly() || !isIndexerEnabled()) {
			return;
		}

		try {
			Indexer<DLFileEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getDDMStructureFileEntries(
					ArrayUtil.toLongArray(ddmStructureIds));

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				indexer.reindex(dlFileEntry);
			}
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	protected void doDelete(DLFileEntryMetadata object) throws Exception {
	}

	@Override
	protected Document doGetDocument(DLFileEntryMetadata object)
		throws Exception {

		return null;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(DLFileEntryMetadata object) throws Exception {
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
	}

}