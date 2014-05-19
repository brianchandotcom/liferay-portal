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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Arrays;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareOnlyThisForTest( {
	BooleanQueryFactoryUtil.class, IndexerRegistryUtil.class,
	SearchEngineUtil.class
})
@RunWith(PowerMockRunner.class)
public class BaseIndexerGetFullQueryTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpBooleanQueryFactory();
		setUpJSONFactory();
		setUpProps();
		setUpRegistries();
		setUpSearchEngine();

		_baseIndexer = new BaseIndexerTest();
	}

	@Test
	public void testGetFullQueryWithIncludeAttachments() throws Exception {
		_searchContext.setIncludeAttachments(true);

		_getFullQuery();

		assertEntryClassNames("__getClassNames__", DLFileEntry.class.getName());

		assertDiscussionIsUnset();

		assertRelatedEntryClassNamesIsSet();
	}

	@Test
	public void testGetFullQueryWithIncludeDiscussions() throws Exception {
		_searchContext.setIncludeDiscussions(true);

		_getFullQuery();

		assertEntryClassNames("__getClassNames__", MBMessage.class.getName());

		assertDiscussionIsSet();

		assertRelatedEntryClassNamesIsSet();
	}

	@Test
	public void testGetFullQueryWithIncludeDiscussionsAndAttachments()
		throws Exception {

		_searchContext.setIncludeAttachments(true);
		_searchContext.setIncludeDiscussions(true);

		_getFullQuery();

		assertEntryClassNames(
			"__getClassNames__", DLFileEntry.class.getName(),
			MBMessage.class.getName());

		assertDiscussionIsSet();

		assertRelatedEntryClassNamesIsSet();
	}

	@Test
	public void testGetFullQueryWithoutIncludeDiscussionsOrAttachments()
		throws Exception {

		_getFullQuery();

		assertEntryClassNames("__getClassNames__");

		assertDiscussionIsUnset();

		assertRelatedEntryClassNamesIsUnset();
	}

	protected void assertDiscussionIsSet() {
		Assert.assertEquals(
			Boolean.TRUE, _searchContext.getAttribute("discussion"));
	}

	protected void assertDiscussionIsUnset() {
		Assert.assertNull(_searchContext.getAttribute("discussion"));
	}

	protected void assertEntryClassNames(String... expected) {
		String[] entryClassNames = _searchContext.getEntryClassNames();

		Arrays.sort(entryClassNames);
		Arrays.sort(expected);

		Assert.assertArrayEquals(expected, entryClassNames);
	}

	protected void assertRelatedEntryClassNamesIsSet() {
		Assert.assertArrayEquals(
			new String[] {"__getClassNames__"},
			(String[])_searchContext.getAttribute("relatedEntryClassNames"));
	}

	protected void assertRelatedEntryClassNamesIsUnset() {
		Assert.assertNull(
			_searchContext.getAttribute("relatedEntryClassNames"));
	}

	protected void setUpBooleanQueryFactory() {
		mockStatic(BooleanQueryFactoryUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(
				BooleanQueryFactoryUtil.class, "create", SearchContext.class
			)
		).toReturn(
			mock(BooleanQuery.class)
		);
	}

	protected void setUpJSONFactory() {
		JSONFactory jsonFactory = mock(JSONFactory.class);

		when(
			jsonFactory.createJSONObject()
		).thenReturn(
			mock(JSONObject.class)
		);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactory);
	}

	protected void setUpProps() {
		Props props = mock(Props.class);

		PropsUtil.setProps(props);
	}

	protected void setUpRegistries() {
		Registry registry = mock(Registry.class);

		when(
			registry.setRegistry(registry)
		).thenReturn(
			registry
		);

		when(
			registry.getRegistry()
		).thenReturn(
			registry
		);

		ServiceTracker<Object, Object> serviceTracker = mock(
			ServiceTracker.class);

		when(
			registry.trackServices(
				(Class<Object>)Matchers.any(),
				(ServiceTrackerCustomizer<Object, Object>)Matchers.any())
		).thenReturn(
			serviceTracker
		);

		RegistryUtil.setRegistry(registry);

		mockStatic(IndexerRegistryUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	protected void setUpSearchEngine() {
		mockStatic(SearchEngineUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(
				SearchEngineUtil.class, "getEntryClassNames"
			)
		).toReturn(
			new String[0]
		);
	}

	private void _getFullQuery() throws SearchException {
		_baseIndexer.getFullQuery(_searchContext);
	}

	private BaseIndexer _baseIndexer;
	private SearchContext _searchContext = new SearchContext();

	private class BaseIndexerTest extends BaseIndexer {

		@Override
		public String[] getClassNames() {
			return new String[] {"__getClassNames__"};
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