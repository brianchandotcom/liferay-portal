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

package com.liferay.depot.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.test.util.DepotTestUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotEntrySearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSearchBothWithoutPermissions() throws Exception {
		_addDepotEntry(TestPropsValues.getUser(), "depot entry 1");
		_addDepotEntry(TestPropsValues.getUser(), "depot entry 2");

		DepotTestUtil.withRegularUser(
			(user, role) -> {
				Indexer<DepotEntry> indexer = IndexerRegistryUtil.getIndexer(
					DepotEntry.class);

				SearchContext searchContext =
					SearchContextTestUtil.getSearchContext(
						TestPropsValues.getGroupId());

				searchContext.setKeywords("depot entry");

				Hits hits = indexer.search(searchContext);

				Assert.assertEquals(hits.toString(), 0, hits.getLength());
			});
	}

	@Test
	public void testSearchBothWithPermissions() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry(
			TestPropsValues.getUser(), "depot entry 1");
		DepotEntry depotEntry2 = _addDepotEntry(
			TestPropsValues.getUser(), "depot entry 2");

		DepotTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Indexer<DepotEntry> indexer = IndexerRegistryUtil.getIndexer(
					DepotEntry.class);

				SearchContext searchContext =
					SearchContextTestUtil.getSearchContext(
						TestPropsValues.getGroupId());

				searchContext.setGroupIds(null);
				searchContext.setKeywords("depot entry");

				Hits hits = indexer.search(searchContext);

				Assert.assertEquals(hits.toString(), 2, hits.getLength());

				List<SearchResult> searchResults =
					SearchResultUtil.getSearchResults(
						hits, LocaleUtil.getDefault());

				SearchResult firstSearchResult = searchResults.get(0);
				SearchResult secondSearchResult = searchResults.get(1);

				Assert.assertEquals(
					depotEntry1,
					_depotEntryService.getDepotEntry(
						firstSearchResult.getClassPK()));
				Assert.assertEquals(
					depotEntry2,
					_depotEntryService.getDepotEntry(
						secondSearchResult.getClassPK()));
			});
	}

	@Test
	public void testSearchOneWithPermissions() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry(
			TestPropsValues.getUser(), "depot entry 1");
		_addDepotEntry(TestPropsValues.getUser(), "depot entry 2");

		DepotTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Indexer<DepotEntry> indexer = IndexerRegistryUtil.getIndexer(
					DepotEntry.class);

				SearchContext searchContext =
					SearchContextTestUtil.getSearchContext(
						TestPropsValues.getGroupId());

				searchContext.setGroupIds(null);
				searchContext.setKeywords("1");

				Hits hits = indexer.search(searchContext);

				Assert.assertEquals(hits.toString(), 1, hits.getLength());

				List<SearchResult> searchResults =
					SearchResultUtil.getSearchResults(
						hits, LocaleUtil.getDefault());

				SearchResult firstSearchResult = searchResults.get(0);

				Assert.assertEquals(
					depotEntry1,
					_depotEntryService.getDepotEntry(
						firstSearchResult.getClassPK()));
			});
	}

	private DepotEntry _addDepotEntry(User user, String name) throws Exception {
		DepotEntry depotEntry = _depotEntryService.addDepotEntry(
			Collections.singletonMap(LocaleUtil.getDefault(), name),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				user.getGroupId(), user.getUserId()));

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryService _depotEntryService;

}