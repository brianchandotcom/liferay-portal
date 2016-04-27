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
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class ScopeFacetTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

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
			new HashMap<Group, Integer>() {
				{
					put(group1, 1);
					put(group2, 2);
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
			new HashMap<Group, Integer>() {
				{
					put(group1, 1);
					put(group2, 1);
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

		assertFrequencies(searchContext, Collections.singletonMap(group1, 1));
	}

	protected static SearchContext getSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		ScopeFacet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(false);

		searchContext.addFacet(scopeFacet);

		return searchContext;
	}

	protected void assertFrequencies(
			final SearchContext searchContext, Map<Group, Integer> frequencies)
		throws Exception {

		final List<String> expectedList = new ArrayList<>();

		for (Map.Entry<Group, Integer> entry : frequencies.entrySet()) {
			Group group = entry.getKey();

			expectedList.add(group.getGroupId() + "=" + entry.getValue());
		}

		Collections.sort(expectedList);

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					FacetedSearcher facetedSearcher = createFacetedSearcher();

					facetedSearcher.search(searchContext);

					Map<String, Facet> facets = searchContext.getFacets();

					Facet facet = facets.get(Field.GROUP_ID);

					FacetCollector facetCollector = facet.getFacetCollector();

					List<String> actualList = new ArrayList<>();

					List<TermCollector> termCollectors =
						facetCollector.getTermCollectors();

					for (TermCollector termCollector : termCollectors) {
						actualList.add(
							termCollector.getTerm() + "=" +
								termCollector.getFrequency());
					}

					Collections.sort(actualList);

					Assert.assertEquals(
						expectedList.toString(), actualList.toString());

					return null;
				}

			});
	}

}