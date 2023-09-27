/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class SearchTestUtil {

	public static final String ATTACHMENT_OWNER_CLASS_NAME =
		RandomTestUtil.randomString();

	public static final long ATTACHMENT_OWNER_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	public static final long ATTACHMENT_OWNER_CLASS_PK =
		RandomTestUtil.randomLong();

	public static final long ENTRY_CLASS_PK = RandomTestUtil.randomLong();

	public static final String SUMMARY_CONTENT = RandomTestUtil.randomString();

	public static final String SUMMARY_TITLE = RandomTestUtil.randomString();

	public static Document createAttachmentDocument(String entryClassName) {
		return createAttachmentDocument(entryClassName, ENTRY_CLASS_PK);
	}

	public static Document createAttachmentDocument(
		String entryClassName, long entryClassPK) {

		Document document = createDocument(entryClassName, entryClassPK);

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setAttachmentOwnerKey(
			ATTACHMENT_OWNER_CLASS_NAME_ID, ATTACHMENT_OWNER_CLASS_PK);

		return document;
	}

	public static Document createDocument(String entryClassName) {
		return createDocument(entryClassName, ENTRY_CLASS_PK);
	}

	public static Document createDocument(
		String entryClassName, long entryClassPK) {

		Document document = new DocumentImpl();

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(entryClassName, entryClassPK);

		return document;
	}

	public static List<SearchResult> getSearchResults(
		SearchResultTranslator searchResultTranslator, Document... documents) {

		Hits hits = new HitsImpl();

		hits.setDocs(documents);

		return searchResultTranslator.translate(hits, null, null, null);
	}

}