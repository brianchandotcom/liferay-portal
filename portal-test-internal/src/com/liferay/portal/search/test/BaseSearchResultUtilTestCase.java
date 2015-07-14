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

package com.liferay.portal.search.test;

import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.SearchResultManagerUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.search.SearchResultManagerImpl;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author André de Oliveira
 */
public abstract class BaseSearchResultUtilTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpRegistryUtil();

		setUpClassNameLocalService();
		setUpFastDateFormatFactoryUtil();
		setUpIndexerRegistry();
		setUpPortalUtil();
		setUpPropsUtil();
		setUpServiceTrackerMap();
		setUpSearchResultManagerUtil();
	}

	protected void assertEmptyCommentRelatedSearchResults(
		SearchResult searchResult) {

		List<RelatedSearchResult<Comment>> commentRelatedSearchResults =
			searchResult.getCommentRelatedSearchResults();

		Assert.assertTrue(commentRelatedSearchResults.isEmpty());
	}

	protected void assertEmptyFileEntryRelatedSearchResults(
		SearchResult searchResult) {

		List<RelatedSearchResult<FileEntry>> fileEntryRelatedSearchResults =
			searchResult.getFileEntryRelatedSearchResults();

		Assert.assertTrue(fileEntryRelatedSearchResults.isEmpty());
	}

	protected void assertEmptyVersions(SearchResult searchResult) {
		List<String> versions = searchResult.getVersions();

		Assert.assertTrue(versions.isEmpty());
	}

	protected SearchResult assertOneSearchResult(Document document) {
		List<SearchResult> searchResults = SearchTestUtil.getSearchResults(
			document);

		Assert.assertEquals(1, searchResults.size());

		return searchResults.get(0);
	}

	protected void setUpClassNameLocalService() throws Exception {
		when(
			classNameLocalService.getClassName(
				SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME_ID)
		).thenReturn(
			className
		);

		when(
			className.getClassName()
		).thenReturn(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME
		);
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			mock(FastDateFormatFactory.class));
	}

	protected void setUpIndexerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			IndexerRegistry.class, new TestIndexerRegistry());
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);

		when(
			portal.getClassName(SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME_ID)
		).thenReturn(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME
		);
	}

	protected void setUpPropsUtil() {
		PropsUtil.setProps(props);
	}

	protected void setUpRegistryUtil() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		mockStatic(
			AssetRendererFactoryRegistryUtil.class, Mockito.CALLS_REAL_METHODS);
		mockStatic(IndexerRegistryUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	protected void setUpSearchResultManagerUtil() {
		SearchResultManagerUtil searchResultManagerUtil =
			new SearchResultManagerUtil();

		SearchResultManager searchResultManager = new SearchResultManagerImpl(
			classNameLocalService, dlAppLocalService, mbMessageLocalService);

		searchResultManagerUtil.setSearchResultManager(searchResultManager);
	}

	protected void setUpServiceTrackerMap() {
		stub(
			method(
				ServiceTrackerCollections.class, "singleValueMap", Class.class,
				String.class, ServiceReferenceMapper.class)
		).toReturn(
			_serviceTrackerMap
		);

		when(
			_serviceTrackerMap.getService(Mockito.anyString())
		).thenReturn(
			null
		);
	}

	@Mock
	protected AssetRenderer assetRenderer;

	@Mock
	protected AssetRendererFactory assetRendererFactory;

	@Mock
	protected ClassName className;

	@Mock
	protected ClassNameLocalService classNameLocalService;

	@Mock
	protected DLAppLocalService dlAppLocalService;

	@Mock
	protected MBMessageLocalService mbMessageLocalService;

	@Mock
	protected Portal portal;

	@Mock
	protected Props props;

	@Mock
	private ServiceTrackerMap<String, SearchResultManager> _serviceTrackerMap;

}