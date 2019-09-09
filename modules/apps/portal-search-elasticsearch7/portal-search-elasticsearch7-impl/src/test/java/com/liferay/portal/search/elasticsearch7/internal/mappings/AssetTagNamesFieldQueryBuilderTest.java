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

package com.liferay.portal.search.elasticsearch7.internal.mappings;

import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
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

	@Override
	@Test
	public void testBasicWordMatches() throws Exception {
		addDocument("name tag end");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("end", Arrays.asList("name tag end"));
		assertSearch("g", Arrays.asList("NA-META-G"));
		assertSearch("META G", Arrays.asList("NA-META-G"));
		assertSearch("meta", Arrays.asList("NA-META-G"));
		assertSearch("META-G", Arrays.asList("NA-META-G"));
		assertSearch("nA mEtA g", Arrays.asList("NA-META-G"));
		assertSearch("NA-META-G", Arrays.asList("NA-META-G"));
		assertSearch("na-meta-g", Arrays.asList("NA-META-G"));
		assertSearch("name tag", Arrays.asList("name tag end", "Tag Name"));
		assertSearch("name", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("NaMe*", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("name-tag", Arrays.asList("name tag end", "Tag Name"));
		assertSearch("tag 1", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag name", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag", Arrays.asList("TAG1", "Tag Name", "name tag end"));
		assertSearch("tag(142857)", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag1", Arrays.asList("TAG1"));

		assertSearchNoHits("1");
		assertSearchNoHits("ame");
		assertSearchNoHits("METAG");
		assertSearchNoHits("nameTAG");
		assertSearchNoHits("tag2");
	}

	@Test
	public void testMultiwordPhrasePrefixesElasticsearch() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tabs Names Tags");
		addDocument("Tag Names");

		List<String> results1 = Arrays.asList(
			"Name Tags", "Names Tab", "Tabs Names Tags");

		assertSearch("\"name ta*\"", results1);
		assertSearch("\"names ta*\"", results1);

		List<String> results2 = Arrays.asList("Name Tags", "Tabs Names Tags");

		assertSearch("\"name tag*\"", results2);
		assertSearch("\"name tags*\"", results2);
		assertSearch("\"names tag*\"", results2);
		assertSearch("\"names tags*\"", results2);

		List<String> results3 = Arrays.asList("Names Tab");

		assertSearch("\"name tab*\"", results3);
		assertSearch("\"name tabs*\"", results3);
		assertSearch("\"names tab*\"", results3);
		assertSearch("\"names tabs*\"", results3);

		List<String> results4 = Arrays.asList("Tabs Names Tags");

		assertSearch("\"tab na*\"", results4);
		assertSearch("\"tab names*\"", results4);
		assertSearch("\"tabs name*\"", results4);
		assertSearch("\"tabs names ta*\"", results4);
		assertSearch("\"tabs names tag*\"", results4);
		assertSearch("\"tabs names tags*\"", results4);
		assertSearch("\"tabs names*\"", results4);
		assertSearch("\"tabs name ta*\"", results4);

		List<String> results5 = Arrays.asList("Tag Names");

		assertSearch("\"tag na*\"", results5);
		assertSearch("\"tag name*\"", results5);
		assertSearch("\"tag names*\"", results5);
		assertSearch("\"tags names*\"", results5);

		assertSearchNoHits("\"tabs na ta*\"");
		assertSearchNoHits("\"tags na ta*\"");
		assertSearchNoHits("\"tags names tabs*\"");
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
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}