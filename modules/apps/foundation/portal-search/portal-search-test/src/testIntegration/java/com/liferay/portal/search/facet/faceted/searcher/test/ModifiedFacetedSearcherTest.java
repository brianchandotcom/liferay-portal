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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class ModifiedFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testRanges() throws Exception {
		Group group = userSearchFixture.addGroup();

		final String keyword = RandomTestUtil.randomString();

		userSearchFixture.addUser(group, keyword);

		final String configRange1 = "[11110101010101 TO 19990101010101]";
		final String configRange2 = "[19990202020202 TO 22220202020202]";
		final String customRange = "[11110101010101 TO 22220202020202]";

		final HashMap<String, Integer> frequencies =
			new HashMap<String, Integer>() {
				{
					put(configRange1, 0);
					put(configRange2, 1);
					put(customRange, 1);
				}
			};

		SearchContext searchContext = getSearchContext(keyword);

		ModifiedFacet modifiedFacet = new ModifiedFacet(searchContext);

		setConfigurationRanges(modifiedFacet, configRange1, configRange2);

		setCustomRange(modifiedFacet, searchContext, customRange);

		searchContext.addFacet(modifiedFacet);

		assertRanges(frequencies, modifiedFacet, searchContext);
	}

	protected static void assertEquals(
		Collection<String> expected, Collection<String> actual) {

		Assert.assertEquals(sort(expected), sort(actual));
	}

	protected static JSONObject createDataJSONObject(String... ranges)
		throws Exception {

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String range : ranges) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("range", range);

			jsonArray.put(jsonObject);
		}

		dataJSONObject.put("ranges", jsonArray);

		return dataJSONObject;
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

	protected static Map<String, Integer> toMap(
		List<TermCollector> termCollectors) {

		Map<String, Integer> map = new HashMap<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			map.put(termCollector.getTerm(), termCollector.getFrequency());
		}

		return map;
	}

	protected void assertRanges(
			Map<String, Integer> expected, ModifiedFacet modifiedFacet,
			SearchContext searchContext)
		throws SearchException {

		FacetedSearcher facetedSearcher = createFacetedSearcher();

		facetedSearcher.search(searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		Facet facet = facets.get(modifiedFacet.getFieldName());

		FacetCollector facetCollector = facet.getFacetCollector();

		AssertUtils.assertEquals(
			searchContext.getKeywords(), expected,
			toMap(facetCollector.getTermCollectors()));
	}

}