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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.internal.facet.modified.ModifiedFacetFactoryImpl;
import com.liferay.portal.search.internal.filter.FilterBuildersImpl;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

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

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setCustomRange(facet, customRange);

				helper.search();

				helper.assertFrequencies(
					facet,
					Arrays.asList("[20170101000000 TO 20170105000000]=2"));
			});
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

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setConfigurationRanges(facet, configRanges);
				setCustomRange(facet, customRange);

				helper.search();

				helper.assertFrequencies(facet, expectedRanges);
			});
	}

	@Test
	public void testSearchEngineDateMath() throws Exception {
		addDocument("17760704000000");
		addDocument("27760704000000");

		String dateMathExpressionWithAlphabeticalOrderSwitched =
			"[now-500y TO now]";

		doTestSearchEngineDateMath(
			dateMathExpressionWithAlphabeticalOrderSwitched, 1);
	}

	protected Facet createFacet(SearchContext searchContext) {
		ModifiedFacetFactory modifiedFacetFactory =
			new ModifiedFacetFactoryImpl() {
				{
					filterBuilders = new FilterBuildersImpl();
				}
			};

		Facet facet = modifiedFacetFactory.newInstance(searchContext);

		initFacet(facet);

		return facet;
	}

	protected JSONArray createRangeArray(String... ranges) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (String range : ranges) {
			jsonArray.put(createRangeArrayElement(range));
		}

		return jsonArray;
	}

	protected JSONObject createRangeArrayElement(String range) {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("range", range);

		return jsonObject;
	}

	protected void doTestSearchEngineDateMath(String range, int frequency)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				SearchContext searchContext = createSearchContext();

				Facet facet = createFacet(searchContext);

				setConfigurationRanges(facet, new String[] {range});

				facet.select(range);

				searchContext.addFacet(facet);

				Hits hits = search(
					searchContext,
					query -> {
						BooleanClause<Filter> booleanClause =
							facet.getFacetFilterBooleanClause();

						query.setPostFilter(booleanClause.getClause());
					});

				Assert.assertEquals(
					hits.toString(), frequency, hits.getLength());

				FacetsAssert.assertFrequencies(
					facet.getFieldName(), searchContext,
					Arrays.asList(range + "=" + frequency));

				return null;
			});
	}

	@Override
	protected String getField() {
		return Field.MODIFIED_DATE;
	}

	protected void setConfigurationRanges(Facet facet, String... ranges) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		jsonObject.put("ranges", createRangeArray(ranges));
	}

	protected void setCustomRange(Facet facet, String customRange) {
		SearchContext searchContext = facet.getSearchContext();

		searchContext.setAttribute(facet.getFieldId(), customRange);
	}

}