/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.facet.BaseNestedFieldsFacetTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class NestedFieldsFacetWithSortableValueFieldTest
	extends BaseNestedFieldsFacetTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	@Test
	public void testMaxTerms() throws Exception {
		addDocuments(6, "one");
		addDocuments(5, "two");
		addDocuments(4, "three");
		addDocuments(3, "four");
		addDocuments(2, "five");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 1);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("one=6"));
			});
		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 5);

				helper.search();

				helper.assertFrequencies(
					facet,
					Arrays.asList(
						"one=6", "two=5", "three=4", "four=3", "five=2"));
			});
	}

	@Override
	@Test
	public void testMaxTermsNegative() throws Exception {
		addDocument("one");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, -25);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("one=1"));
			});
	}

	@Override
	@Test
	public void testMaxTermsZero() throws Exception {
		addDocument("one");

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				setMaxTerms(facet, 0);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("one=1"));
			});
	}

	@Override
	@Test
	public void testUnmatchedAreIgnored() throws Exception {
		String presentButUnmatched = RandomTestUtil.randomString();

		addDocument("one");
		addDocument(presentButUnmatched);

		assertSearchFacet(
			helper -> {
				Facet facet = helper.addFacet(this::createFacet);

				filterUnmatched(helper, presentButUnmatched);

				helper.search();

				helper.assertFrequencies(facet, Arrays.asList("one=1"));
			});
	}

	@Override
	protected void addDocument(String... values) throws Exception {
		addDocument(
			DocumentCreationHelpers.oneSortableDDMStringField(
				getField(), getNestedDocumentValueFieldName(), values));
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	@Override
	protected String getValueFieldName() {
		return "ddmFieldArray.ddmFieldValueKeyword_en_US_String_sortable." +
			"keyword_lowercase";
	}

}