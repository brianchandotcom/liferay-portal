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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Test
	public void testSearchByFacet() throws Exception {
		final Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		userSearchFixture.addUser(
			group1, keyword + " " + RandomTestUtil.randomString());

		final Group group2 = userSearchFixture.addGroup();

		userSearchFixture.addUser(
			group2, keyword + " " + RandomTestUtil.randomString());
		userSearchFixture.addUser(
			group2, keyword + " " + RandomTestUtil.randomString());

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setGroupIds(
			new long[] {group1.getGroupId(), group2.getGroupId()});

		assertFrequencies(
			searchContext,
			new HashMap<Long, Integer>() {
				{
					putAll(toMap(group1, 1));
					putAll(toMap(group2, 2));
				}
			});
	}

	@Test
	public void testSearchFromSearchPortletWithScopeEverything()
		throws Exception {

		final Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		userSearchFixture.addUser(
			group1, keyword + " " + RandomTestUtil.randomString());

		final Group group2 = userSearchFixture.addGroup();

		userSearchFixture.addUser(
			group2, keyword + " " + RandomTestUtil.randomString());

		final SearchContext searchContext = getSearchContext(keyword);

		searchContext.setAttribute("groupId", "0");
		searchContext.setGroupIds(new long[] {RandomTestUtil.randomLong()});

		assertFrequencies(
			searchContext,
			new HashMap<Long, Integer>() {
				{
					putAll(toMap(group1, 1));
					putAll(toMap(group2, 1));
				}
			});
	}

	@Test
	public void testSearchFromSearchPortletWithScopeThisSite()
		throws Exception {

		Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		userSearchFixture.addUser(
			group1, keyword + " " + RandomTestUtil.randomString());

		Group group2 = userSearchFixture.addGroup();

		userSearchFixture.addUser(
			group2, keyword + " " + RandomTestUtil.randomString());

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setAttribute(
			"groupId", String.valueOf(group1.getGroupId()));
		searchContext.setGroupIds(new long[] {group2.getGroupId()});

		assertFrequencies(searchContext, toMap(group1, 1));
	}

	protected static Map<Long, Integer> toMap(Group group, Integer count) {
		return Collections.singletonMap(group.getGroupId(), count);
	}

	protected static Map<Long, Integer> toMap(
		List<TermCollector> termCollectors) {

		Map<Long, Integer> actual = new HashMap<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			actual.put(
				Long.valueOf(termCollector.getTerm()),
				termCollector.getFrequency());
		}

		return actual;
	}

	protected void assertFrequencies(
			final SearchContext searchContext,
			final Map<Long, Integer> expected)
		throws Exception {

		FacetedSearcher facetedSearcher = createFacetedSearcher();

		facetedSearcher.search(searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		Facet facet = facets.get(Field.GROUP_ID);

		FacetCollector facetCollector = facet.getFacetCollector();

		AssertUtils.assertEquals(
			searchContext.getKeywords(), expected,
			toMap(facetCollector.getTermCollectors()));
	}

	protected SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		ScopeFacet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(false);

		searchContext.addFacet(scopeFacet);

		return searchContext;
	}

}