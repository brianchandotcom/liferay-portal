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

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.indexing.QueryContributors;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public abstract class BaseSimpleFacetTestCase extends BaseFacetTestCase {

	@Override
	protected Facet createFacet(SearchContext searchContext) {
		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName(Field.TITLE);

		return facet;
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	protected void testFrequencyThreshold() throws Exception {
		addDocument("One Two Three Four Five Six");
		addDocument("ONE TWO THREE FOUR FIVE");
		addDocument("one two three four");
		addDocument("OnE tWo ThReE");
		addDocument("oNE tWO");
		addDocument("oNe");

		assertFacet(
			setUpFrequencyThreshold(4, setUpMaxTerms(5)),
			new ArrayList<String>() {
				{
					add("one=6");
					add("two=5");
					add("three=4");
				}
			});

		assertFacet(
			setUpFrequencyThreshold(4, setUpMaxTerms(2)),
			new ArrayList<String>() {
				{
					add("one=6");
					add("two=5");
				}
			});
	}

	protected void testMaxTerms() throws Exception {
		addDocument("One Two Three Four Five Six");
		addDocument("ONE TWO THREE FOUR FIVE");
		addDocument("one two three four");
		addDocument("OnE tWo ThReE");
		addDocument("oNE tWO");
		addDocument("oNe");

		assertFacet(setUpMaxTerms(1), Arrays.asList("one=6"));

		assertFacet(
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
	}

	protected void testMaxTermsNegative() throws Exception {
		addDocument("One");

		assertFacet(setUpMaxTerms(-25), Arrays.asList("one=1"));
	}

	protected void testMaxTermsZero() throws Exception {
		addDocument("One");

		assertFacet(setUpMaxTerms(0), Arrays.asList("one=1"));
	}

	protected void testUnmatchedAreIgnored() throws Exception {
		String presentButUnmatched = RandomTestUtil.randomString();

		addDocument("One");
		addDocument(presentButUnmatched);

		assertFacet(
			QueryContributors.mustNotMatch(getField(), presentButUnmatched),
			Arrays.asList("one=1"));
	}

}