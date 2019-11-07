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

package com.liferay.portal.search.elasticsearch6.internal.index;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class LiferayTypeMappingsPortugueseTest {

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_liferayIndexFixture = new LiferayIndexFixture(
			clazz.getSimpleName(), new IndexName(testName.getMethodName()));

		_liferayIndexFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testPortugueseDynamicTemplatesMatchAnalyzers()
		throws Exception {

		String field_pt = RandomTestUtil.randomString() + "_pt";
		String field_pt_BR = RandomTestUtil.randomString() + "_pt_BR";
		String field_pt_PT = RandomTestUtil.randomString() + "_pt_PT";

		_liferayIndexFixture.index(
			HashMapBuilder.<String, Object>put(
				field_pt, RandomTestUtil.randomString()
			).put(
				field_pt_BR, RandomTestUtil.randomString()
			).put(
				field_pt_PT, RandomTestUtil.randomString()
			).build());

		assertAnalyzer(field_pt, "portuguese");
		assertAnalyzer(field_pt_BR, "brazilian");
		assertAnalyzer(field_pt_PT, "portuguese");
	}

	@Rule
	public TestName testName = new TestName();

	protected void assertAnalyzer(String field, String analyzer)
		throws Exception {

		_liferayIndexFixture.assertAnalyzer(field, analyzer);
	}

	private LiferayIndexFixture _liferayIndexFixture;

}