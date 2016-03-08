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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
@Sync
public class FacetedSearcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_userSearchFixture.setUp();

		_assetTags = _userSearchFixture.getAssetTags();
		_groups = _userSearchFixture.getGroups();
		_users = _userSearchFixture.getUsers();
	}

	@After
	public void tearDown() throws Exception {
		_userSearchFixture.tearDown();
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		Group group = _userSearchFixture.addGroup();

		String tag = RandomTestUtil.randomString();

		_userSearchFixture.addUser(group, tag);

		assertSearch(tag, 1);
	}

	@Test
	public void testSearchByKeywordsIgnoresInactiveSites() throws Exception {
		Group group1 = _userSearchFixture.addGroup();

		_userSearchFixture.addUser(
			group1, "Liferay " + RandomTestUtil.randomString());

		Group group2 = _userSearchFixture.addGroup();

		_userSearchFixture.addUser(
			group2, "Liferay " + RandomTestUtil.randomString());

		assertSearch("Liferay", 2);

		deactivate(group1);

		assertSearch("Liferay", 1);

		deactivate(group2);

		assertSearch("Liferay", 0);
	}

	protected static void assertSearch(final String tag, final int count)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					FacetedSearcher facetedSearcher = new FacetedSearcher();

					Hits hits = facetedSearcher.search(getSearchContext(tag));

					Assert.assertEquals(count, hits.getLength());

					return null;
				}

			});
	}

	protected static void deactivate(Group group) {
		group.setActive(false);

		GroupLocalServiceUtil.updateGroup(group);
	}

	protected static SearchContext getSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	@DeleteAfterTestRun
	private List<AssetTag> _assetTags;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

	private final UserSearchFixture _userSearchFixture =
		new UserSearchFixture();

}