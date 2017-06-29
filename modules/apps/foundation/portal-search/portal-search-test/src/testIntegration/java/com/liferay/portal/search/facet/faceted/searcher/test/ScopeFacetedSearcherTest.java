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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacetFactory;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.SearchMapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class ScopeFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		_scopeFacetFactory = registry.getService(ScopeFacetFactory.class);
	}

	@Test
	public void testSearchByFacet() throws Exception {
		final Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		addUser(group1, keyword + " " + RandomTestUtil.randomString());

		final Group group2 = userSearchFixture.addGroup();

		addUser(group2, keyword + " " + RandomTestUtil.randomString());
		addUser(group2, keyword + " " + RandomTestUtil.randomString());

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _scopeFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		searchContext.setGroupIds(
			new long[] {group1.getGroupId(), group2.getGroupId()});

		search(searchContext);

		Map<String, Integer> frequencies = SearchMapUtil.join(
			toMap(group1, 1), toMap(group2, 2));

		assertFrequencies(facet.getFieldName(), searchContext, frequencies);
	}

	@Test
	public void testSearchFromSearchPortletWithScopeEverything()
		throws Exception {

		final Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		String tag1 = keyword + " " + RandomTestUtil.randomString();

		User user1 = addUser(group1, tag1);

		final Group group2 = userSearchFixture.addGroup();

		String tag2 = keyword + " " + RandomTestUtil.randomString();

		User user2 = addUser(group2, tag2);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _scopeFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		searchContext.setAttribute("groupId", "0");
		searchContext.setGroupIds(new long[] {RandomTestUtil.randomLong()});

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = SearchMapUtil.join(
			toMap(group1, 1), toMap(group2, 1));

		assertFrequencies(facet.getFieldName(), searchContext, frequencies);

		Map<String, String> tags = SearchMapUtil.join(
			toMap(user1, tag1), toMap(user2, tag2));

		assertTags(keyword, hits, tags);
	}

	@Test
	public void testSearchFromSearchPortletWithScopeThisSite()
		throws Exception {

		Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		String tag1 = keyword + " " + RandomTestUtil.randomString();

		User user1 = addUser(group1, tag1);

		Group group2 = userSearchFixture.addGroup();

		String tag2 = keyword + " " + RandomTestUtil.randomString();

		addUser(group2, tag2);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _scopeFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		searchContext.setAttribute(
			"groupId", String.valueOf(group1.getGroupId()));

		BooleanClause<?> booleanClause = new BooleanClauseImpl<>(
			new TermQueryImpl(
				Field.GROUP_ID, String.valueOf(group1.getGroupId())),
			BooleanClauseOccur.MUST);

		searchContext.setBooleanClauses(new BooleanClause[] {booleanClause});

		searchContext.setGroupIds(new long[] {group2.getGroupId()});

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = toMap(group1, 1);

		assertFrequencies(facet.getFieldName(), searchContext, frequencies);

		Map<String, String> tags = toMap(user1, tag1);

		assertTags(keyword, hits, tags);
	}

	protected static Map<String, Integer> toMap(Group group, Integer count) {
		return Collections.singletonMap(
			String.valueOf(group.getGroupId()), count);
	}

	private ScopeFacetFactory _scopeFacetFactory;

}