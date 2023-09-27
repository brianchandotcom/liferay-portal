/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.test.util.TestIndexer;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class BaseIndexerGetFullQueryTest {

	@Before
	public void setUp() {
		_indexer = new TestIndexer(_CLASS_NAME);
	}

	@Test
	public void testGetFullQueryWithAttachments() throws SearchException {
		_searchContext.setIncludeAttachments(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME, DLFileEntry.class.getName());

		Assert.assertNull(_searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(Object[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

	@Test
	public void testGetFullQueryWithoutAttachmentsOrDiscussions()
		throws SearchException {

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME);

		Assert.assertNull(_searchContext.getAttribute("discussion"));
		Assert.assertNull(
			_searchContext.getAttribute("relatedEntryClassNames"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertEntryClassNames(String... expectedEntryClassNames) {
		Arrays.sort(expectedEntryClassNames);

		String[] actualEntryClassNames = _searchContext.getEntryClassNames();

		Arrays.sort(actualEntryClassNames);

		Assert.assertArrayEquals(
			expectedEntryClassNames, actualEntryClassNames);
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private Indexer<Object> _indexer;
	private final SearchContext _searchContext = new SearchContext();

}