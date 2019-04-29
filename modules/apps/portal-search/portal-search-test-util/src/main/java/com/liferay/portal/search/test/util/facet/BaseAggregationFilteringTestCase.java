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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.facet.site.SiteFacetFactory;
import com.liferay.portal.search.facet.tag.AssetTagNamesFacetFactory;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.internal.facet.modified.ModifiedFacetFactoryImpl;
import com.liferay.portal.search.internal.facet.site.SiteFacetFactoryImpl;
import com.liferay.portal.search.internal.facet.tag.AssetTagNamesFacetFactoryImpl;
import com.liferay.portal.search.internal.facet.user.UserFacetFactoryImpl;
import com.liferay.portal.search.internal.filter.FilterBuildersImpl;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseAggregationFilteringTestCase
	extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpJSONFactoryUtil();
	}

	@Test
	public void testAggregationUnfiltered() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.search();

				helper.assertResultCount(4);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=2",
						"[20180602100840 TO 20180602100849]=1",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=2", "222=2"));
				helper.assertFrequencies(
					TAG, Arrays.asList("tde=2", "tab=1", "tbc=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=2", "u cd=2"));
			});
	}

	@Test
	public void testBasicFacetSelection() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.setSearchContextAttribute(
					SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION,
					Boolean.TRUE);

				helper.select(MOD, "[20180602100850 TO 20180602100859]");
				helper.select(SIT, "111");
				helper.select(TAG, "tde");
				helper.select(USE, "u cd");

				helper.search();

				helper.assertResultCount(1);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=2",
						"[20180602100840 TO 20180602100849]=1",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=2", "222=2"));
				helper.assertFrequencies(
					TAG, Arrays.asList("tde=2", "tab=1", "tbc=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=2", "u cd=2"));
			});
	}

	@Test
	public void testSelectModified() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.select(MOD, "[20180602100830 TO 20180602100839]");

				helper.search();

				helper.assertResultCount(2);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=2",
						"[20180602100840 TO 20180602100849]=1",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=1", "222=1"));
				helper.assertFrequencies(TAG, Arrays.asList("tab=1", "tbc=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=1", "u cd=1"));
			});
	}

	@Test
	public void testSelectSite() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.select(SIT, "111");

				helper.search();

				helper.assertResultCount(2);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=1",
						"[20180602100840 TO 20180602100849]=0",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=2", "222=2"));
				helper.assertFrequencies(TAG, Arrays.asList("tab=1", "tde=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=1", "u cd=1"));
			});
	}

	@Test
	public void testSelectTag() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.select(TAG, "tde");

				helper.search();

				helper.assertResultCount(2);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=0",
						"[20180602100840 TO 20180602100849]=1",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=1", "222=1"));
				helper.assertFrequencies(
					TAG, Arrays.asList("tde=2", "tab=1", "tbc=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=1", "u cd=1"));
			});
	}

	@Test
	public void testSelectUser() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.select(USE, "u ab");

				helper.search();

				helper.assertResultCount(2);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=1",
						"[20180602100840 TO 20180602100849]=1",
						"[20180602100850 TO 20180602100859]=0"));
				helper.assertFrequencies(SIT, Arrays.asList("111=1", "222=1"));
				helper.assertFrequencies(TAG, Arrays.asList("tab=1", "tde=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=2", "u cd=2"));
			});
	}

	@Test
	public void testVariousSelectionsCombined() throws Exception {
		addDocument("tab", "20180602100837", 111, "u ab");
		addDocument("tbc", "20180602100837", 222, "u cd");
		addDocument("tde", "20180602100847", 222, "u ab");
		addDocument("tde", "20180602100857", 111, "u cd");

		assertSearchAggregationFiltering(
			helper -> {
				helper.select(TAG, "tde");
				helper.select(USE, "u cd");

				helper.search();

				helper.assertResultCount(1);

				helper.assertFrequencies(
					MOD,
					Arrays.asList(
						"[20180602100830 TO 20180602100839]=0",
						"[20180602100840 TO 20180602100849]=0",
						"[20180602100850 TO 20180602100859]=1"));
				helper.assertFrequencies(SIT, Arrays.asList("111=1"));
				helper.assertFrequencies(TAG, Arrays.asList("tbc=1", "tde=1"));
				helper.assertFrequencies(
					USE, Arrays.asList("u ab=1", "u cd=1"));
			});
	}

	protected static void setConfigurationRanges(
		Facet facet, Collection<String> ranges) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String range : ranges) {
			JSONObject jsonObject = JSONUtil.put("range", range);

			jsonArray.put(jsonObject);
		}

		dataJSONObject.put("ranges", jsonArray);
	}

	protected void addDocument(
			String tag, String modified, long site, String user)
		throws Exception {

		addDocument(
			document -> {
				document.addKeyword(Field.ASSET_TAG_NAMES, tag);
				document.addKeyword(Field.MODIFIED_DATE, modified);
				document.addNumber(Field.GROUP_ID, site);
				document.addKeyword(Field.USER_NAME, user);
			});
	}

	protected void assertSearchAggregationFiltering(
			Consumer<AggregationFilteringTestHelper> consumer)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				consumer.accept(new AggregationFilteringTestHelper());

				return null;
			});
	}

	protected ModifiedFacetFactoryImpl createModifiedFacetFactory() {
		return new ModifiedFacetFactoryImpl() {
			{
				filterBuilders = new FilterBuildersImpl();
			}
		};
	}

	protected Hits doSearch(SearchContext searchContext) {
		return search(searchContext);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected static final String MOD = "mod";

	protected static final String SIT = "sit";

	protected static final String TAG = "tag";

	protected static final String USE = "use";

	protected AssetTagNamesFacetFactory assetTagNamesFacetFactory =
		new AssetTagNamesFacetFactoryImpl();
	protected ModifiedFacetFactory modifiedFacetFactory =
		createModifiedFacetFactory();
	protected SiteFacetFactory siteFacetFactory = new SiteFacetFactoryImpl();
	protected UserFacetFactory userFacetFactory = new UserFacetFactoryImpl();

	private class AggregationFilteringTestHelper {

		public AggregationFilteringTestHelper() {
			_searchContext = createSearchContext();

			addFacet(MOD, createModifiedFacet());
			addFacet(SIT, createFacet(siteFacetFactory));
			addFacet(TAG, createFacet(assetTagNamesFacetFactory));
			addFacet(USE, createFacet(userFacetFactory));
		}

		public void assertFrequencies(
			String aggregationName, List<String> expected) {

			FacetsAssert.assertFrequencies(
				aggregationName, _searchContext, expected);
		}

		public void assertResultCount(int expected) {
			Document[] documents = _hits.getDocs();

			Assert.assertEquals(
				Arrays.toString(documents), expected, documents.length);
		}

		public void search() {
			_hits = doSearch(_searchContext);
		}

		public void select(String aggregationName, String... selections) {
			Facet facet = (Facet)_searchContext.getFacet(aggregationName);

			facet.select(selections);
		}

		public void setSearchContextAttribute(String name, Serializable value) {
			_searchContext.setAttribute(name, value);
		}

		protected void addFacet(String aggregationName, Facet facet) {
			Map<String, com.liferay.portal.kernel.search.facet.Facet> facets =
				_searchContext.getFacets();

			facet.setAggregationName(aggregationName);

			facets.put(aggregationName, facet);
		}

		protected Facet createFacet(FacetFactory facetFactory) {
			return facetFactory.newInstance(_searchContext);
		}

		protected Facet createModifiedFacet() {
			Facet facet = modifiedFacetFactory.newInstance(_searchContext);

			setConfigurationRanges(
				facet,
				Arrays.asList(
					"[20180602100830 TO 20180602100839]",
					"[20180602100840 TO 20180602100849]",
					"[20180602100850 TO 20180602100859]"));

			return facet;
		}

		private Hits _hits;
		private final SearchContext _searchContext;

	}

}