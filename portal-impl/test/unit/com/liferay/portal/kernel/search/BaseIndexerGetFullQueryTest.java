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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.util.DLFileEntryIndexer;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Arrays;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareOnlyThisForTest( {
	BooleanQueryFactoryUtil.class, SearchEngineUtil.class
})
@RunWith(PowerMockRunner.class)
public class BaseIndexerGetFullQueryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpBooleanQueryFactoryUtil();
		setUpJSONFactoryUtil();
		setUpPropsUtil();
		setUpRegistryUtil();
		setUpSearchEngineUtil();

		_indexer = new TestIndexer();
	}

	@Test
	public void testGetFullQueryWithAttachments() throws Exception {
		_searchContext.setIncludeAttachments(true);

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME, DLFileEntry.class.getName());

		Assert.assertNull(_searchContext.getAttribute("discussion"));
		Assert.assertArrayEquals(
			new String[] {_CLASS_NAME},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
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

	@Test
	public void testGetFullQueryWithoutAttachmentsOrDiscussions()
		throws Exception {

		_indexer.getFullQuery(_searchContext);

		assertEntryClassNames(_CLASS_NAME);

		Assert.assertNull(_searchContext.getAttribute("discussion"));
		Assert.assertNull(
			_searchContext.getAttribute("relatedEntryClassNames"));
	}

	protected void assertEntryClassNames(String... expectedEntryClassNames) {
		Arrays.sort(expectedEntryClassNames);

		String[] actualEntryClassNames = _searchContext.getEntryClassNames();

		Arrays.sort(actualEntryClassNames);

		Assert.assertArrayEquals(
			expectedEntryClassNames, actualEntryClassNames);
	}

	protected void setUpBooleanQueryFactoryUtil() {
		mockStatic(BooleanQueryFactoryUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(
				BooleanQueryFactoryUtil.class, "create", SearchContext.class
			)
		).toReturn(
			mock(BooleanQuery.class)
		);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		JSONFactory jsonFactory = mock(JSONFactory.class);

		when(
			jsonFactory.createJSONObject()
		).thenReturn(
			mock(JSONObject.class)
		);

		jsonFactoryUtil.setJSONFactory(jsonFactory);
	}

	protected void setUpPropsUtil() {
		Props props = mock(Props.class);

		PropsUtil.setProps(props);
	}

	protected void setUpRegistryUtil() throws Exception {
		Registry registry = new BasicRegistryImpl();

		RegistryUtil.setRegistry(registry);

		registry.registerService(Indexer.class, new DLFileEntryIndexer());
		registry.registerService(Indexer.class, new MBMessageIndexer());
	}

	protected void setUpSearchEngineUtil() {
		mockStatic(SearchEngineUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(
				SearchEngineUtil.class, "getEntryClassNames"
			)
		).toReturn(
			new String[0]
		);
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private Indexer _indexer;
	private final SearchContext _searchContext = new SearchContext();

	private class TestIndexer extends BaseIndexer {

		@Override
		public String[] getClassNames() {
			return new String[] {_CLASS_NAME};
		}

		@Override
		public String getPortletId() {
			return null;
		}

		@Override
		protected void doDelete(Object obj) throws Exception {
		}

		@Override
		protected Document doGetDocument(Object obj) throws Exception {
			return null;
		}

		@Override
		protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL, PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {

			return null;
		}

		@Override
		protected void doReindex(Object obj) throws Exception {
		}

		@Override
		protected void doReindex(String className, long classPK)
			throws Exception {
		}

		@Override
		protected void doReindex(String[] ids) throws Exception {
		}

		@Override
		protected String getPortletId(SearchContext searchContext) {
			return null;
		}

	}

}