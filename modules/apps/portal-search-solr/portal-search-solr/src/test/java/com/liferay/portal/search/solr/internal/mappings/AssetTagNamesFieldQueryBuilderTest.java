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

package com.liferay.portal.search.solr.internal.mappings;

import com.liferay.portal.search.solr.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.mappings.BaseAssetTagNamesFieldQueryBuilderTestCase;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class AssetTagNamesFieldQueryBuilderTest
	extends BaseAssetTagNamesFieldQueryBuilderTestCase {

	@Test
	public void testMultiwordPhrasePrefixesSolr() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("\"name ta*\"", 1);
		assertSearch("\"name tab*\"", 1);
		assertSearch("\"name tabs*\"", 1);
		assertSearch("\"name tag*\"", 1);
		assertSearch("\"name tags*\"", 1);
		assertSearch("\"names ta*\"", 3);
		assertSearch("\"names tab*\"", 3);
		assertSearch("\"names tabs*\"", 3);
		assertSearch("\"names tag*\"", 3);
		assertSearch("\"names tags*\"", 3);
		assertSearch("\"tab na*\"", 1);
		assertSearch("\"tab names*\"", 1);
		assertSearch("\"tabs na ta*\"", 1);
		assertSearch("\"tabs name ta*\"", 1);
		assertSearch("\"tabs name*\"", 1);
		assertSearch("\"tabs names ta*\"", 1);
		assertSearch("\"tabs names tag*\"", 1);
		assertSearch("\"tabs names tags*\"", 1);
		assertSearch("\"tabs names*\"", 1);
		assertSearch("\"tag na*\"", 1);
		assertSearch("\"tag name*\"", 1);
		assertSearch("\"tag names*\"", 1);
		assertSearch("\"tags na ta*\"", 2);
		assertSearch("\"tags names tabs*\"", 2);
		assertSearch("\"tags names*\"", 2);

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
			"Name Tags", "Names Tab", "Tabs Names Tags", "Tag Names");

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

		List<String> results4 = Arrays.asList("Tabs Names Tags", "Names Tab");

		assertSearch("tab na", results4);

		List<String> results5 = Arrays.asList(
			"Tabs Names Tags", "Names Tab", "Name Tags", "Tag Names");

		assertSearch("tab names", results5);
		assertSearch("tabs names", results5);
		assertSearch("tabs names tags", results5);
		assertSearch("tags names tabs", results5);

		List<String> results6 = Arrays.asList("Names Tab", "Tabs Names Tags");

		assertSearch("tabs na ta", results6);

		List<String> results7 = Arrays.asList(
			"Tag Names", "Name Tags", "Tabs Names Tags");

		assertSearch("tag na", results7);

		List<String> results8 = Arrays.asList(
			"Tag Names", "Name Tags", "Tabs Names Tags", "Names Tab");

		assertSearch("tag name", results8);
		assertSearch("tag names", results8);
		assertSearch("tags names", results8);

		List<String> results9 = Arrays.asList(
			"Name Tags", "Tag Names", "Tabs Names Tags");

		assertSearch("tags na ta", results9);

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