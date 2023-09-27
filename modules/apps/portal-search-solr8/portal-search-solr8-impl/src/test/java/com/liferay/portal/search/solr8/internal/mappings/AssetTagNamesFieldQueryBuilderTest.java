/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.mappings;

import com.liferay.portal.search.solr8.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseAssetTagNamesFieldQueryBuilderTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class AssetTagNamesFieldQueryBuilderTest
	extends BaseAssetTagNamesFieldQueryBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultiwordPhrasePrefixesSolr() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tabs Names Tags");
		addDocument("Tag Names");

		List<String> results1 = Arrays.asList(
			"Name Tags", "Names Tab", "Tag Names", "Tabs Names Tags");

		assertSearch("\"name ta*\"", results1);
		assertSearch("\"name tabs*\"", results1);
		assertSearch("\"name tags*\"", results1);
		assertSearch("\"names ta*\"", results1);
		assertSearch("\"names tabs*\"", results1);
		assertSearch("\"names tags*\"", results1);

		List<String> results2 = Arrays.asList(
			"Name Tags", "Tag Names", "Tabs Names Tags", "Names Tab");

		assertSearch("\"name tag*\"", results2);
		assertSearch("\"names tag*\"", results2);

		List<String> results3 = Arrays.asList("Names Tab", "Tabs Names Tags");

		assertSearch("\"tab na*\"", results3);
		assertSearch("\"tab names*\"", results3);
		assertSearch("\"tabs na ta*\"", results3);
		assertSearch("\"tabs name ta*\"", results3);
		assertSearch("\"tabs name*\"", results3);
		assertSearch("\"tabs names ta*\"", results3);
		assertSearch("\"tabs names tags*\"", results3);
		assertSearch("\"tabs names*\"", results3);

		List<String> results4 = Arrays.asList("Tabs Names Tags", "Names Tab");

		assertSearch("\"tabs names tag*\"", results4);

		List<String> results5 = Arrays.asList(
			"Name Tags", "Tag Names", "Tabs Names Tags");

		assertSearch("\"tag na*\"", results5);
		assertSearch("\"tag name*\"", results5);
		assertSearch("\"tag names*\"", results5);
		assertSearch("\"tags na ta*\"", results5);
		assertSearch("\"tags names tabs*\"", results5);
		assertSearch("\"tags names*\"", results5);

		List<String> results6 = Arrays.asList(
			"Names Tab", "Tabs Names Tags", "Name Tags", "Tag Names");

		assertSearch("\"name tab*\"", results6);
		assertSearch("\"names tab*\"", results6);

		assertSearchNoHits("\"zz na*\"");
		assertSearchNoHits("\"zz name*\"");
		assertSearchNoHits("\"zz names*\"");
		assertSearchNoHits("\"zz ta*\"");
		assertSearchNoHits("\"zz tab*\"");
		assertSearchNoHits("\"zz tabs*\"");
		assertSearchNoHits("\"zz tag*\"");
		assertSearchNoHits("\"zz tags*\"");
	}

	@Override
	@Test
	public void testMultiwordPrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tabs Names Tags");
		addDocument("Tag Names");

		List<String> results1 = Arrays.asList(
			"Name Tags", "Names Tab", "Tag Names", "Tabs Names Tags");

		assertSearch("name ta", results1);
		assertSearch("names ta", results1);

		List<String> results2 = Arrays.asList(
			"Names Tab", "Tabs Names Tags", "Name Tags", "Tag Names");

		assertSearch("name tab", results2);
		assertSearch("name tabs", results2);
		assertSearch("names tab", results2);
		assertSearch("names tabs", results2);

		List<String> results3 = Arrays.asList(
			"Name Tags", "Tabs Names Tags", "Tag Names", "Names Tab");

		assertSearch("name tag", results3);
		assertSearch("name tags", results3);
		assertSearch("names tag", results3);
		assertSearch("names tags", results3);

		List<String> results4 = Arrays.asList("Names Tab", "Tabs Names Tags");

		assertSearch("tab na", results4);
		assertSearch("tabs na ta", results4);

		List<String> results5 = Arrays.asList(
			"Tabs Names Tags", "Names Tab", "Name Tags", "Tag Names");

		assertSearch("tab names", results5);
		assertSearch("tabs names", results5);
		assertSearch("tabs names tags", results5);

		List<String> results6 = Arrays.asList(
			"Tabs Names Tags", "Name Tags", "Tag Names", "Names Tab");

		assertSearch("tags names tabs", results6);

		List<String> results7 = Arrays.asList(
			"Name Tags", "Tag Names", "Tabs Names Tags");

		assertSearch("tag na", results7);
		assertSearch("tags na ta", results7);

		List<String> results8 = Arrays.asList(
			"Tag Names", "Name Tags", "Tabs Names Tags", "Names Tab");

		assertSearch("tag name", results8);
		assertSearch("tag names", results8);
		assertSearch("tags names", results8);

		assertSearch("zz name", 4);
		assertSearch("zz names", 4);
		assertSearch("zz tab", 2);
		assertSearch("zz tabs", 2);
		assertSearch("zz tag", 3);
		assertSearch("zz tags", 3);

		assertSearchNoHits("zz na");
		assertSearchNoHits("zz ta");
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}