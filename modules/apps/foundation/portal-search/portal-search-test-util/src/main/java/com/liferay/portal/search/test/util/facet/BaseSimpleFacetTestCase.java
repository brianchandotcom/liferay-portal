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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.mockito.Mockito;

/**
 * @author Bryan Engler
 */
public abstract class BaseSimpleFacetTestCase extends BaseIndexingTestCase {

	protected void addDocument(final String fieldValue) throws Exception {
		addDocument(
			new DocumentCreationHelper() {

				@Override
				public void populate(Document document) {
					document.addText(FACET_FIELD, fieldValue);
				}

			});
	}

	protected void addFacet(
		SearchContext searchContext, JSONObject jsonObject) {

		Facet facet = createFacet(searchContext);

		facet.setFieldName(FACET_FIELD);

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		facetConfiguration.setDataJSONObject(jsonObject);

		searchContext.addFacet(facet);
	}

	protected void assertSearch(
			final JSONObject jsonObject, final List<String> expectedTerms)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					doAssertSearch(jsonObject, expectedTerms);

					return null;
				}

			});
	}

	protected Facet createFacet(SearchContext searchContext) {
		return new SimpleFacet(searchContext);
	}

	protected void doAssertSearch(JSONObject jsonObject, List<String> terms)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		addFacet(searchContext, jsonObject);

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(getDefaultQuery(), BooleanClauseOccur.MUST);
		booleanQuery.add(
			new MatchQuery(FACET_FIELD, PRESENT_BUT_UNMATCHED),
			BooleanClauseOccur.MUST_NOT);

		search(searchContext, booleanQuery);

		List<TermCollector> termCollectors = getTermCollectors(searchContext);

		Assert.assertNotNull(termCollectors);

		Assert.assertEquals(terms.toString(), toString(termCollectors));
	}

	protected List<TermCollector> getTermCollectors(
		SearchContext searchContext) {

		Facet facet = searchContext.getFacet(FACET_FIELD);

		FacetCollector facetCollector = facet.getFacetCollector();

		return facetCollector.getTermCollectors();
	}

	protected JSONObject setUpFrequencyThreshold(
		int frequencyThreshold, JSONObject jsonObject) {

		Mockito.doReturn(
			frequencyThreshold
		).when(
			jsonObject
		).getInt(
			"frequencyThreshold"
		);

		return jsonObject;
	}

	protected JSONObject setUpMaxTerms(int maxTerms) {
		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			maxTerms
		).when(
			jsonObject
		).getInt(
			"maxTerms"
		);

		return jsonObject;
	}

	protected void testMaxTerms() throws Exception {
		addDocument("One Two Three Four Five Six");
		addDocument("ONE TWO THREE FOUR FIVE");
		addDocument("one two three four");
		addDocument("OnE tWo ThReE");
		addDocument("oNE tWO");
		addDocument("oNe");
		addDocument(PRESENT_BUT_UNMATCHED);

		assertSearch(setUpMaxTerms(1), Arrays.asList("one=6"));

		assertSearch(
			setUpMaxTerms(5),
			new ArrayList<String>() {
				{
					add("one=6");
					add("two=5");
					add("three=4");
					add("four=3");
					add("five=2");
				}
			});

		assertSearch(
			setUpFrequencyThreshold(4, setUpMaxTerms(5)),
			new ArrayList<String>() {
				{
					add("one=6");
					add("two=5");
					add("three=4");
				}
			});

		assertSearch(
			setUpFrequencyThreshold(4, setUpMaxTerms(2)),
			new ArrayList<String>() {
				{
					add("one=6");
					add("two=5");
				}
			});
	}

	protected void testMaxTermsNegative() throws Exception {
		addDocument("One");
		addDocument(PRESENT_BUT_UNMATCHED);

		assertSearch(setUpMaxTerms(-25), Arrays.asList("one=1"));
	}

	protected void testMaxTermsZero() throws Exception {
		addDocument("One");
		addDocument(PRESENT_BUT_UNMATCHED);

		assertSearch(setUpMaxTerms(0), Arrays.asList("one=1"));
	}

	protected String toString(List<TermCollector> termCollectors) {
		List<String> list = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			list.add(
				termCollector.getTerm() + "=" + termCollector.getFrequency());
		}

		return list.toString();
	}

	protected static final String FACET_FIELD = Field.TITLE;

	protected static final String PRESENT_BUT_UNMATCHED =
		RandomTestUtil.randomString();

}