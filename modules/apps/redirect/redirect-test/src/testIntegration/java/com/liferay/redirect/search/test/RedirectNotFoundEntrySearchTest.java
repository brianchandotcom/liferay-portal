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

package com.liferay.redirect.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
public class RedirectNotFoundEntrySearchTest extends BaseSearchTestCase {

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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), "/page");

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setAttribute(
			"groupBaseURL", "http://localhost:8080/web/guest");
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setKeywords("http://localhost:8080/web/guest/page");

		List<SearchResult> searchResults = _getSearchResults(searchContext);

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectNotFoundEntry.getRedirectNotFoundEntryId(),
			searchResult.getClassPK());
	}

	@Override
	@Test
	public void testSearchByDDMStructureField() {
	}

	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() {
	}

	@Test
	public void testSearchByURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), "/path/page");

		List<SearchResult> searchResults = _getSearchResults("/path/page");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectNotFoundEntry.getRedirectNotFoundEntryId(),
			searchResult.getClassPK());
	}

	@Test
	public void testSearchByURLPrefix() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), "/path/page");

		List<SearchResult> searchResults = _getSearchResults("/path");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectNotFoundEntry.getRedirectNotFoundEntryId(),
			searchResult.getClassPK());
	}

	@Test
	public void testSearchByURLSuffix() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), "/path/page");

		List<SearchResult> searchResults = _getSearchResults("/page");

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			redirectNotFoundEntry.getRedirectNotFoundEntryId(),
			searchResult.getClassPK());
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

		AtomicReference<RedirectNotFoundEntry>
			redirectNotFoundEntryAtomicReference = new AtomicReference<>();

		redirectNotFoundEntryAtomicReference.set(
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				serviceContext.getScopeGroup(), keywords));

		return redirectNotFoundEntryAtomicReference.get();
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_redirectNotFoundEntryLocalService.deleteRedirectNotFoundEntry(
			primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return RedirectNotFoundEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "url";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
		BaseModel<?> baseModel, String keywords,
		ServiceContext serviceContext) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			(RedirectNotFoundEntry)baseModel;

		redirectNotFoundEntry.setUrl(keywords);

		return _redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
			redirectNotFoundEntry);
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

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}