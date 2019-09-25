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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.facet.site.SiteFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.search.test.util.SearchMapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class ScopeFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchByFacet() throws Exception {
		final Group group1 = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		addUser(group1, keyword + " " + RandomTestUtil.randomString());

		final Group group2 = userSearchFixture.addGroup();

		addUser(group2, keyword + " " + RandomTestUtil.randomString());
		addUser(group2, keyword + " " + RandomTestUtil.randomString());

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setGroupIds(
			new long[] {group1.getGroupId(), group2.getGroupId()});

		Facet facet = _siteFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = SearchMapUtil.join(
			toMap(group1, 1), toMap(group2, 2));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
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

		Facet facet = _siteFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = SearchMapUtil.join(
			toMap(group1, 1), toMap(group2, 1));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);

		Map<String, String> tags = SearchMapUtil.join(
			toMap(user1, tag1), toMap(user2, tag2));

		assertTags(keyword, hits, tags, searchContext);
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

		searchContext.setGroupIds(new long[] {group1.getGroupId()});

		Facet facet = _siteFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = toMap(group1, 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);

		Map<String, String> tags = toMap(user1, tag1);

		assertTags(keyword, hits, tags, searchContext);
	}

	protected static Map<String, Integer> toMap(Group group, Integer count) {
		return Collections.singletonMap(
			String.valueOf(group.getGroupId()), count);
	}

	@Inject
	private static SiteFacetFactory _siteFacetFactory;

}