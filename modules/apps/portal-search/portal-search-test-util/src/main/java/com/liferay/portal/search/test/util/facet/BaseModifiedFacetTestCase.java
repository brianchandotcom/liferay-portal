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

package com.liferay.portal.search.test.util.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.internal.facet.modified.ModifiedFacetFactoryImpl;
import com.liferay.portal.search.test.util.indexing.QueryContributors;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Bryan Engler
 */
public abstract class BaseModifiedFacetTestCase extends BaseFacetTestCase {

	@Test
	public void testCustomRange() throws Exception {
		addDocument("20170102000000");
		addDocument("20170104000000");
		addDocument("20170106000000");

		String customRange = "[20170101000000 TO 20170105000000]";

		assertFacet(
			searchContext -> {
				Facet facet = createFacet(searchContext);

				setCustomRange(facet, customRange);

				return facet;
			},
			Arrays.asList("[20170101000000 TO 20170105000000]=2"));
	}

	@Test
	public void testRanges() throws Exception {
		addDocument("20170102000000");

		String[] configRanges = {
			"[11110101010101 TO 19990101010101]",
			"[19990202020202 TO 22220202020202]"
		};

		String customRange = "[11110101010101 TO 22220202020202]";

		List<String> expectedRanges = Arrays.asList(
			"[11110101010101 TO 19990101010101]=0",
			"[11110101010101 TO 22220202020202]=1",
			"[19990202020202 TO 22220202020202]=1");

		assertFacet(
			searchContext -> {
				Facet facet = createFacet(searchContext);

				setConfigurationRanges(facet, configRanges);

				setCustomRange(facet, customRange);

				return facet;
			},
			expectedRanges);
	}

	protected static JSONArray createRangeArray(String... ranges) {
		JSONArray jsonArray = Mockito.mock(JSONArray.class);

		Mockito.doReturn(
			ranges.length
		).when(
			jsonArray
		).length();

		for (int i = 0; i < ranges.length; i++) {
			Mockito.doReturn(
				createRangeArrayElement(ranges[i])
			).when(
				jsonArray
			).getJSONObject(
				i
			);
		}

		return jsonArray;
	}

	protected static JSONObject createRangeArrayElement(String range) {
		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			range
		).when(
			jsonObject
		).getString(
			"range"
		);

		return jsonObject;
	}

	protected static void setConfigurationRanges(
		Facet facet, String... ranges) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		Mockito.doReturn(
			true
		).when(
			jsonObject
		).has(
			"ranges"
		);

		Mockito.doReturn(
			createRangeArray(ranges)
		).when(
			jsonObject
		).getJSONArray(
			"ranges"
		);
	}

	protected static void setCustomRange(Facet facet, String customRange) {
		SearchContext searchContext = facet.getSearchContext();

		searchContext.setAttribute(facet.getFieldId(), customRange);
	}

	protected void assertFacet(
			Function<SearchContext, Facet> function, List<String> expectedTerms)
		throws Exception {

		assertFacet(function, QueryContributors.dummy(), expectedTerms);
	}

	protected Facet createFacet(SearchContext searchContext) {
		ModifiedFacetFactory modifiedFacetFactory =
			new ModifiedFacetFactoryImpl();

		Facet facet = modifiedFacetFactory.newInstance(searchContext);

		initFacet(facet);

		return facet;
	}

	protected JSONArray createJSONArray() {
		JSONArray jsonArray = Mockito.mock(JSONArray.class);

		Mockito.doReturn(
			1
		).when(
			jsonArray
		).length();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			jsonArray
		).getString(
			0
		);

		return jsonArray;
	}

	protected JSONFactory createJSONFactory() {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.doReturn(
			createJSONObject()
		).when(
			jsonFactory
		).createJSONObject();

		return jsonFactory;
	}

	protected JSONObject createJSONObject() {
		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			true
		).when(
			jsonObject
		).has(
			"values"
		);

		Mockito.doReturn(
			createJSONArray()
		).when(
			jsonObject
		).getJSONArray(
			"values"
		);

		return jsonObject;
	}

	@Override
	protected String getField() {
		return Field.MODIFIED_DATE;
	}

}