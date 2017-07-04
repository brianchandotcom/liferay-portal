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

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.test.SearchTestUtil;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;

import java.util.Locale;

import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Lino Alves
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayBuilderTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpAssetRenderer();
		setUpHtmlUtil();
		setUpLocaleThreadLocal();
		setUpProps();

		themeDisplay = createThemeDisplay();
	}

	@Test
	public void testClassFieldsWithoutAssetTagsOrCategories() throws Exception {
		PortletURL portletURL = Mockito.mock(PortletURL.class);

		Mockito.doReturn(
			portletURL
		).when(
			portletURLFactory
		).getPortletURL();

		String entryClassName = RandomTestUtil.randomString();

		long entryClassPK = RandomTestUtil.randomLong();

		whenAssetRendererFactoryGetAssetRenderer(entryClassPK, assetRenderer);

		whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			entryClassName);

		SearchResultSummaryDisplayBuilder searchResultSummaryDisplayBuilder =
			createSearchResultSummaryDisplayBuilder();

		searchResultSummaryDisplayBuilder.setDocument(
			createDocument(entryClassName, entryClassPK));

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			searchResultSummaryDisplayBuilder.build();

		Assert.assertEquals(
			entryClassName, searchResultSummaryDisplayContext.getClassName());
		Assert.assertEquals(
			entryClassPK, searchResultSummaryDisplayContext.getClassPK());
		Assert.assertEquals(
			portletURL, searchResultSummaryDisplayContext.getPortletURL());
	}

	@Test
	public void testResultIsTemporarilyUnavailable() throws Exception {
		ruinAssetRendererFactoryLookup();

		SearchResultSummaryDisplayBuilder searchResultSummaryDisplayBuilder =
			createSearchResultSummaryDisplayBuilder();

		searchResultSummaryDisplayBuilder.setDocument(
			Mockito.mock(Document.class));

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			searchResultSummaryDisplayBuilder.build();

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isTemporarilyUnavailable());
	}

	@Test
	public void testTagsURLDownloadAndUserPortraitFromResult()
		throws Exception {

		long userId = RandomTestUtil.randomLong();

		AssetEntry assetEntry = createAssetEntryWithTagsPresent(userId);

		String className = RandomTestUtil.randomString();

		long entryClassPK = RandomTestUtil.randomLong();

		whenAssetEntryLocalServiceFetchEntry(
			className, entryClassPK, assetEntry);

		whenAssetRendererFactoryGetAssetRenderer(entryClassPK, assetRenderer);

		whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		String urlDownload = RandomTestUtil.randomString();

		whenAssetRendererGetURLDownload(assetRenderer, urlDownload);

		whenIndexerRegistryGetIndexer(className, createIndexer());

		SearchResultSummaryDisplayBuilder searchResultSummaryDisplayBuilder =
			createSearchResultSummaryDisplayBuilder();

		searchResultSummaryDisplayBuilder.setDocument(
			createDocument(className, entryClassPK));

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			searchResultSummaryDisplayBuilder.build();

		assertAssetRendererURLDownloadVisible(
			urlDownload, searchResultSummaryDisplayContext);

		assertTagsVisible(entryClassPK, searchResultSummaryDisplayContext);

		assertUserPortraitVisible(userId, searchResultSummaryDisplayContext);
	}

	@Test
	public void testUserPortraitFromResultButTagsAndURLDownloadFromRoot()
		throws Exception {

		long userId = RandomTestUtil.randomInt(2, Integer.MAX_VALUE);

		AssetEntry assetEntry = createAssetEntry(userId);

		long rootUserId = userId - 1;

		AssetEntry rootAssetEntry = createAssetEntryWithTagsPresent(rootUserId);

		String className = RandomTestUtil.randomString();

		long entryClassPK = RandomTestUtil.randomInt(2, Integer.MAX_VALUE);

		long rootEntryClassPK = entryClassPK - 1;

		whenAssetEntryLocalServiceFetchEntry(
			className, entryClassPK, assetEntry);

		whenAssetEntryLocalServiceFetchEntry(
			className, rootEntryClassPK, rootAssetEntry);

		whenAssetRendererFactoryGetAssetRenderer(entryClassPK, assetRenderer);

		AssetRenderer<?> rootAssetRenderer = Mockito.mock(AssetRenderer.class);

		whenAssetRendererFactoryGetAssetRenderer(
			rootEntryClassPK, rootAssetRenderer);

		whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		String rootURLDownload = RandomTestUtil.randomString();

		whenAssetRendererGetURLDownload(rootAssetRenderer, rootURLDownload);

		whenIndexerRegistryGetIndexer(className, createIndexer());

		SearchResultSummaryDisplayBuilder searchResultSummaryDisplayBuilder =
			createSearchResultSummaryDisplayBuilder();

		Document document = createDocument(className, entryClassPK);

		document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, rootEntryClassPK);

		searchResultSummaryDisplayBuilder.setDocument(document);

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			searchResultSummaryDisplayBuilder.build();

		assertAssetRendererURLDownloadVisible(
			rootURLDownload, searchResultSummaryDisplayContext);

		assertTagsVisible(rootEntryClassPK, searchResultSummaryDisplayContext);

		assertUserPortraitVisible(userId, searchResultSummaryDisplayContext);
	}

	protected void assertAssetRendererURLDownloadVisible(
		String urlDownload,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.
				isAssetRendererURLDownloadVisible());

		Assert.assertEquals(
			urlDownload,
			searchResultSummaryDisplayContext.getAssetRendererURLDownload());
	}

	protected void assertTagsVisible(
		long entryClassPK,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isAssetCategoriesOrTagsVisible());

		Assert.assertEquals(
			entryClassPK, searchResultSummaryDisplayContext.getClassPK());
	}

	protected void assertUserPortraitVisible(
		long userId,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isUserPortraitVisible());

		Assert.assertEquals(
			userId, searchResultSummaryDisplayContext.getAssetEntryUserId());
	}

	protected AssetEntry createAssetEntry(long userId) {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			assetRenderer
		).when(
			assetEntry
		).getAssetRenderer();

		Mockito.doReturn(
			assetRendererFactory
		).when(
			assetEntry
		).getAssetRendererFactory();

		Mockito.doReturn(
			userId
		).when(
			assetEntry
		).getUserId();

		return assetEntry;
	}

	protected AssetEntry createAssetEntryWithTagsPresent(long rootUserId) {
		AssetEntry rootAssetEntry = createAssetEntry(rootUserId);

		Mockito.doReturn(
			new String[] {RandomTestUtil.randomString()}
		).when(
			rootAssetEntry
		).getTagNames();

		return rootAssetEntry;
	}

	protected Document createDocument(
		String entryClassName, long entryClassPK) {

		Document document = new DocumentImpl();

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(entryClassName, entryClassPK);

		return document;
	}

	protected Indexer createIndexer() throws Exception {
		Indexer indexer = Mockito.mock(Indexer.class);

		Mockito.doReturn(
			new Summary(Locale.US, null, null)
		).when(
			indexer
		).getSummary(
			Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any()
		);

		return indexer;
	}

	protected SearchResultSummaryDisplayBuilder
		createSearchResultSummaryDisplayBuilder() {

		SearchResultSummaryDisplayBuilder searchResultSummaryDisplayBuilder =
			new SearchResultSummaryDisplayBuilder();

		searchResultSummaryDisplayBuilder.setAssetEntryLocalService(
			assetEntryLocalService);
		searchResultSummaryDisplayBuilder.setAssetRendererFactoryLookup(
			assetRendererFactoryLookup);
		searchResultSummaryDisplayBuilder.setIndexerRegistry(indexerRegistry);
		searchResultSummaryDisplayBuilder.setLanguage(
			Mockito.mock(Language.class));
		searchResultSummaryDisplayBuilder.setLocale(Locale.US);
		searchResultSummaryDisplayBuilder.setPortletURLFactory(
			portletURLFactory);
		searchResultSummaryDisplayBuilder.setResourceActions(
			Mockito.mock(ResourceActions.class));
		searchResultSummaryDisplayBuilder.setSearchResultPreferences(
			Mockito.mock(SearchResultPreferences.class));
		searchResultSummaryDisplayBuilder.setSearchResultViewURLSupplier(
			Mockito.mock(SearchResultViewURLSupplier.class));
		searchResultSummaryDisplayBuilder.setThemeDisplay(themeDisplay);

		return searchResultSummaryDisplayBuilder;
	}

	protected ThemeDisplay createThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setUser(Mockito.mock(User.class));
		themeDisplay.setPermissionChecker(permissionChecker);

		return themeDisplay;
	}

	protected void ruinAssetRendererFactoryLookup() {
		Mockito.doThrow(
			RuntimeException.class
		).when(
			assetRendererFactoryLookup
		).getAssetRendererFactoryByClassName(
			Mockito.anyString()
		);
	}

	protected void setUpAssetRenderer() throws Exception {
		Mockito.doReturn(
			SearchTestUtil.SUMMARY_CONTENT
		).when(
			assetRenderer
		).getSearchSummary(
			(Locale)Matchers.any()
		);

		Mockito.doReturn(
			SearchTestUtil.SUMMARY_TITLE
		).when(
			assetRenderer
		).getTitle(
			(Locale)Matchers.any()
		);
	}

	protected void setUpHtmlUtil() throws Exception {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(Mockito.mock(Html.class));
	}

	protected void setUpLocaleThreadLocal() {
		LocaleThreadLocal.setThemeDisplayLocale(Locale.US);
	}

	protected void setUpProps() {
		PropsUtil.setProps(Mockito.mock(Props.class));
	}

	protected void whenAssetEntryLocalServiceFetchEntry(
		String className, long classPK, AssetEntry assetEntry) {

		Mockito.doReturn(
			assetEntry
		).when(
			assetEntryLocalService
		).fetchEntry(
			className, classPK
		);
	}

	protected void whenAssetRendererFactoryGetAssetRenderer(
			long entryClassPK, AssetRenderer<?> assetRenderer)
		throws Exception {

		Mockito.doReturn(
			assetRenderer
		).when(
			assetRendererFactory
		).getAssetRenderer(
			entryClassPK
		);
	}

	protected void
		whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			String className) {

		Mockito.doReturn(
			assetRendererFactory
		).when(
			assetRendererFactoryLookup
		).getAssetRendererFactoryByClassName(
			className
		);
	}

	protected void whenAssetRendererGetURLDownload(
		AssetRenderer<?> assetRenderer, String urlDownload) {

		Mockito.doReturn(
			urlDownload
		).when(
			assetRenderer
		).getURLDownload(
			themeDisplay
		);
	}

	protected void whenIndexerRegistryGetIndexer(
		String className, Indexer<?> indexer) {

		Mockito.doReturn(
			indexer
		).when(
			indexerRegistry
		).getIndexer(
			className
		);
	}

	@Mock
	protected AssetEntryLocalService assetEntryLocalService;

	@Mock
	protected AssetRenderer<?> assetRenderer;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory;

	@Mock
	protected AssetRendererFactoryLookup assetRendererFactoryLookup;

	@Mock
	protected IndexerRegistry indexerRegistry;

	@Mock
	protected PermissionChecker permissionChecker;

	@Mock
	protected PortletURLFactory portletURLFactory;

	protected ThemeDisplay themeDisplay;

}