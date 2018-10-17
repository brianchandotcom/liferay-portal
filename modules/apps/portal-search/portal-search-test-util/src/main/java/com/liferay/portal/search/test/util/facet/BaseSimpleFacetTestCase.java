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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.indexing.QueryContributors;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public abstract class BaseSimpleFacetTestCase extends BaseFacetTestCase {

	@Test
	public void testFrequencyThreshold() throws Exception {
		addDocuments(6, "one");
		addDocuments(5, "two");
		addDocuments(4, "three");
		addDocuments(3, "four");
		addDocuments(2, "five");
		addDocuments(1, "six");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setFrequencyThreshold(facet, 4);
				setMaxTerms(facet, 5);

				helper.search();

				helper.assertFrequencies(
					facet, Arrays.asList("one=6", "two=5", "three=4"));
			});

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setFrequencyThreshold(facet, 4);
				setMaxTerms(facet, 2);

				helper.search();

				helper.assertFrequencies(
					facet, Arrays.asList("one=6", "two=5"));
			});
	}

	@Test
	public void testMaxTerms() throws Exception {
		addDocuments(6, "One");
		addDocuments(5, "TWO");
		addDocuments(4, "ThReE");
		addDocuments(3, "four");
		addDocuments(2, "fivE");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 1);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("One=6"));
			});

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 5);

				helper.search();

				helper.assertFrequencies(
					facet,
					Arrays.asList(
						"One=6", "TWO=5", "ThReE=4", "four=3", "fivE=2"));
			});
	}

	@Test
	public void testMaxTermsNegative() throws Exception {
		addDocument("One");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, -25);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("One=1"));
			});
	}

	@Test
	public void testMaxTermsZero() throws Exception {
		addDocument("One");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 0);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("One=1"));
			});
	}

	@Test
	public void testSelection() throws Exception {
		addDocuments(6, "one");
		addDocuments(5, "two");
		addDocuments(4, "three");
		addDocuments(3, "four");
		addDocuments(2, "five");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				select(facet, "three", helper);

				helper.search();

				helper.assertResultCount(4);

				helper.assertFrequencies(
					facet,
					Arrays.asList(
						"one=6", "two=5", "three=4", "four=3", "five=2"));
			});
	}

	@Test
	public void testUnmatchedAreIgnored() throws Exception {
		String presentButUnmatched = RandomTestUtil.randomString();

		addDocument("One");
		addDocument(presentButUnmatched);

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				helper.setQueryContributor(
					QueryContributors.mustNotTerm(
						getField(), presentButUnmatched));

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("One=1"));
			});
	}

	protected Facet createFacet(SearchContext searchContext) {
		Facet facet = new SimpleFacet(searchContext);

		initFacet(facet);

		facet.setFieldName(Field.STATUS);

		return facet;
	}

	@Override
	protected String getField() {
		return Field.STATUS;
	}

	protected void select(
		Facet facet, String value, FacetTestHelper facetTestHelper) {

		facetTestHelper.setSearchContextAttribute(facet.getFieldId(), value);
	}

	protected void setFrequencyThreshold(Facet facet, int frequencyThreshold) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		jsonObject.put("frequencyThreshold", frequencyThreshold);
	}

	protected void setMaxTerms(Facet facet, int maxTerms) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		jsonObject.put("maxTerms", maxTerms);
	}

}