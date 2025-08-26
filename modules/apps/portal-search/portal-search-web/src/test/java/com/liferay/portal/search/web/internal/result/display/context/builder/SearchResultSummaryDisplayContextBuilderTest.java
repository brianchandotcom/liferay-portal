/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.result.display.context.builder;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.internal.summary.SummaryBuilderFactoryImpl;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Lino Alves
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayContextBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_searchResultContributorServiceRegistration =
			bundleContext.registerService(
				SearchResultContributor.class,
				Mockito.mock(SearchResultContributor.class), null);
	}

	@AfterClass
	public static void tearDownClass() {
		if (_searchResultContributorServiceRegistration != null) {
			_searchResultContributorServiceRegistration.unregister();
		}
	}

	@Before
	public void setUp() throws Exception {
		setUpAssetRenderer();
		_setUpGroupLocalService();
		_setUpLocaleThreadLocal();
		_setUpUser();
		_setUpUserLocalService();

		themeDisplay = _createThemeDisplay();
	}

	@After
	public void tearDown() {
		searchUtilMockedStatic.close();
	}

	@Test
	public void testClassFieldsWithoutAssetTagsOrCategories() throws Exception {
		PortletURL portletURL = Mockito.mock(PortletURL.class);

		Mockito.doReturn(
			portletURL
		).when(
			portletURLFactory
		).getPortletURL();

		String className = RandomTestUtil.randomString();

		long classPK = RandomTestUtil.randomLong();

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(_createDocument(className, classPK));

		Assert.assertEquals(
			className, searchResultSummaryDisplayContext.getClassName());
		Assert.assertEquals(
			classPK, searchResultSummaryDisplayContext.getClassPK());
		Assert.assertEquals(
			portletURL, searchResultSummaryDisplayContext.getPortletURL());
	}

	@Test
	public void testCreationDate() throws Exception {
		String className = RandomTestUtil.randomString();

		long classPK = RandomTestUtil.randomLong();

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		Document document = _createDocument(className, classPK);

		_assertCreationDateMissing(document);

		document.addKeyword(Field.CREATE_DATE, "20180425171442");

		_assertCreationDate(document, "Apr 25, 18, 5:14 PM");

		_assertCreationDate(
			document, "25 de abr. de 18 17:14", LocaleUtil.BRAZIL);
		_assertCreationDate(document, "18年4月25日 下午5:14", LocaleUtil.CHINA);
		_assertCreationDate(document, "25.04.18, 17:14", LocaleUtil.GERMANY);
		_assertCreationDate(document, "18. ápr. 25. 17:14", LocaleUtil.HUNGARY);
		_assertCreationDate(document, "25 apr 18, 17:14", LocaleUtil.ITALY);
		_assertCreationDate(document, "18/04/25 17:14", LocaleUtil.JAPAN);
		_assertCreationDate(
			document, "25 apr. 18 17:14", LocaleUtil.NETHERLANDS);
		_assertCreationDate(document, "25 abr 18 17:14", LocaleUtil.SPAIN);
	}

	@Test
	public void testNoStagingLabel() throws Exception {
		String className = RandomTestUtil.randomString();

		long classPK = RandomTestUtil.randomLong();

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		_whenGroupLocalServiceGetGroup(false);

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(_createDocument(className, classPK));

		Assert.assertEquals(
			_SUMMARY_TITLE,
			searchResultSummaryDisplayContext.getHighlightedTitle());
	}

	@Test
	public void testResultIsTemporarilyUnavailable() throws Exception {
		_ruinAssetRendererFactoryLookup();

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(Mockito.mock(Document.class));

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isTemporarilyUnavailable());
	}

	@Test
	public void testStagingLabel() throws Exception {
		String className = RandomTestUtil.randomString();

		long classPK = RandomTestUtil.randomLong();

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		_whenGroupLocalServiceGetGroup(true);
		_whenLanguageGet("staged");

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(_createDocument(className, classPK));

		Assert.assertEquals(
			_SUMMARY_TITLE + " (staged)",
			searchResultSummaryDisplayContext.getHighlightedTitle());
	}

	@Test
	public void testTagsURLDownloadAndUserPortraitFromResult()
		throws Exception {

		long userId = RandomTestUtil.randomLong();

		AssetEntry assetEntry = _createAssetEntryWithTagsPresent(userId);

		String className = RandomTestUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		_whenAssetEntryLocalServiceFetchEntry(assetEntry, className, classPK);

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryHasPermission(true);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		String urlDownload = RandomTestUtil.randomString();

		_whenAssetRendererGetURLDownload(assetRenderer, urlDownload);

		_whenIndexerRegistryGetIndexer(className, _createIndexer());

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(_createDocument(className, classPK));

		_assertAssetRendererURLDownloadVisible(
			searchResultSummaryDisplayContext, urlDownload);

		_assertTagsVisible(classPK, searchResultSummaryDisplayContext);

		_assertUserPortraitVisible(searchResultSummaryDisplayContext, userId);
	}

	@Test
	public void testURLDownloadHiddenFromResult() throws Exception {
		long userId = RandomTestUtil.randomLong();

		AssetEntry assetEntry = _createAssetEntryWithTagsPresent(userId);

		String className = RandomTestUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		_whenAssetEntryLocalServiceFetchEntry(assetEntry, className, classPK);

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		_whenAssetRendererFactoryHasPermission(false);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		String urlDownload = RandomTestUtil.randomString();

		_whenAssetRendererGetURLDownload(assetRenderer, urlDownload);

		_whenIndexerRegistryGetIndexer(className, _createIndexer());

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(_createDocument(className, classPK));

		Assert.assertEquals(
			urlDownload,
			searchResultSummaryDisplayContext.getAssetRendererURLDownload());
		Assert.assertFalse(
			searchResultSummaryDisplayContext.
				isAssetRendererURLDownloadVisible());
	}

	@Test
	public void testUserPortraitFromResultButTagsAndURLDownloadFromRoot()
		throws Exception {

		long userId = RandomTestUtil.randomInt(2, Integer.MAX_VALUE);

		AssetEntry assetEntry = _createAssetEntry(userId);

		long rootUserId = userId - 1;

		AssetEntry rootAssetEntry = _createAssetEntryWithTagsPresent(
			rootUserId);

		String className = RandomTestUtil.randomString();

		long classPK = RandomTestUtil.randomInt(2, Integer.MAX_VALUE);

		long rootClassPK = classPK - 1;

		_whenAssetEntryLocalServiceFetchEntry(assetEntry, className, classPK);

		_whenAssetEntryLocalServiceFetchEntry(
			rootAssetEntry, className, rootClassPK);

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		AssetRenderer<?> rootAssetRenderer = Mockito.mock(AssetRenderer.class);

		_whenAssetRendererFactoryGetAssetRenderer(
			rootAssetRenderer, rootClassPK);

		_whenAssetRendererFactoryHasPermission(true);

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		String rootURLDownload = RandomTestUtil.randomString();

		_whenAssetRendererGetURLDownload(rootAssetRenderer, rootURLDownload);

		_whenIndexerRegistryGetIndexer(className, _createIndexer());

		Document document = _createDocument(className, classPK);

		document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, rootClassPK);

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(document);

		_assertAssetRendererURLDownloadVisible(
			searchResultSummaryDisplayContext, rootURLDownload);

		_assertTagsVisible(rootClassPK, searchResultSummaryDisplayContext);

		_assertUserPortraitVisible(searchResultSummaryDisplayContext, userId);
	}

	@Test
	public void testViewURL1() throws Exception {
		long classNameId = RandomTestUtil.randomLong();

		_whenClassNameLocalServiceGetClassName(
			classNameId, RandomTestUtil.randomString());

		String className = RandomTestUtil.randomString();

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className);

		long classPK = RandomTestUtil.randomLong();

		_whenSearchUtilGetSearchResultViewURL(className, classPK);
		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK);

		Document document = _createDocument(className, classPK);

		document.addKeyword(Field.CLASS_NAME_ID, classNameId);
		document.addKeyword(Field.CLASS_PK, RandomTestUtil.randomLong());

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(document);

		Assert.assertEquals(
			className + classPK,
			searchResultSummaryDisplayContext.getViewURL());
	}

	@Test
	public void testViewURL2() throws Exception {
		String className1 = RandomTestUtil.randomString();

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className1);

		long classNameId = RandomTestUtil.randomLong();

		_whenClassNameLocalServiceGetClassName(classNameId, className1);

		long classPK1 = RandomTestUtil.randomLong();

		_whenSearchUtilGetSearchResultViewURL(className1, classPK1);

		String className2 = RandomTestUtil.randomString();

		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			className2);

		long classPK2 = RandomTestUtil.randomLong();

		_whenAssetRendererFactoryGetAssetRenderer(assetRenderer, classPK2);

		Document document = _createDocument(className2, classPK2);

		document.addKeyword(Field.CLASS_NAME_ID, classNameId);
		document.addKeyword(Field.CLASS_PK, classPK1);

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(document);

		Assert.assertEquals(
			className1 + classPK1,
			searchResultSummaryDisplayContext.getViewURL());
	}

	protected SearchResultSummaryDisplayContext build(Document document)
		throws Exception {

		SearchResultSummaryDisplayContextBuilder
			searchResultSummaryDisplayContextBuilder =
				_createSearchResultSummaryDisplayContextBuilder();

		searchResultSummaryDisplayContextBuilder.setDocument(document);

		return searchResultSummaryDisplayContextBuilder.build();
	}

	protected void setUpAssetRenderer() throws Exception {
		Mockito.doReturn(
			_SUMMARY_CONTENT
		).when(
			assetRenderer
		).getSearchSummary(
			Mockito.nullable(Locale.class)
		);

		Mockito.doReturn(
			_SUMMARY_TITLE
		).when(
			assetRenderer
		).getTitle(
			Mockito.nullable(Locale.class)
		);
	}

	protected AssetEntryLocalService assetEntryLocalService = Mockito.mock(
		AssetEntryLocalService.class);
	protected AssetRenderer<?> assetRenderer = Mockito.mock(
		AssetRenderer.class);
	protected AssetRendererFactory<?> assetRendererFactory = Mockito.mock(
		AssetRendererFactory.class);
	protected AssetRendererFactoryLookup assetRendererFactoryLookup =
		Mockito.mock(AssetRendererFactoryLookup.class);
	protected ClassNameLocalService classNameLocalService = Mockito.mock(
		ClassNameLocalService.class);
	protected FastDateFormatFactory fastDateFormatFactory =
		new FastDateFormatFactoryImpl();
	protected Group group = Mockito.mock(Group.class);
	protected GroupLocalService groupLocalService = Mockito.mock(
		GroupLocalService.class);
	protected IndexerRegistry indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	protected Language language = Mockito.mock(Language.class);
	protected Locale locale = LocaleUtil.US;
	protected PermissionChecker permissionChecker = Mockito.mock(
		PermissionChecker.class);
	protected PortletURLFactory portletURLFactory = Mockito.mock(
		PortletURLFactory.class);
	protected MockedStatic<SearchUtil> searchUtilMockedStatic =
		Mockito.mockStatic(SearchUtil.class);
	protected ThemeDisplay themeDisplay;
	protected User user = Mockito.mock(User.class);
	protected UserLocalService userLocalService = Mockito.mock(
		UserLocalService.class);

	private void _assertAssetRendererURLDownloadVisible(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		String urlDownload) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.
				isAssetRendererURLDownloadVisible());

		Assert.assertEquals(
			urlDownload,
			searchResultSummaryDisplayContext.getAssetRendererURLDownload());
	}

	private void _assertCreationDate(
			Document document, String expectedCreationDateString)
		throws Exception {

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(document);

		Assert.assertEquals(
			expectedCreationDateString,
			searchResultSummaryDisplayContext.getCreationDateString());

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isCreationDateVisible());
	}

	private void _assertCreationDate(
			Document document, String expectedCreationDateString,
			Locale locale1)
		throws Exception {

		locale = locale1;

		_assertCreationDate(document, expectedCreationDateString);
	}

	private void _assertCreationDateMissing(Document document)
		throws Exception {

		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext =
			build(document);

		Assert.assertNull(
			searchResultSummaryDisplayContext.getCreationDateString());

		Assert.assertFalse(
			searchResultSummaryDisplayContext.isCreationDateVisible());
	}

	private void _assertTagsVisible(
		long classPK,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isAssetCategoriesOrTagsVisible());

		Assert.assertEquals(
			classPK, searchResultSummaryDisplayContext.getClassPK());
	}

	private void _assertUserPortraitVisible(
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext,
		long userId) {

		Assert.assertTrue(
			searchResultSummaryDisplayContext.isUserPortraitVisible());

		Assert.assertEquals(
			userId, searchResultSummaryDisplayContext.getAssetEntryUserId());
	}

	private AssetEntry _createAssetEntry(long userId) {
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

	private AssetEntry _createAssetEntryWithTagsPresent(long userId) {
		AssetEntry assetEntry = _createAssetEntry(userId);

		Mockito.doReturn(
			new String[] {RandomTestUtil.randomString()}
		).when(
			assetEntry
		).getTagNames();

		return assetEntry;
	}

	private Document _createDocument(String className, long classPK) {
		Document document = new DocumentImpl();

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(className, classPK);

		return document;
	}

	private Indexer<?> _createIndexer() throws Exception {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doReturn(
			new Summary(LocaleUtil.US, null, null)
		).when(
			indexer
		).getSummary(
			Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any()
		);

		return indexer;
	}

	private SearchResultSummaryDisplayContextBuilder
		_createSearchResultSummaryDisplayContextBuilder() {

		SearchResultSummaryDisplayContextBuilder
			searchResultSummaryDisplayContextBuilder =
				new SearchResultSummaryDisplayContextBuilder();

		searchResultSummaryDisplayContextBuilder.setAssetEntryLocalService(
			assetEntryLocalService);
		searchResultSummaryDisplayContextBuilder.setAssetRendererFactoryLookup(
			assetRendererFactoryLookup);
		searchResultSummaryDisplayContextBuilder.setClassNameLocalService(
			classNameLocalService);
		searchResultSummaryDisplayContextBuilder.setFastDateFormatFactory(
			fastDateFormatFactory);
		searchResultSummaryDisplayContextBuilder.setGroupLocalService(
			groupLocalService);
		searchResultSummaryDisplayContextBuilder.setIndexerRegistry(
			indexerRegistry);
		searchResultSummaryDisplayContextBuilder.setLanguage(language);
		searchResultSummaryDisplayContextBuilder.setLocale(locale);
		searchResultSummaryDisplayContextBuilder.setPortletURLFactory(
			portletURLFactory);
		searchResultSummaryDisplayContextBuilder.setResourceActions(
			Mockito.mock(ResourceActions.class));
		searchResultSummaryDisplayContextBuilder.setSearchResultPreferences(
			Mockito.mock(SearchResultPreferences.class));
		searchResultSummaryDisplayContextBuilder.setSummaryBuilderFactory(
			new SummaryBuilderFactoryImpl());
		searchResultSummaryDisplayContextBuilder.setThemeDisplay(themeDisplay);
		searchResultSummaryDisplayContextBuilder.setUserLocalService(
			userLocalService);

		return searchResultSummaryDisplayContextBuilder;
	}

	private ThemeDisplay _createThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setUser(Mockito.mock(User.class));

		return themeDisplay;
	}

	private void _ruinAssetRendererFactoryLookup() {
		Mockito.doThrow(
			RuntimeException.class
		).when(
			assetRendererFactoryLookup
		).getAssetRendererFactoryByClassName(
			Mockito.anyString()
		);
	}

	private void _setUpGroupLocalService() {
		Mockito.doReturn(
			group
		).when(
			groupLocalService
		).fetchGroup(
			Mockito.anyLong()
		);
	}

	private void _setUpLocaleThreadLocal() {
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	private void _setUpUser() throws Exception {
		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			user
		).getPortraitURL(
			Mockito.any()
		);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			user
		).getPortraitId();
	}

	private void _setUpUserLocalService() {
		Mockito.doReturn(
			user
		).when(
			userLocalService
		).fetchUser(
			Mockito.anyLong()
		);
	}

	private void _whenAssetEntryLocalServiceFetchEntry(
		AssetEntry assetEntry, String className, long classPK) {

		Mockito.doReturn(
			assetEntry
		).when(
			assetEntryLocalService
		).fetchEntry(
			className, classPK
		);
	}

	private void _whenAssetRendererFactoryGetAssetRenderer(
			AssetRenderer<?> assetRenderer, long classPK)
		throws Exception {

		Mockito.doReturn(
			assetRenderer
		).when(
			assetRendererFactory
		).getAssetRenderer(
			classPK
		);
	}

	private void _whenAssetRendererFactoryHasPermission(boolean hasPermission)
		throws Exception {

		Mockito.doReturn(
			hasPermission
		).when(
			assetRendererFactory
		).hasPermission(
			Mockito.any(), Mockito.anyLong(), Mockito.anyString()
		);
	}

	private void
		_whenAssetRendererFactoryLookupGetAssetRendererFactoryByClassName(
			String className) {

		Mockito.doReturn(
			assetRendererFactory
		).when(
			assetRendererFactoryLookup
		).getAssetRendererFactoryByClassName(
			className
		);
	}

	private void _whenAssetRendererGetURLDownload(
		AssetRenderer<?> assetRenderer, String urlDownload) {

		Mockito.doReturn(
			urlDownload
		).when(
			assetRenderer
		).getURLDownload(
			themeDisplay
		);
	}

	private void _whenClassNameLocalServiceGetClassName(
			long classNameId, String value)
		throws Exception {

		ClassName className = Mockito.mock(ClassName.class);

		Mockito.doReturn(
			value
		).when(
			className
		).getClassName();

		Mockito.doReturn(
			className
		).when(
			classNameLocalService
		).getClassName(
			Mockito.eq(classNameId)
		);
	}

	private void _whenGroupLocalServiceGetGroup(boolean stagingGroup)
		throws Exception {

		Mockito.doReturn(
			stagingGroup
		).when(
			group
		).isStagingGroup();
	}

	private void _whenIndexerRegistryGetIndexer(
		String className, Indexer<?> indexer) {

		Mockito.doReturn(
			indexer
		).when(
			indexerRegistry
		).getIndexer(
			className
		);
	}

	private void _whenLanguageGet(String string) {
		Mockito.doReturn(
			string
		).when(
			language
		).get(
			Mockito.nullable(HttpServletRequest.class), Mockito.anyString()
		);
	}

	private void _whenSearchUtilGetSearchResultViewURL(
		String className, long classPK) {

		searchUtilMockedStatic.when(
			() -> SearchUtil.getSearchResultViewURL(
				Mockito.any(), Mockito.any(), Mockito.eq(className),
				Mockito.eq(classPK), Mockito.eq(false), Mockito.isNull())
		).thenReturn(
			className + classPK
		);
	}

	private static final String _SUMMARY_CONTENT =
		RandomTestUtil.randomString();

	private static final String _SUMMARY_TITLE = RandomTestUtil.randomString();

	private static ServiceRegistration<SearchResultContributor>
		_searchResultContributorServiceRegistration;

}