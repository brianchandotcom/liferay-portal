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
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.QueryContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.mockito.Mockito;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public abstract class BaseFacetTestCase extends BaseIndexingTestCase {

	protected void addDocument(final String... values) throws Exception {
		addDocument(DocumentCreationHelpers.singleText(getField(), values));
	}

	protected Facet addFacet(
		SearchContext searchContext, JSONObject jsonObject) {

		Facet facet = createFacet(searchContext);

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		facetConfiguration.setDataJSONObject(jsonObject);

		searchContext.addFacet(facet);

		return facet;
	}

	protected void assertFacet(
			JSONObject jsonObject, List<String> expectedTerms)
		throws Exception {

		assertFacet(jsonObject, null, expectedTerms);
	}

	protected void assertFacet(
			final JSONObject jsonObject,
			final QueryContributor queryContributor,
			final List<String> expectedTerms)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					doAssertFacet(jsonObject, queryContributor, expectedTerms);

					return null;
				}

			});
	}

	protected void assertFacet(List<String> expectedTerms) throws Exception {
		assertFacet((QueryContributor)null, expectedTerms);
	}

	protected void assertFacet(
			QueryContributor queryContributor, List<String> expectedTerms)
		throws Exception {

		assertFacet(
			Mockito.mock(JSONObject.class), queryContributor, expectedTerms);
	}

	protected abstract Facet createFacet(SearchContext searchContext);

	protected void doAssertFacet(
			JSONObject jsonObject, QueryContributor queryContributor,
			List<String> expectedTerms)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		Facet facet = addFacet(searchContext, jsonObject);

		search(searchContext, _getQuery(queryContributor));

		List<TermCollector> termCollectors = getTermCollectors(
			searchContext, facet.getFieldName());

		Assert.assertNotNull(termCollectors);

		Assert.assertEquals(expectedTerms.toString(), toString(termCollectors));
	}

	protected abstract String getField();

	protected List<TermCollector> getTermCollectors(
		SearchContext searchContext, String facetField) {

		Facet facet = searchContext.getFacet(facetField);

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

	protected String toString(List<TermCollector> termCollectors) {
		List<String> list = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			list.add(
				termCollector.getTerm() + "=" + termCollector.getFrequency());
		}

		return list.toString();
	}

	private Query _getQuery(QueryContributor queryContributor)
		throws Exception {

		Query query = getDefaultQuery();

		if (queryContributor == null) {
			return query;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(query, BooleanClauseOccur.MUST);

		queryContributor.contribute(booleanQuery);

		return booleanQuery;
	}

}