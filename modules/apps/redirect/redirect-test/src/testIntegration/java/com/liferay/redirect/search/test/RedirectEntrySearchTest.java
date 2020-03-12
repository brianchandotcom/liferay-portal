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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
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
import org.junit.Ignore;
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

	@Ignore
	@Override
	@Test
	public void testBaseModelUserPermissions() {
	}

	@Ignore
	@Override
	@Test
	public void testLocalizedSearch() {
	}

	@Ignore
	@Override
	@Test
	public void testParentBaseModelUserPermissions() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchAttachments() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchBaseModelWithTrash() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByDDMStructureField() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchComments() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchCommentsByKeywords() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireAllVersions() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireLatestVersion() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchMyEntries() {
	}

	@Ignore
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
				RandomTestUtil.randomString(), true, null, serviceContext);
		RedirectEntry redirectEntry2 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com/b",
				RandomTestUtil.randomString(), true, null, serviceContext);

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
				serviceContext.getScopeGroupId(), "http://www.liferay.com", "a",
				true, null, serviceContext);
		RedirectEntry redirectEntry2 =
			_redirectEntryLocalService.addRedirectEntry(
				serviceContext.getScopeGroupId(), "http://www.liferay.com", "b",
				true, null, serviceContext);

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

	@Ignore
	@Override
	@Test
	public void testSearchStatus() {
	}

	@Ignore
	@Override
	@Test
	public void testSearchVersions() {
	}

	@Ignore
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
			serviceContext.getScopeGroupId(), keywords, keywords, true, null,
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
			redirectEntry.getRedirectEntryId(), keywords, keywords,
			RandomTestUtil.randomBoolean(), null);
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

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

}