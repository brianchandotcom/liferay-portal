/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectEntrySearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	@Test
	public void testBaseModelUserPermissions() {
	}

	@Override
	@Test
	public void testLocalizedSearch() {
	}

	@Override
	@Test
	public void testParentBaseModelUserPermissions() {
	}

	@Override
	@Test
	public void testSearchAttachments() {
	}

	@Override
	@Test
	public void testSearchBaseModelWithTrash() {
	}

	@Test
	public void testSearchByAbsoluteSourceURL() throws Exception {
		if (!_isSearchEngine("Elasticsearch")) {
			return;
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "liferay", serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute(
			"groupBaseURL", "http://localhost:8080/web/guest");
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setKeywords("http://localhost:8080/web/guest/liferay");

		List<SearchResult> searchResults = _getSearchResults(searchContext);

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Override
	@Test
	public void testSearchByDDMStructureField() {
	}

	@Test
	public void testSearchByDestinationURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "test", serviceContext);

		List<SearchResult> searchResults = _getSearchResults(
			"http://www.liferay.com");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Test
	public void testSearchByDestinationURLPrefix() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "test", serviceContext);

		List<SearchResult> searchResults = _getSearchResults(
			"http://www.liferay.co");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Test
	public void testSearchByDestinationURLSubstring() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "test", serviceContext);

		List<SearchResult> searchResults = _getSearchResults("liferay");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Test
	public void testSearchByDestinationURLSuffix() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "test", serviceContext);

		List<SearchResult> searchResults = _getSearchResults("liferay.com");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() {
	}

	@Test
	public void testSearchBySourceURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "test", serviceContext);

		List<SearchResult> searchResults = _getSearchResults("test");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Test
	public void testSearchBySourceURLAfterUpdatingChainedRedirectEntries()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_redirectEntryLocalService.addRedirectEntry(
			serviceContext.getScopeGroupId(), "http://localhost/site/page2",
			null, false, "page1", serviceContext);

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://localhost/site/page3",
				null, "http://localhost/site", false, "page2", true,
				serviceContext);

		List<SearchResult> searchResults = _getSearchResults("page2");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Test
	public void testSearchBySourceURLSubstring() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "liferay", serviceContext);

		List<SearchResult> searchResults = _getSearchResults("life");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry.getRedirectEntryId(), searchResult.getClassPK());
	}

	@Override
	@Test
	public void testSearchComments() {
	}

	@Override
	@Test
	public void testSearchCommentsByKeywords() {
	}

	@Override
	@Test
	public void testSearchExpireAllVersions() {
	}

	@Override
	@Test
	public void testSearchExpireLatestVersion() {
	}

	@Override
	@Test
	public void testSearchMixedPhraseKeywords() {
	}

	@Override
	@Test
	public void testSearchMyEntries() {
	}

	@Override
	@Test
	public void testSearchRecentEntries() {
	}

	@Test
	public void testSearchSortingByDestinationURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry1 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com/a",
				null, false, RandomTestUtil.randomString(), serviceContext);
		RedirectEntry redirectEntry2 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com/b",
				null, false, RandomTestUtil.randomString(), serviceContext);

		_assertSearchResults(
			redirectEntry1, redirectEntry2,
			new Sort(
				Field.getSortableFieldName("destinationURL"), Sort.STRING_TYPE,
				false));
		_assertSearchResults(
			redirectEntry2, redirectEntry1,
			new Sort(
				Field.getSortableFieldName("destinationURL"), Sort.STRING_TYPE,
				true));
	}

	@Test
	public void testSearchSortingBySourceURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectEntry redirectEntry1 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "a", serviceContext);
		RedirectEntry redirectEntry2 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com",
				null, false, "b", serviceContext);

		_assertSearchResults(
			redirectEntry1, redirectEntry2,
			new Sort(
				Field.getSortableFieldName("sourceURL"), Sort.STRING_TYPE,
				false));
		_assertSearchResults(
			redirectEntry2, redirectEntry1,
			new Sort(
				Field.getSortableFieldName("sourceURL"), Sort.STRING_TYPE,
				true));
	}

	@Override
	@Test
	public void testSearchStatus() {
	}

	@Override
	@Test
	public void testSearchVersions() {
	}

	@Override
	@Test
	public void testSearchWithinDDMStructure() {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return _redirectEntryLocalService.addRedirectEntry(
			serviceContext.getScopeGroupId(), keywords, null, false, keywords,
			serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_redirectEntryLocalService.deleteRedirectEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return RedirectEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "sourceURL";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		RedirectEntry redirectEntry = (RedirectEntry)baseModel;

		return _redirectEntryLocalService.updateRedirectEntry(
			redirectEntry.getRedirectEntryId(), keywords, null,
			RandomTestUtil.randomBoolean(), keywords);
	}

	private void _assertSearchResults(
			RedirectEntry redirectEntry1, RedirectEntry redirectEntry2,
			Sort sort)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setSorts(sort);

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			getBaseModelClass());

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			indexer.search(searchContext), LocaleUtil.getDefault());

		Assert.assertEquals(searchResults.toString(), 2, searchResults.size());

		SearchResult searchResult1 = searchResults.get(0);

		Assert.assertEquals(
			redirectEntry1.getRedirectEntryId(), searchResult1.getClassPK());

		SearchResult searchResult2 = searchResults.get(1);

		Assert.assertEquals(
			redirectEntry2.getRedirectEntryId(), searchResult2.getClassPK());
	}

	private List<SearchResult> _getSearchResults(SearchContext searchContext)
		throws Exception {

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			getBaseModelClass());

		return SearchResultUtil.getSearchResults(
			indexer.search(searchContext), LocaleUtil.getDefault());
	}

	private List<SearchResult> _getSearchResults(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setKeywords(keywords);

		return _getSearchResults(searchContext);
	}

	private boolean _isSearchEngine(String engine) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		String vendor = searchEngine.getVendor();

		return vendor.equals(engine);
	}

	@Inject
	private static SearchEngineHelper _searchEngineHelper;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

}