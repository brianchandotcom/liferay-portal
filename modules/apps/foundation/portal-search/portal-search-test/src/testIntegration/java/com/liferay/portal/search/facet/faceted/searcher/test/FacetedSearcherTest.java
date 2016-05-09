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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 */
@RunWith(Arquillian.class)
@Sync
public class FacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testSearchByKeywords() throws Exception {
		Group group = userSearchFixture.addGroup();

		String tag = RandomTestUtil.randomString();

		User user = userSearchFixture.addUser(group, tag);

		assertSearch(tag, toMap(user, tag));
	}

	@Test
	public void testSearchByKeywordsIgnoresInactiveSites() throws Exception {
		Group group1 = userSearchFixture.addGroup();

		String prefix = RandomTestUtil.randomString();

		final String tag1 = prefix + " " + RandomTestUtil.randomString();

		final User user1 = userSearchFixture.addUser(group1, tag1);

		Group group2 = userSearchFixture.addGroup();

		final String tag2 = prefix + " " + RandomTestUtil.randomString();

		final User user2 = userSearchFixture.addUser(group2, tag2);

		assertSearch(
			prefix,
			new HashMap<String, String>() {
				{
					putAll(toMap(user1, tag1));
					putAll(toMap(user2, tag2));
				}
			});

		deactivate(group1);

		assertSearch(prefix, toMap(user2, tag2));

		deactivate(group2);

		assertSearch(prefix, Collections.<String, String>emptyMap());
	}

	protected static Map<String, String> toMap(List<Document> list) {
		Map<String, String> map = new HashMap<>(list.size());

		for (Document document : list) {
			map.put(
				document.get("screenName"),
				document.get(Field.ASSET_TAG_NAMES));
		}

		return map;
	}

	protected static Map<String, String> toMap(User user, String tag) {
		return Collections.singletonMap(
			user.getScreenName(), StringUtil.toLowerCase(tag));
	}

	protected void assertSearch(
			final String tag, final Map<String, String> expected)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					FacetedSearcher facetedSearcher = createFacetedSearcher();

					Hits hits = facetedSearcher.search(getSearchContext(tag));

					AssertUtils.assertEquals(
						tag, expected, toMap(hits.toList()));

					return null;
				}

			});
	}

	protected void deactivate(Group group) {
		group.setActive(false);

		GroupLocalServiceUtil.updateGroup(group);
	}

	protected SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

}