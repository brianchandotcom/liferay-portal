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

package com.liferay.portal.search.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.test.util.UserSearchFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class ModifiedFacetTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

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
	public void testRanges() throws Exception {
		Group group = _userSearchFixture.addGroup();

		final String keyword = RandomTestUtil.randomString();

		_userSearchFixture.addUser(group, keyword);

		final String configRange1 = "[11110101010101 TO 19990101010101]";
		final String configRange2 = "[19990202020202 TO 22220202020202]";
		final String customRange = "[11110101010101 TO 22220202020202]";

		final Collection<String> frequencies = toEntryStrings(
			new HashMap<String, Integer>() {
				{
					put(configRange1, 0);
					put(configRange2, 1);
					put(customRange, 1);
				}
			});

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					SearchContext searchContext = getSearchContext(keyword);

					ModifiedFacet modifiedFacet = new ModifiedFacet(
						searchContext);

					setConfigurationRanges(
						modifiedFacet, configRange1, configRange2);

					setCustomRange(modifiedFacet, searchContext, customRange);

					searchContext.addFacet(modifiedFacet);

					assertRanges(frequencies, modifiedFacet, searchContext);

					return null;
				}

			});
	}

	protected static void assertEquals(
		Collection<String> expected, Collection<String> actual) {

		Assert.assertEquals(sort(expected), sort(actual));
	}

	protected static void assertRanges(
			Collection<String> expected, ModifiedFacet modifiedFacet,
			SearchContext searchContext)
		throws SearchException {

		FacetedSearcher facetedSearcher = new FacetedSearcher();

		facetedSearcher.search(searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		Facet facet = facets.get(modifiedFacet.getFieldName());

		FacetCollector facetCollector = facet.getFacetCollector();

		assertEquals(
			expected,
			toEntryStrings(toMap(facetCollector.getTermCollectors())));
	}

	protected static JSONObject createDataJSONObject(String... ranges)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String range : ranges) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("range", range);

			jsonArray.put(jsonObject);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("ranges", jsonArray);

		return jsonObject;
	}

	protected static SearchContext getSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected static void setConfigurationRanges(
			ModifiedFacet modifiedFacet, String... ranges)
		throws Exception {

		FacetConfiguration facetConfiguration =
			modifiedFacet.getFacetConfiguration();

		facetConfiguration.setDataJSONObject(createDataJSONObject(ranges));
	}

	protected static void setCustomRange(
		ModifiedFacet modifiedFacet, SearchContext searchContext,
		String customRange) {

		searchContext.setAttribute(modifiedFacet.getFieldId(), customRange);
	}

	protected static String sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

	protected static List<String> toEntryStrings(Map<?, ?> map) {
		List<String> strings = new ArrayList<>(map.size());

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			strings.add(entry.toString());
		}

		return strings;
	}

	protected static Map<String, Integer> toMap(
		List<TermCollector> termCollectors) {

		Map<String, Integer> map = new HashMap<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			map.put(termCollector.getTerm(), termCollector.getFrequency());
		}

		return map;
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