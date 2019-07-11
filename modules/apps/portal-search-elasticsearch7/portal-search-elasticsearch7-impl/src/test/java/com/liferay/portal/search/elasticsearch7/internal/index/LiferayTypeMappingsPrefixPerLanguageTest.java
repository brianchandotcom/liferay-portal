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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.document.SingleFieldFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryBuilderFactories;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class LiferayTypeMappingsPrefixPerLanguageTest {

	@Before
	public void setUp() throws Exception {
		IndexName indexName = new IndexName(testName.getMethodName());

		_liferayIndexFixture = new LiferayIndexFixture(_PREFIX, indexName);

		_liferayIndexFixture.setUp();

		_singleFieldFixture = new SingleFieldFixture(
			_liferayIndexFixture.getClient(), indexName,
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		_singleFieldFixture.setQueryBuilderFactory(
			QueryBuilderFactories.MATCH_PHRASE_PREFIX);
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testPrefixEnglish() throws Exception {
		_singleFieldFixture.setField(_PREFIX + "_en");

		String content1 = "terrible";
		String content2 = "terrific";

		index(content1, content2);

		assertSearch("terr", content1, content2);

		assertSearch("terrib", content1);

		assertSearch("terrif", content2);

		assertNoHits("terrier");
	}

	@Test
	public void testPrefixJapanese() throws Exception {
		_singleFieldFixture.setField(_PREFIX + "_ja");

		String content1 = "作戦大成功";
		String content2 = "新規作戦";
		String content3 = "映像製作会社";
		String content4 = "作文を書いた";
		String content5 = "新しい作戦";
		String content6 = "新規作成";
		String content7 = "新世界";
		String content8 = "新着情報";

		index(
			content1, content2, content3, content4, content5, content6,
			content7, content8);

		assertSearch("新", content2, content5, content6, content7, content8);

		assertSearch("作", content1, content2, content4, content5, content6);

		assertNoHits("し");
	}

	@Rule
	public TestName testName = new TestName();

	protected void assertNoHits(String query) throws Exception {
		_singleFieldFixture.assertNoHits(query);
	}

	protected void assertSearch(String query, String... expected)
		throws Exception {

		_singleFieldFixture.assertSearch(query, expected);
	}

	protected void index(String... strings) {
		for (String string : strings) {
			_singleFieldFixture.indexDocument(string);
		}
	}

	private static final String _PREFIX =
		LiferayTypeMappingsPrefixPerLanguageTest.class.getSimpleName();

	private LiferayIndexFixture _liferayIndexFixture;
	private SingleFieldFixture _singleFieldFixture;

}