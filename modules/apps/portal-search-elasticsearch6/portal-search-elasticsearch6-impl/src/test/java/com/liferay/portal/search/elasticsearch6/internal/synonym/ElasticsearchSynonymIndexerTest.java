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

package com.liferay.portal.search.elasticsearch6.internal.synonym;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchSynonymIndexerTest {

	@Before
	public void setUp() throws Exception {
		_synonymIndexerFixture = new ElasticsearchSynonymIndexerFixture(
			ElasticsearchSynonymIndexerTest.class.getSimpleName(),
			testName.getMethodName(), "liferay_filter_synonym_en");

		_synonymIndexerFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_synonymIndexerFixture.tearDown();
	}

	@Test
	public void testGetSynonymSets() throws Exception {
		ElasticsearchSynonymIndexer elasticsearchSynonymIndexer =
			_synonymIndexerFixture.getElasticsearchSynonymIndexer();
		String indexName = _synonymIndexerFixture.getIndexName();

		Assert.assertArrayEquals(
			new String[0],
			elasticsearchSynonymIndexer.getSynonymSets(
				indexName, "liferay_filter_synonym_en"));

		String[] synonymSet = {"car, automobile"};

		_synonymIndexerFixture.updateSynonymSets(synonymSet);

		Assert.assertArrayEquals(
			synonymSet,
			elasticsearchSynonymIndexer.getSynonymSets(
				indexName, "liferay_filter_synonym_en"));
	}

	@Test
	public void testUpdateSynonymSets() throws Exception {
		_synonymIndexerFixture.assertSynonym(
			"title_en_US", "Automobiles can be useful", "automobile", "car",
			"car");
	}

	@Test
	public void testUpdateSynonymSetsCaseInsensitiveQueryString()
		throws Exception {

		_synonymIndexerFixture.assertSynonym(
			"title_en_US", "Automobiles can be useful.", "automobile", "car",
			"CAR");
	}

	@Test
	public void testUpdateSynonymSetsCaseInsensitiveSynonym() throws Exception {
		_synonymIndexerFixture.assertSynonym(
			"title_en_US", "Automobiles can be useful.", "automobile", "CAR",
			"Car");
	}

	@Test
	public void testUpdateSynonymSetsOrderInsensitive() throws Exception {
		_synonymIndexerFixture.assertSynonym(
			"title_en_US", "Automobiles can be useful", "car", "automobile",
			"car");
	}

	@Rule
	public TestName testName = new TestName();

	private ElasticsearchSynonymIndexerFixture _synonymIndexerFixture;

}