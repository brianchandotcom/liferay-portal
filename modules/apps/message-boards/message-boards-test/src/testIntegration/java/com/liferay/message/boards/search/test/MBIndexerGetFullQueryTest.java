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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class MBIndexerGetFullQueryTest {

	@Before
	public void setUp() {
		_indexer = new TestIndexer();
	}

	@Test
	public void testGetFullQueryWithAttachmentsAndDiscussions()
		throws Exception {

		_searchContext.setIncludeAttachments(true);
		_searchContext.setIncludeDiscussions(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(
			_CLASS_NAME, DLFileEntry.class.getName(),
			MBMessage.class.getName());

		Assert.assertEquals(
			Boolean.TRUE, _searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

	@Test
	public void testGetFullQueryWithDiscussions() throws Exception {
		_searchContext.setIncludeDiscussions(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME, MBMessage.class.getName());

		Assert.assertEquals(
			Boolean.TRUE, _searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

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

	private static class TestIndexer extends BaseIndexer<Object> {

		@Override
		public String getClassName() {
			return _CLASS_NAME;
		}

		@Override
		protected void doDelete(Object object) throws Exception {
		}

		@Override
		protected Document doGetDocument(Object object) throws Exception {
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
		protected void doReindex(Object object) throws Exception {
		}

		@Override
		protected void doReindex(String className, long classPK)
			throws Exception {
		}

		@Override
		protected void doReindex(String[] ids) throws Exception {
		}

	}

}