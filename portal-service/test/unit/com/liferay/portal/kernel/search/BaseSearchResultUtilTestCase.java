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

import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;

import javax.portlet.PortletURL;

import org.junit.Assert;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author André de Oliveira
 */
public abstract class BaseSearchResultUtilTestCase extends PowerMockito {

	protected void doSetUp() {
		MockitoAnnotations.initMocks(this);

		setUpFastDateFormatFactory();
		setUpPortal();
		setUpProps();
		setUpRegistries();
	}

	protected List<SearchResult> getSearchResults(Document... documents) {
		Hits hits = new HitsImpl();

		hits.setDocs(documents);

		return SearchResultUtil.getSearchResults(hits, null, portletURL);
	}

	protected Document createDocument(String entryClassName) {
		return createDocument(entryClassName, ENTRY_CLASS_PK);
	}

	protected Document createDocument(
		String entryClassName, long entryClassPk) {

		Document document = new DocumentImpl();

		document.add(new Field(Field.ENTRY_CLASS_NAME, entryClassName));
		document.add(
			new Field(Field.ENTRY_CLASS_PK, String.valueOf(entryClassPk)));

		return document;
	}

	protected Document createDocumentWithAlternateKey(String entryClassName) {
		Document document = createDocument(entryClassName);

		setKeyInDocument(document);

		return document;
	}

	protected void searchSingleDocument(Document document) {
		List<SearchResult> searchResults = getSearchResults(document);

		Assert.assertEquals("one hit, one result", 1, searchResults.size());

		result = searchResults.get(0);
	}

	protected void setKeyInDocument(Document document) {
		document.add(
			new Field(
				Field.CLASS_NAME_ID, String.valueOf(DOCUMENT_CLASS_NAME_ID)));
		document.add(
			new Field(Field.CLASS_PK, String.valueOf(DOCUMENT_CLASS_PK)));
	}

	protected void setUpFastDateFormatFactory() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			mock(FastDateFormatFactory.class));
	}

	protected void setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);

		when(
			portal.getClassName(DOCUMENT_CLASS_NAME_ID)
		).thenReturn(
			DOCUMENT_CLASS_NAME
		);
	}

	protected void setUpProps() {
		PropsUtil.setProps(props);
	}

	protected void setUpRegistries() {
		Registry registry = mock(Registry.class);

		when(
			registry.getRegistry()
		).thenReturn(
			registry
		);

		when(
			registry.setRegistry(registry)
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

		mockStatic(
			AssetRendererFactoryRegistryUtil.class, Mockito.CALLS_REAL_METHODS);

		mockStatic(IndexerRegistryUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	protected static final String DOCUMENT_CLASS_NAME =
		"com.liferay.ClassInDocument";

	protected static final long DOCUMENT_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	protected static final long DOCUMENT_CLASS_PK = RandomTestUtil.randomLong();

	protected static final long ENTRY_CLASS_PK = RandomTestUtil.randomLong();

	protected static final String SUMMARY_CONTENT =
		"A long time ago, in a galaxy far, far away...";

	protected static final String SUMMARY_TITLE = "S.R. Wars";

	@Mock
	protected AssetRenderer assetRenderer;

	@Mock
	protected AssetRendererFactory assetRendererFactory;

	@Mock
	protected Portal portal;

	@Mock
	protected PortletURL portletURL;

	@Mock
	protected Props props;

	protected SearchResult result;

}