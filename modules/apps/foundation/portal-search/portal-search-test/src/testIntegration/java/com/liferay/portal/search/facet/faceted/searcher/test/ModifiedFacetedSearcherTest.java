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
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ModifiedFacetFactory;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
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
public class ModifiedFacetedSearcherTest extends BaseFacetedSearcherTestCase {

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

		_modifiedFacetFactory = registry.getService(ModifiedFacetFactory.class);
	}

	@Test
	public void testRanges() throws Exception {
		Group group = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		addUser(group, keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _modifiedFacetFactory.newInstance(searchContext);

		String configRange1 = "[11110101010101 TO 19990101010101]";
		String configRange2 = "[19990202020202 TO 22220202020202]";
		String customRange = "[11110101010101 TO 22220202020202]";

		setConfigurationRanges(facet, configRange1, configRange2);

		setCustomRange(facet, searchContext, customRange);

		searchContext.addFacet(facet);

		search(searchContext);

		Map<String, Integer> frequencies = SearchMapUtil.join(
			toMap(configRange1, 0), toMap(configRange2, 1),
			toMap(customRange, 1));

		assertFrequencies(facet.getFieldName(), searchContext, frequencies);
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

	protected static void setConfigurationRanges(Facet facet, String... ranges)
		throws Exception {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		facetConfiguration.setDataJSONObject(createDataJSONObject(ranges));
	}

	protected static void setCustomRange(
		Facet facet, SearchContext searchContext, String customRange) {

		searchContext.setAttribute(facet.getFieldId(), customRange);
	}

	protected static Map<String, Integer> toMap(String key, Integer value) {
		return Collections.singletonMap(key, value);
	}

	private ModifiedFacetFactory _modifiedFacetFactory;

}